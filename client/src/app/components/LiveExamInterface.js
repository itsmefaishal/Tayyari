'use client';
import { useState, useEffect, useRef } from 'react';
import { useRouter } from 'next/navigation';

export default function LiveExamInterface({ examData }) {
  const router = useRouter();
  
  console.log('LiveExamInterface received examData:', examData);
  
  // Exam state
  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [answers, setAnswers] = useState({});
  const [markedForReview, setMarkedForReview] = useState(new Set());
  const [visited, setVisited] = useState(new Set([0]));
  const [timeRemaining, setTimeRemaining] = useState(examData?.duration ? examData.duration * 60 : 180 * 60); // Convert minutes to seconds
  const [isFullScreen, setIsFullScreen] = useState(false);
  const [showWarning, setShowWarning] = useState(false);
  const [warningCount, setWarningCount] = useState(0);
  const [showSubmitConfirm, setShowSubmitConfirm] = useState(false);
  const [isExamStarted, setIsExamStarted] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState(null);
  
  const examContainerRef = useRef(null);
  const startTime = useRef(null);

  // Sample exam data structure - ONLY used as fallback
  const sampleExamData = examData && examData.sections && examData.sections.length > 0 
    ? examData 
    : {
        title: "JEE Main 2024 Mock Test",
        duration: 180,
        totalMarks: 300,
        sections: [
          {
            name: "Physics",
            questions: [
              {
                id: 1,
                text: "A particle moves in a circle of radius 5 cm with constant speed and time period 0.2π sec. The acceleration of the particle is:",
                options: ["25 m/s²", "36 m/s²", "5 m/s²", "15 m/s²"],
                correctAnswer: 0,
                marks: 4,
                negativeMarks: -1
              }
            ]
          }
        ]
      };

  // Flatten all questions from all sections
  const allQuestions = sampleExamData.sections.flatMap(section => 
    section.questions.map(q => ({ ...q, section: section.name }))
  );

  // Debug logs
  useEffect(() => {
    console.log('LiveExamInterface mounted');
    console.log('Exam Data:', examData);
    console.log('Sample Exam Data:', sampleExamData);
    console.log('All Questions:', allQuestions);
    console.log('Number of questions:', allQuestions.length);
  }, []);

  useEffect(() => {
    console.log('isExamStarted changed:', isExamStarted);
  }, [isExamStarted]);

  // Timer effect
  useEffect(() => {
    if (!isExamStarted) return;

    const timer = setInterval(() => {
      setTimeRemaining(prev => {
        if (prev <= 1) {
          handleAutoSubmit();
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(timer);
  }, [isExamStarted]);

  // Format time
  const formatTime = (seconds) => {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const secs = seconds % 60;
    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
  };

  // Fullscreen handling
  const enterFullScreen = async () => {
    console.log('enterFullScreen called');
    console.log('examContainerRef.current:', examContainerRef.current);
    
    try {
      if (examContainerRef.current) {
        console.log('Attempting to enter fullscreen...');
        
        // Try different fullscreen methods for browser compatibility
        if (examContainerRef.current.requestFullscreen) {
          console.log('Using requestFullscreen');
          await examContainerRef.current.requestFullscreen();
        } else if (examContainerRef.current.webkitRequestFullscreen) {
          console.log('Using webkitRequestFullscreen');
          await examContainerRef.current.webkitRequestFullscreen();
        } else if (examContainerRef.current.mozRequestFullScreen) {
          console.log('Using mozRequestFullScreen');
          await examContainerRef.current.mozRequestFullScreen();
        } else if (examContainerRef.current.msRequestFullscreen) {
          console.log('Using msRequestFullscreen');
          await examContainerRef.current.msRequestFullscreen();
        } else {
          console.log('No fullscreen API available');
        }
        
        console.log('Setting isFullScreen and isExamStarted to true');
        setIsFullScreen(true);
        setIsExamStarted(true);
      } else {
        console.error('examContainerRef.current is null');
      }
    } catch (err) {
      console.error('Error entering fullscreen:', err);
      // If fullscreen fails, still start the exam (for testing)
      console.log('Fullscreen failed, starting exam anyway for testing');
      alert('Fullscreen not supported. Starting exam in normal mode for testing.');
      setIsExamStarted(true);
    }
  };

  // Detect fullscreen exit
  useEffect(() => {
    const handleFullScreenChange = () => {
      if (!document.fullscreenElement && isExamStarted) {
        setWarningCount(prev => prev + 1);
        setShowWarning(true);
        
        if (warningCount >= 2) {
          handleAutoSubmit();
        }
      }
    };

    document.addEventListener('fullscreenchange', handleFullScreenChange);
    return () => document.removeEventListener('fullscreenchange', handleFullScreenChange);
  }, [isExamStarted, warningCount]);

  // Prevent context menu, copy, paste
  useEffect(() => {
    const preventActions = (e) => {
      e.preventDefault();
      return false;
    };

    if (isExamStarted) {
      document.addEventListener('contextmenu', preventActions);
      document.addEventListener('copy', preventActions);
      document.addEventListener('cut', preventActions);
      document.addEventListener('paste', preventActions);
    }

    return () => {
      document.removeEventListener('contextmenu', preventActions);
      document.removeEventListener('copy', preventActions);
      document.removeEventListener('cut', preventActions);
      document.removeEventListener('paste', preventActions);
    };
  }, [isExamStarted]);

  // Tab visibility detection
  useEffect(() => {
    const handleVisibilityChange = () => {
      if (document.hidden && isExamStarted) {
        setWarningCount(prev => prev + 1);
        setShowWarning(true);
        
        if (warningCount >= 2) {
          handleAutoSubmit();
        }
      }
    };

    document.addEventListener('visibilitychange', handleVisibilityChange);
    return () => document.removeEventListener('visibilitychange', handleVisibilityChange);
  }, [isExamStarted, warningCount]);

  // Question navigation
  const goToQuestion = (index) => {
    setCurrentQuestion(index);
    setVisited(prev => new Set([...prev, index]));
  };

  const nextQuestion = () => {
    if (currentQuestion < allQuestions.length - 1) {
      goToQuestion(currentQuestion + 1);
    }
  };

  const previousQuestion = () => {
    if (currentQuestion > 0) {
      goToQuestion(currentQuestion - 1);
    }
  };

  // Answer handling
  const selectAnswer = (optionIndex) => {
    setAnswers(prev => ({
      ...prev,
      [currentQuestion]: optionIndex
    }));
  };

  const clearResponse = () => {
    setAnswers(prev => {
      const newAnswers = { ...prev };
      delete newAnswers[currentQuestion];
      return newAnswers;
    });
  };

  const toggleMarkForReview = () => {
    setMarkedForReview(prev => {
      const newSet = new Set(prev);
      if (newSet.has(currentQuestion)) {
        newSet.delete(currentQuestion);
      } else {
        newSet.add(currentQuestion);
      }
      return newSet;
    });
  };

  const saveAndNext = () => {
    nextQuestion();
  };

  const markForReviewAndNext = () => {
    toggleMarkForReview();
    nextQuestion();
  };

  // Get question status
  const getQuestionStatus = (index) => {
    const isAnswered = answers.hasOwnProperty(index);
    const isMarked = markedForReview.has(index);
    const isVisited = visited.has(index);

    if (isAnswered && isMarked) return 'answered-marked';
    if (isAnswered) return 'answered';
    if (isMarked) return 'marked';
    if (isVisited) return 'not-answered';
    return 'not-visited';
  };

  // Calculate stats
  const stats = {
    answered: Object.keys(answers).filter(k => !markedForReview.has(parseInt(k))).length,
    notAnswered: allQuestions.length - Object.keys(answers).length - markedForReview.size,
    marked: Array.from(markedForReview).filter(k => answers.hasOwnProperty(k)).length,
    notVisited: allQuestions.length - visited.size
  };

  // Submit exam
  const handleAutoSubmit = () => {
    if (document.fullscreenElement) {
      document.exitFullscreen();
    }
    // Calculate results and redirect
    // router.push('/exam/results');
    alert('Exam auto-submitted!');
  };

  const handleSubmit = () => {
    setShowSubmitConfirm(true);
  };

  const confirmSubmit = () => {
    if (document.fullscreenElement) {
      document.exitFullscreen();
    }
    // Process results
    alert('Exam submitted successfully!');
    // router.push('/exam/results');
  };

  // Pre-exam instructions screen
  if (!isExamStarted) {
    // Check if exam has questions
    const hasQuestions = allQuestions && allQuestions.length > 0;
    
    return (
      <div className="min-h-screen bg-gray-100 p-8">
        <div className="max-w-4xl mx-auto bg-white rounded-lg shadow-lg p-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-6">{sampleExamData.title}</h1>
          
          <div className="mb-6 grid grid-cols-2 gap-4">
            <div className="bg-blue-50 p-4 rounded-lg">
              <div className="text-sm text-gray-600">Duration</div>
              <div className="text-2xl font-bold text-blue-600">{sampleExamData.duration} minutes</div>
            </div>
            <div className="bg-green-50 p-4 rounded-lg">
              <div className="text-sm text-gray-600">Total Marks</div>
              <div className="text-2xl font-bold text-green-600">{sampleExamData.totalMarks}</div>
            </div>
            <div className="bg-purple-50 p-4 rounded-lg">
              <div className="text-sm text-gray-600">Total Questions</div>
              <div className="text-2xl font-bold text-purple-600">{allQuestions.length}</div>
            </div>
            <div className="bg-orange-50 p-4 rounded-lg">
              <div className="text-sm text-gray-600">Sections</div>
              <div className="text-2xl font-bold text-orange-600">{sampleExamData.sections.length}</div>
            </div>
          </div>

          {!hasQuestions && (
            <div className="bg-red-100 border-l-4 border-red-500 p-4 mb-6">
              <p className="text-red-800 font-semibold">⚠️ No questions found for this exam!</p>
              <p className="text-red-700 text-sm">Please contact support or try another exam.</p>
            </div>
          )}

          <div className="bg-yellow-50 border-l-4 border-yellow-400 p-4 mb-6">
            <h3 className="font-bold text-yellow-800 mb-2">⚠️ Important Instructions</h3>
            <ul className="list-disc list-inside space-y-2 text-sm text-gray-800">
              <li>The exam will start in <strong>fullscreen mode</strong> and you cannot exit until submission</li>
              <li>Switching tabs or exiting fullscreen will trigger warnings</li>
              <li>After <strong>3 warnings</strong>, your exam will be auto-submitted</li>
              <li>Right-click, copy, and paste are disabled during the exam</li>
              <li>The timer will countdown automatically</li>
              <li>You can navigate between questions freely</li>
              <li>Mark questions for review to revisit them later</li>
              <li>Exam will auto-submit when time expires</li>
            </ul>
          </div>

          <div className="bg-gray-50 p-4 rounded-lg mb-6">
            <h3 className="font-semibold text-gray-900 mb-3">Question Status Guide:</h3>
            <div className="grid grid-cols-2 gap-3 text-sm">
              <div className="flex items-center">
                <div className="w-8 h-8 bg-green-500 rounded-full mr-3"></div>
                <span>Answered</span>
              </div>
              <div className="flex items-center">
                <div className="w-8 h-8 bg-red-500 rounded-full mr-3"></div>
                <span>Not Answered</span>
              </div>
              <div className="flex items-center">
                <div className="w-8 h-8 bg-gray-400 rounded-full mr-3"></div>
                <span>Not Visited</span>
              </div>
              <div className="flex items-center">
                <div className="w-8 h-8 bg-purple-500 rounded-full mr-3"></div>
                <span>Marked for Review</span>
              </div>
            </div>
          </div>

          <div className="flex justify-center space-x-4">
            <button
              onClick={() => {
                console.log('Start Exam button clicked');
                console.log('hasQuestions:', hasQuestions);
                console.log('allQuestions:', allQuestions);
                enterFullScreen();
              }}
              disabled={!hasQuestions}
              className={`font-bold py-4 px-8 rounded-lg text-lg transition duration-300 transform ${
                hasQuestions 
                  ? 'bg-indigo-600 hover:bg-indigo-700 text-white hover:scale-105 cursor-pointer'
                  : 'bg-gray-400 text-gray-200 cursor-not-allowed'
              }`}
            >
              {hasQuestions ? 'Start Exam (Fullscreen)' : 'No Questions Available'}
            </button>
            
            {/* Test button without fullscreen */}
            <button
              onClick={() => {
                console.log('Test Mode: Starting exam without fullscreen');
                setIsExamStarted(true);
              }}
              disabled={!hasQuestions}
              className={`font-bold py-4 px-8 rounded-lg text-lg transition duration-300 ${
                hasQuestions 
                  ? 'bg-green-600 hover:bg-green-700 text-white cursor-pointer'
                  : 'bg-gray-400 text-gray-200 cursor-not-allowed'
              }`}
            >
              Test Mode (No Fullscreen)
            </button>
          </div>
        </div>
      </div>
    );
  }

  const currentQ = allQuestions[currentQuestion];

  return (
    <div ref={examContainerRef} className="h-screen bg-gray-100 flex flex-col">
      {/* Warning Modal */}
      {showWarning && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-8 max-w-md">
            <h3 className="text-2xl font-bold text-red-600 mb-4">⚠️ Warning!</h3>
            <p className="text-gray-700 mb-4">
              You exited fullscreen or switched tabs. This is warning <strong>{warningCount}/3</strong>.
            </p>
            <p className="text-gray-700 mb-6">
              {warningCount >= 2 ? 'One more violation will auto-submit your exam!' : 'Please return to fullscreen to continue.'}
            </p>
            <button
              onClick={() => {
                setShowWarning(false);
                enterFullScreen();
              }}
              className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-3 px-6 rounded-lg"
            >
              Return to Exam
            </button>
          </div>
        </div>
      )}

      {/* Submit Confirmation Modal */}
      {showSubmitConfirm && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-8 max-w-md">
            <h3 className="text-2xl font-bold text-gray-900 mb-4">Confirm Submission</h3>
            <div className="mb-6 space-y-2 text-sm">
              <p><strong>Answered:</strong> {stats.answered} questions</p>
              <p><strong>Not Answered:</strong> {stats.notAnswered} questions</p>
              <p><strong>Marked for Review:</strong> {stats.marked} questions</p>
              <p><strong>Not Visited:</strong> {stats.notVisited} questions</p>
            </div>
            <p className="text-gray-700 mb-6">
              Are you sure you want to submit your exam? You cannot change answers after submission.
            </p>
            <div className="flex space-x-4">
              <button
                onClick={() => setShowSubmitConfirm(false)}
                className="flex-1 bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-3 px-6 rounded-lg"
              >
                Cancel
              </button>
              <button
                onClick={confirmSubmit}
                className="flex-1 bg-green-600 hover:bg-green-700 text-white font-bold py-3 px-6 rounded-lg"
              >
                Submit Exam
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Header */}
      <div className="bg-white shadow-md px-6 py-4 flex justify-between items-center">
        <div>
          <h1 className="text-xl font-bold text-gray-900">{sampleExamData.title}</h1>
          <p className="text-sm text-gray-600">Question {currentQuestion + 1} of {allQuestions.length}</p>
        </div>
        <div className="flex items-center space-x-6">
          <div className="text-right">
            <div className="text-sm text-gray-600">Time Remaining</div>
            <div className={`text-2xl font-bold ${timeRemaining < 300 ? 'text-red-600' : 'text-indigo-600'}`}>
              {formatTime(timeRemaining)}
            </div>
          </div>
        </div>
      </div>

      <div className="flex-1 flex overflow-hidden">
        {/* Question Panel */}
        <div className="flex-1 bg-white m-4 rounded-lg shadow-lg overflow-y-auto p-8">
          <div className="mb-6">
            <span className="inline-block bg-indigo-100 text-indigo-800 text-sm font-semibold px-3 py-1 rounded-full">
              {currentQ.section}
            </span>
            <span className="ml-2 text-sm text-gray-600">
              Marks: +{currentQ.marks} | {currentQ.negativeMarks}
            </span>
          </div>

          <div className="mb-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">
              Question {currentQuestion + 1}:
            </h3>
            <p className="text-gray-800 text-base leading-relaxed">{currentQ.text}</p>
          </div>

          <div className="space-y-3 mb-8">
            {currentQ.options.map((option, index) => (
              <label
                key={index}
                className={`flex items-center p-4 border-2 rounded-lg cursor-pointer transition-all ${
                  answers[currentQuestion] === index
                    ? 'border-indigo-600 bg-indigo-50'
                    : 'border-gray-300 hover:border-indigo-300'
                }`}
              >
                <input
                  type="radio"
                  name="answer"
                  checked={answers[currentQuestion] === index}
                  onChange={() => selectAnswer(index)}
                  className="w-5 h-5 text-indigo-600"
                />
                <span className="ml-3 text-gray-800">{option}</span>
              </label>
            ))}
          </div>

          {/* Action Buttons */}
          <div className="flex flex-wrap gap-3">
            <button
              onClick={saveAndNext}
              className="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-6 rounded-lg transition"
            >
              Save & Next
            </button>
            <button
              onClick={clearResponse}
              className="bg-gray-400 hover:bg-gray-500 text-white font-semibold py-2 px-6 rounded-lg transition"
            >
              Clear Response
            </button>
            <button
              onClick={markForReviewAndNext}
              className="bg-purple-600 hover:bg-purple-700 text-white font-semibold py-2 px-6 rounded-lg transition"
            >
              Mark for Review & Next
            </button>
          </div>
        </div>

        {/* Right Panel - Question Palette */}
        <div className="w-80 bg-white m-4 rounded-lg shadow-lg overflow-y-auto p-6">
          {/* Statistics */}
          <div className="mb-6">
            <h3 className="font-bold text-gray-900 mb-4">Question Status</h3>
            <div className="space-y-2 text-sm">
              <div className="flex justify-between items-center">
                <div className="flex items-center">
                  <div className="w-4 h-4 bg-green-500 rounded-full mr-2"></div>
                  <span>Answered</span>
                </div>
                <span className="font-semibold">{stats.answered}</span>
              </div>
              <div className="flex justify-between items-center">
                <div className="flex items-center">
                  <div className="w-4 h-4 bg-red-500 rounded-full mr-2"></div>
                  <span>Not Answered</span>
                </div>
                <span className="font-semibold">{stats.notAnswered}</span>
              </div>
              <div className="flex justify-between items-center">
                <div className="flex items-center">
                  <div className="w-4 h-4 bg-purple-500 rounded-full mr-2 "></div>
                  <span>Marked</span>
                </div>
                <span className="font-semibold">{stats.marked}</span>
              </div>
              <div className="flex justify-between items-center">
                <div className="flex items-center">
                  <div className="w-4 h-4 bg-gray-400 rounded-full mr-2"></div>
                  <span>Not Visited</span>
                </div>
                <span className="font-semibold">{stats.notVisited}</span>
              </div>
            </div>
          </div>

          {/* Question Grid */}
          {sampleExamData.sections.map((section, sectionIndex) => (
            <div key={sectionIndex} className="mb-6">
              <h4 className="font-semibold text-gray-900 mb-3">{section.name}</h4>
              <div className="grid grid-cols-5 gap-2">
                {section.questions.map((_, qIndex) => {
                  const globalIndex = sampleExamData.sections
                    .slice(0, sectionIndex)
                    .reduce((sum, s) => sum + s.questions.length, 0) + qIndex;
                  
                  const status = getQuestionStatus(globalIndex);
                  const statusColors = {
                    'answered': 'bg-green-500 text-white',
                    'not-answered': 'bg-red-500 text-white',
                    'marked': 'bg-purple-500 text-white',
                    'answered-marked': 'bg-purple-500 text-white',
                    'not-visited': 'bg-gray-300 text-gray-700'
                  };

                  return (
                    <button
                      key={qIndex}
                      onClick={() => goToQuestion(globalIndex)}
                      className={`w-10 h-10 rounded-lg font-semibold text-sm ${
                        statusColors[status]
                      } ${
                        currentQuestion === globalIndex ? 'ring-2 ring-offset-2 ring-indigo-600' : ''
                      } hover:opacity-80 transition`}
                    >
                      {globalIndex + 1}
                    </button>
                  );
                })}
              </div>
            </div>
          ))}

          {/* Navigation Buttons */}
          <div className="mt-6 space-y-3">
            <button
              onClick={previousQuestion}
              disabled={currentQuestion === 0}
              className="w-full bg-gray-600 hover:bg-gray-700 disabled:bg-gray-300 text-white font-semibold py-2 px-4 rounded-lg transition"
            >
              ← Previous
            </button>
            <button
              onClick={nextQuestion}
              disabled={currentQuestion === allQuestions.length - 1}
              className="w-full bg-indigo-600 hover:bg-indigo-700 disabled:bg-gray-300 text-white font-semibold py-2 px-4 rounded-lg transition"
            >
              Next →
            </button>
            <button
              onClick={handleSubmit}
              className="w-full bg-green-600 hover:bg-green-700 text-white font-bold py-3 px-4 rounded-lg transition"
            >
              Submit Exam
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
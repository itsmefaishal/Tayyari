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
  const [timeRemaining, setTimeRemaining] = useState(examData?.duration ? examData.duration * 60 : 180 * 60);
  const [isFullScreen, setIsFullScreen] = useState(false);
  const [showWarning, setShowWarning] = useState(false);
  const [warningCount, setWarningCount] = useState(0);
  const [showSubmitConfirm, setShowSubmitConfirm] = useState(false);
  const [isExamStarted, setIsExamStarted] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState(null);
  const [userId, setUserId] = useState(null);
  const [enrollmentId, setEnrollmentId] = useState(1); // TODO: Get from enrollment if needed
  
  const examContainerRef = useRef(null);
  const examStartTime = useRef(null);
  const examSubmitTime = useRef(null);

  // Use examData if available, otherwise fallback
  const sampleExamData = examData && examData.sections && examData.sections.length > 0 
    ? examData 
    : {
        title: "Sample Mock Test",
        duration: 180,
        totalMarks: 300,
        sections: [
          {
            name: "General",
            questions: [
              {
                id: 1,
                text: "Sample question",
                options: ["Option A", "Option B", "Option C", "Option D"],
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
    console.log('All Questions:', allQuestions);
    console.log('Number of questions:', allQuestions.length);
  }, []);

  useEffect(() => {
    console.log('isExamStarted changed:', isExamStarted);
    setUserId(localStorage.getItem('userId'));
  }, [isExamStarted]);

  // Timer effect
  useEffect(() => {
    if (!isExamStarted) return;

    // Record exam start time
    if (!examStartTime.current) {
      examStartTime.current = new Date().toISOString();
    }

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
    
    try {
      if (examContainerRef.current) {
        console.log('Attempting to enter fullscreen...');
        
        if (examContainerRef.current.requestFullscreen) {
          await examContainerRef.current.requestFullscreen();
        } else if (examContainerRef.current.webkitRequestFullscreen) {
          await examContainerRef.current.webkitRequestFullscreen();
        } else if (examContainerRef.current.mozRequestFullScreen) {
          await examContainerRef.current.mozRequestFullScreen();
        } else if (examContainerRef.current.msRequestFullscreen) {
          await examContainerRef.current.msRequestFullscreen();
        }
        
        setIsFullScreen(true);
        setIsExamStarted(true);
      }
    } catch (err) {
      console.error('Error entering fullscreen:', err);
      alert('Fullscreen not supported. Starting exam in normal mode.');
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
  const handleAutoSubmit = async () => {
    console.log('Auto-submitting exam...');
    await submitExam(true);
  };

  const handleSubmit = () => {
    setShowSubmitConfirm(true);
  };

  const confirmSubmit = async () => {
    setShowSubmitConfirm(false);
    await submitExam(false);
  };

  const submitExam = async (isAutoSubmit = false) => {
    try {
      setIsSubmitting(true);
      setSubmitError(null);

      // Exit fullscreen if active
      if (document.fullscreenElement) {
        await document.exitFullscreen();
      }

      // Generate unique key: timestamp_userId (add token if needed)
      const timestamp = Date.now();
      const uniqueKey = `${timestamp}_${userId}`;
      
      // Get submission time
      const submittedAt = new Date().toISOString();
      const startedAt = examStartTime.current || submittedAt;

      // Prepare answers in Pair format
      const listOfUserAttemptQuestionsAndAnswers = allQuestions.map((question, index) => {
        const selectedOptionIndex = answers[index];
        let selectedAnswer = '';
        
        // Convert index to actual answer text from options
        if (selectedOptionIndex !== undefined && selectedOptionIndex !== -1) {
          selectedAnswer = question.options[selectedOptionIndex] || '';
        }
        
        return {
          questionId: question.id || (index + 1), // Use question ID or fallback to index + 1
          content: question.text,
          selectedAnswer: selectedAnswer // Empty string for unanswered
        };
      });

      // Prepare submission payload matching QuizResponseDTO
      const quizResponseDTO = {
        userId: 2,
        quizId: sampleExamData.id,
        enrollementId: enrollmentId, // Note: backend uses 'enrollement' not 'enrollment'
        startedAt: startedAt,
        submittedAt: submittedAt,
        listOfUserAttemptQuestionsAndAnswers: listOfUserAttemptQuestionsAndAnswers
      };

      console.log('=== Submitting Exam ===');
      console.log('Unique Key:', uniqueKey);
      console.log('Quiz Response DTO:', quizResponseDTO);
      console.log('Number of questions:', allQuestions.length);
      console.log('Answered questions:', Object.keys(answers).length);

      // Submit to backend API
      const response = await fetch(`https://tayyari-ma4h.onrender.com/quiz/public/submit-quiz?uniqueKey=${encodeURIComponent(uniqueKey)}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          // 'Authorization': `Bearer ${token}` // Add if you have authentication
        },
        body: JSON.stringify(quizResponseDTO)
      });

      if (!response.ok) {
        const errorText = await response.text();
        console.error('Submission failed:', errorText);
        throw new Error(`Failed to submit exam: ${response.status} - ${errorText}`);
      }

      // Get QuizAttemptResponseDTO from backend
      const quizAttemptResponse = await response.json();
      console.log('=== Submission Successful ===');
      console.log('Quiz Attempt Response:', quizAttemptResponse);
      alert(quizAttemptResponse);
      // Store data in sessionStorage for results page
      sessionStorage.setItem('examResult', JSON.stringify(quizAttemptResponse));
      sessionStorage.setItem('examAnswers', JSON.stringify(answers));
      sessionStorage.setItem('examQuestions', JSON.stringify(allQuestions));
      sessionStorage.setItem('quizData', JSON.stringify(sampleExamData));

      // Redirect to results page using uniqueKey
      const resultKey = quizAttemptResponse.uniqueKey || uniqueKey;
      console.log('Redirecting to results page:', `/exam/result/${resultKey}`);
      
      // router.push(`/exam/result/${resultKey}`);
      router.push(`/dashboard`);

    } catch (error) {
      console.error('=== Error Submitting Exam ===');
      console.error('Error details:', error);
      setSubmitError(error.message);
      setIsSubmitting(false);
      
      // Show user-friendly error message
      alert(
        'Failed to submit exam!\n\n' +
        'Error: ' + error.message + '\n\n' +
        'Please check:\n' +
        '1. Backend server is running on localhost:8072\n' +
        '2. Network connection is stable\n' +
        '3. Check browser console for details\n\n' +
        'Click OK to try again.'
      );
    }
  };

  // Pre-exam instructions screen
  if (!isExamStarted) {
    const hasQuestions = allQuestions && allQuestions.length > 0;
    
    return (
      <div ref={examContainerRef} className="min-h-screen bg-gray-100 p-8">
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
            <ul className="list-disc list-inside space-y-2 text-sm text-yellow-900">
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
            <div className="grid grid-cols-2 gap-3 text-sm text-gray-700">
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
          <div className="bg-white rounded-lg p-8 max-w-md text-gray-700">
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
                disabled={isSubmitting}
                className="flex-1 bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-3 px-6 rounded-lg disabled:opacity-50"
              >
                Cancel
              </button>
              <button
                onClick={confirmSubmit}
                disabled={isSubmitting}
                className="flex-1 bg-green-600 hover:bg-green-700 text-white font-bold py-3 px-6 rounded-lg disabled:opacity-50 flex items-center justify-center"
              >
                {isSubmitting ? (
                  <>
                    <svg className="animate-spin h-5 w-5 mr-2" viewBox="0 0 24 24">
                      <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
                      <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
                    </svg>
                    Submitting...
                  </>
                ) : (
                  'Submit Exam'
                )}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Submitting Modal */}
      {isSubmitting && !showSubmitConfirm && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-8 max-w-md text-center">
            <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-indigo-600 mx-auto mb-4"></div>
            <h3 className="text-xl font-bold text-gray-900 mb-2">Submitting Your Exam</h3>
            <p className="text-gray-600">Please wait while we process your answers...</p>
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
            <div className="space-y-2 text-sm text-gray-700">
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
                  <div className="w-4 h-4 bg-purple-500 rounded-full mr-2"></div>
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
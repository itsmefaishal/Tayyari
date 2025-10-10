'use client';

import { useEffect, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import LiveExamInterface from '@/app/components/LiveExamInterface';
import { useAuth } from '@/contexts/AuthContext';

export default function LiveExamPage() {
  const params = useParams();
  const router = useRouter();
  const examId = params.id;
  const {user} = useAuth();
  
  const [examData, setExamData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchExamData = async () => {
      try {
        setLoading(true);
        
        // Fetch exam details
        const examRes = await fetch(`http://localhost:8072/quiz/public/get/${examId}`);
        if (!examRes.ok) throw new Error('Failed to fetch exam details');
        const exam = await examRes.json();

        console.log('Exam data:', exam);

        // Check if exam has question IDs
        if (!exam.question || exam.question.length === 0) {
          throw new Error('No questions found for this exam');
        }
        

        // Fetch questions using POST method with question IDs
        // const questionsRes = await fetch('http://localhost:8085/question/getMultipleQuestions', {
        //   method: 'POST',
        //   headers: {
        //     'Content-Type': 'application/json',
        //     // 'Authorization': `Bearer ${user}`,
        //   },
        //   body: JSON.stringify(exam.question)
        // });

        // if (!questionsRes.ok) throw new Error('Failed to fetch questions');
        const questions = exam.question;

        console.log('Questions data:', questions);

        // Transform the data to match LiveExamInterface format
        const formattedExamData = {
          id: exam.quiz.id,
          title: exam.quiz.title,
          description: exam.quiz.description,
          duration: exam.quiz.duration || 180, // minutes
          totalMarks: exam.quiz.totalMarks || calculateTotalMarks(questions),
          sections: 1
        };

        setExamData(formattedExamData);
      } catch (err) {
        console.error('Error fetching exam:', err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    if (examId) {
      fetchExamData();
    }
  }, [examId]);

  // Helper function to calculate total marks if not provided
  const calculateTotalMarks = (questions) => {
    return questions.reduce((total, q) => total + (q.marks || 4), 0);
  };

  // Helper function to format questions by section
  const formatQuestionsBySection = (questions) => {
    // If questions already have sections, group them
    const sectionMap = {};
    
    questions.forEach(q => {
      const sectionName = q.section || q.subject || 'General';
      
      if (!sectionMap[sectionName]) {
        sectionMap[sectionName] = [];
      }
      
      sectionMap[sectionName].push({
        id: q.id,
        text: q.questionText || q.text,
        options: q.options || [q.option1, q.option2, q.option3, q.option4].filter(Boolean),
        correctAnswer: q.correctAnswer || 0,
        marks: q.marks || 4,
        negativeMarks: q.negativeMarks || -1,
        image: q.imageUrl || null
      });
    });

    // Convert map to array format
    return Object.entries(sectionMap).map(([name, questions]) => ({
      name,
      questions
    }));
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-indigo-600 mx-auto mb-4"></div>
          <p className="text-lg text-gray-700">Loading exam...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center p-6">
        <div className="bg-white rounded-lg shadow-xl p-8 max-w-md text-center">
          <div className="w-16 h-16 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg className="w-8 h-8 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
            </svg>
          </div>
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Error Loading Exam</h2>
          <p className="text-gray-600 mb-6">{error}</p>
          <button
            onClick={() => router.push('/exams')}
            className="bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-6 rounded-lg transition"
          >
            Back to Exams
          </button>
        </div>
      </div>
    );
  }

  if (!examData) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center">
        <div className="text-center">
          <p className="text-lg text-gray-700">Exam not found</p>
          <button
            onClick={() => router.push('/exams')}
            className="mt-4 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-6 rounded-lg transition"
          >
            Back to Exams
          </button>
        </div>
      </div>
    );
  }

  return <LiveExamInterface examData={examData} />;
}
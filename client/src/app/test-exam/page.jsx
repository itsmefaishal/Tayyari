'use client';

import { useState } from 'react';
import LiveExamInterface from '../components/LiveExamInterface';

export default function DebugExamTest() {
  const [startExam, setStartExam] = useState(false);

  // Sample test data
  const testExamData = {
    id: 1,
    title: "Test Exam - Sample",
    description: "This is a test exam to debug the interface",
    duration: 10, // 10 minutes for testing
    totalMarks: 40,
    sections: [
      {
        name: "Physics",
        questions: [
          {
            id: 1,
            text: "What is the SI unit of force?",
            options: ["Newton", "Joule", "Watt", "Pascal"],
            correctAnswer: 0,
            marks: 4,
            negativeMarks: -1
          },
          {
            id: 2,
            text: "What is the speed of light in vacuum?",
            options: ["3 × 10⁸ m/s", "3 × 10⁶ m/s", "3 × 10⁷ m/s", "3 × 10⁹ m/s"],
            correctAnswer: 0,
            marks: 4,
            negativeMarks: -1
          },
          {
            id: 3,
            text: "Which law states that force equals mass times acceleration?",
            options: ["Newton's First Law", "Newton's Second Law", "Newton's Third Law", "Law of Gravitation"],
            correctAnswer: 1,
            marks: 4,
            negativeMarks: -1
          }
        ]
      },
      {
        name: "Chemistry",
        questions: [
          {
            id: 4,
            text: "What is the atomic number of Carbon?",
            options: ["6", "12", "14", "8"],
            correctAnswer: 0,
            marks: 4,
            negativeMarks: -1
          },
          {
            id: 5,
            text: "What is the chemical formula of water?",
            options: ["H2O", "CO2", "O2", "H2O2"],
            correctAnswer: 0,
            marks: 4,
            negativeMarks: -1
          }
        ]
      },
      {
        name: "Mathematics",
        questions: [
          {
            id: 6,
            text: "What is the value of π (pi) approximately?",
            options: ["3.14", "2.71", "1.41", "1.73"],
            correctAnswer: 0,
            marks: 4,
            negativeMarks: -1
          },
          {
            id: 7,
            text: "What is 2 + 2?",
            options: ["4", "5", "3", "6"],
            correctAnswer: 0,
            marks: 4,
            negativeMarks: -1
          },
          {
            id: 8,
            text: "What is the square root of 144?",
            options: ["12", "14", "10", "16"],
            correctAnswer: 0,
            marks: 4,
            negativeMarks: -1
          },
          {
            id: 9,
            text: "What is 10 × 10?",
            options: ["100", "1000", "10", "50"],
            correctAnswer: 0,
            marks: 4,
            negativeMarks: -1
          },
          {
            id: 10,
            text: "What is 15 ÷ 3?",
            options: ["5", "3", "6", "4"],
            correctAnswer: 0,
            marks: 4,
            negativeMarks: -1
          }
        ]
      }
    ]
  };

  if (startExam) {
    return <LiveExamInterface examData={testExamData} />;
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 p-8">
      <div className="max-w-2xl mx-auto bg-white rounded-lg shadow-xl p-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-4">Debug Test Page</h1>
        <p className="text-gray-600 mb-6">
          This is a test page with sample data to debug the Live Exam Interface.
        </p>

        <div className="bg-blue-50 border-l-4 border-blue-500 p-4 mb-6">
          <h3 className="font-semibold text-blue-900 mb-2">Test Exam Details:</h3>
          <ul className="text-sm text-blue-800 space-y-1">
            <li>• <strong>Title:</strong> {testExamData.title}</li>
            <li>• <strong>Duration:</strong> {testExamData.duration} minutes</li>
            <li>• <strong>Total Marks:</strong> {testExamData.totalMarks}</li>
            <li>• <strong>Sections:</strong> {testExamData.sections.length}</li>
            <li>• <strong>Total Questions:</strong> {testExamData.sections.reduce((sum, s) => sum + s.questions.length, 0)}</li>
          </ul>
        </div>

        <div className="bg-yellow-50 border-l-4 border-yellow-400 p-4 mb-6">
          <h3 className="font-semibold text-yellow-900 mb-2">⚠️ Testing Instructions:</h3>
          <ul className="text-sm text-yellow-800 space-y-1">
            <li>1. Click "Start Test Exam" below</li>
            <li>2. Check browser console for debug logs</li>
            <li>3. Try clicking "Start Exam" button</li>
            <li>4. Check if fullscreen activates</li>
            <li>5. Check if exam interface loads</li>
          </ul>
        </div>

        <div className="space-y-3">
          <button
            onClick={() => {
              console.log('Starting test exam...');
              console.log('Exam Data:', testExamData);
              setStartExam(true);
            }}
            className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-4 px-8 rounded-lg text-lg transition duration-300 transform hover:scale-105"
          >
            Start Test Exam
          </button>

          <button
            onClick={() => {
              console.log('Test Exam Data:', testExamData);
              console.log('Number of questions:', testExamData.sections.reduce((sum, s) => sum + s.questions.length, 0));
              alert('Check browser console for exam data');
            }}
            className="w-full bg-gray-600 hover:bg-gray-700 text-white font-semibold py-3 px-6 rounded-lg transition"
          >
            Console Log Exam Data
          </button>
        </div>

        <div className="mt-6 p-4 bg-gray-50 rounded-lg">
          <h4 className="font-semibold text-gray-900 mb-2">Troubleshooting:</h4>
          <ul className="text-sm text-gray-700 space-y-1">
            <li>• Open browser console (F12) to see debug logs</li>
            <li>• Check if fullscreen is blocked by browser</li>
            <li>• Make sure you're testing in a modern browser</li>
            <li>• Try allowing fullscreen permissions</li>
            <li>• Check if exam data is properly formatted</li>
          </ul>
        </div>
      </div>
    </div>
  );
}
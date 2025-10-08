'use client';

import Navbar from '@/comps/Navbar';
import { useEffect, useState } from 'react';

export default function ExamPage() {
  const [exams, setExams] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);

    const response = async () => {
        try{
            const res = await fetch('https://tayyari-ma4h.onrender.com/quiz/public/get/7');

            if (!res.ok) throw new Error('Failed to fetch exams');

            const data = await res.json();
            console.log(data.quiz);
            setExams(data.quiz);
        }
        catch (error) {
            console.error('Error fetching exams:', error);
        } 
        finally {
            setLoading(false);
        }
    }
    response();
  }, []);
  

  if (loading) {
    return <div className="text-center mt-10">Loading exams...</div>;
  }

  return (
    <>
      <Navbar />
      <div className="min-h-screen bg-gray-50 p-6">
        <h1 className="text-3xl font-bold text-center text-gray-800 mb-6">Available Exams</h1>
        <div className="max-w-3xl mx-auto space-y-4">
          {exams ? (
            // exams.map((exam) => (
              <div key={exams.id} className="bg-white p-4 rounded-lg shadow hover:shadow-md transition-shadow">
                <h2 className="text-xl font-semibold text-gray-800">{exams.title}</h2>
                <p className="text-sm text-gray-600">{exams.description}</p>
              </div>
            // ))
          ) : (
            <p className="text-center text-gray-600">No exams available at the moment.</p>
          )}
        </div>
      </div>
    </>
  );
}

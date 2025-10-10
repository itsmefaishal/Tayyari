'use client';

import Navbar from '@/comps/Navbar';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

export default function ExamPage() {
  const router = useRouter();
  const [exams, setExams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [error, setError] = useState(null);

  const fetchExams = async (page) => {
    setLoading(true);
    setError(null);
    
    try {
      const res = await fetch(`https://tayyari-ma4h.onrender.com/quiz/public/get-multiple-quiz?page=${page}`);
      
      if (!res.ok) throw new Error('Failed to fetch exams');

      const data = await res.json();
      console.log('Fetched data:', data);
      
      // Check if we got less than 13 items (page size), meaning no more pages
      if (data.length < 13) {
        setHasMore(false);
      } else {
        setHasMore(true);
      }
      
      setExams(data);
    } catch (error) {
      console.error('Error fetching exams:', error);
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchExams(currentPage);
  }, [currentPage]);

  const handleNextPage = () => {
    if (hasMore) {
      setCurrentPage(prev => prev + 1);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      setCurrentPage(prev => prev - 1);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  };

  const handleExamClick = (exam) => {
    // Navigate to live exam interface with exam ID
    router.push(`/exam/live/${exam.id}`);
  };

  if (loading && currentPage === 0) {
    return (
      <>
        <Navbar />
        <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center">
          <div className="text-center">
            <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-indigo-600 mx-auto mb-4"></div>
            <p className="text-lg text-gray-700">Loading exams...</p>
          </div>
        </div>
      </>
    );
  }

  return (
    <>
      <Navbar />
      <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 p-6">
        <div className="max-w-6xl mx-auto">
          <div className="text-center mb-8">
            <h1 className="text-4xl font-bold text-gray-900 mb-2">Available Exams</h1>
            <p className="text-gray-600">Select an exam to start your test</p>
          </div>

          {error && (
            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">
              <p className="font-semibold">Error loading exams</p>
              <p>{error}</p>
            </div>
          )}

          {loading ? (
            <div className="text-center py-12">
              <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600 mx-auto mb-4"></div>
              <p className="text-gray-600">Loading exams...</p>
            </div>
          ) : exams && exams.length > 0 ? (
            <>
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
                {exams.map((exam) => (
                  <div
                    key={exam.id}
                    onClick={() => handleExamClick(exam)}
                    className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 cursor-pointer transform hover:-translate-y-1 border border-gray-200 hover:border-indigo-300"
                  >
                    <div className="flex items-start justify-between mb-4">
                      <div className="flex-1">
                        <h2 className="text-xl font-bold text-gray-900 mb-2 line-clamp-2">
                          {exam.title}
                        </h2>
                        <p className="text-sm text-gray-600 mb-4 line-clamp-3">
                          {exam.description || 'No description available'}
                        </p>
                      </div>
                      <div className="ml-2">
                        <svg className="w-6 h-6 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                        </svg>
                      </div>
                    </div>

                    <div className="space-y-2 mb-4">
                      {exam.duration && (
                        <div className="flex items-center text-sm text-gray-600">
                          <svg className="w-4 h-4 mr-2 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                          </svg>
                          <span>Duration: {exam.duration} minutes</span>
                        </div>
                      )}
                      {exam.totalMarks && (
                        <div className="flex items-center text-sm text-gray-600">
                          <svg className="w-4 h-4 mr-2 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z" />
                          </svg>
                          <span>Total Marks: {exam.totalMarks}</span>
                        </div>
                      )}
                      {exam.numberOfQuestions && (
                        <div className="flex items-center text-sm text-gray-600">
                          <svg className="w-4 h-4 mr-2 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                          </svg>
                          <span>Questions: {exam.numberOfQuestions}</span>
                        </div>
                      )}
                      {exam.difficulty && (
                        <div className="flex items-center text-sm">
                          <span className={`px-2 py-1 rounded-full text-xs font-semibold ${
                            exam.difficulty === 'EASY' ? 'bg-green-100 text-green-800' :
                            exam.difficulty === 'MEDIUM' ? 'bg-yellow-100 text-yellow-800' :
                            'bg-red-100 text-red-800'
                          }`}>
                            {exam.difficulty}
                          </span>
                        </div>
                      )}
                    </div>

                    <button className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-4 rounded-lg transition duration-200">
                      Start Exam
                    </button>
                  </div>
                ))}
              </div>

              {/* Pagination Controls */}
              <div className="flex items-center justify-center space-x-4 pb-8">
                <button
                  onClick={handlePreviousPage}
                  disabled={currentPage === 0}
                  className={`flex items-center px-6 py-3 rounded-lg font-semibold transition-all duration-200 ${
                    currentPage === 0
                      ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
                      : 'bg-white text-indigo-600 hover:bg-indigo-50 shadow-md hover:shadow-lg'
                  }`}
                >
                  <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
                  </svg>
                  Previous
                </button>

                <div className="bg-white px-6 py-3 rounded-lg shadow-md">
                  <span className="text-gray-700 font-semibold">
                    Page {currentPage + 1}
                  </span>
                </div>

                <button
                  onClick={handleNextPage}
                  disabled={!hasMore}
                  className={`flex items-center px-6 py-3 rounded-lg font-semibold transition-all duration-200 ${
                    !hasMore
                      ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
                      : 'bg-indigo-600 text-white hover:bg-indigo-700 shadow-md hover:shadow-lg'
                  }`}
                >
                  Next
                  <svg className="w-5 h-5 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                  </svg>
                </button>
              </div>
            </>
          ) : (
            <div className="text-center py-12 bg-white rounded-xl shadow-lg">
              <svg className="w-16 h-16 text-gray-400 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
              </svg>
              <p className="text-xl text-gray-600">No exams available at the moment.</p>
              <p className="text-sm text-gray-500 mt-2">Please check back later.</p>
            </div>
          )}
        </div>
      </div>
    </>
  );
}
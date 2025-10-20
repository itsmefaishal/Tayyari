'use client';
import Navbar from '@/comps/Navbar';
import Link from 'next/link';

export default function AboutPage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex flex-col">
      <Navbar />

      <main className="flex-grow max-w-5xl mx-auto px-6 py-16">
        <h1 className="text-4xl sm:text-5xl font-extrabold text-center text-gray-900 mb-8">
          About <span className="text-indigo-600">Tayyari</span>
        </h1>

        <p className="text-lg text-gray-700 text-center max-w-3xl mx-auto mb-12">
          Tayyari is your personalized online test preparation and skill assessment platform designed
          to help students master competitive exams with confidence. From JEE and NEET to SSC and UPSC,
          we make learning interactive, measurable, and enjoyable.
        </p>

        <div className="grid md:grid-cols-2 gap-10 mt-10">
          <div className="bg-white rounded-xl shadow-md p-8">
            <h2 className="text-2xl font-bold text-gray-900 mb-4">🚀 Our Mission</h2>
            <p className="text-gray-700 leading-relaxed">
              To empower learners across India with accessible, effective, and data-driven
              preparation tools. We believe every student deserves equal access to quality practice,
              real-time feedback, and performance analytics to reach their full potential.
            </p>
          </div>

          <div className="bg-white rounded-xl shadow-md p-8">
            <h2 className="text-2xl font-bold text-gray-900 mb-4">💡 What Makes Us Different</h2>
            <ul className="list-disc list-inside text-gray-700 space-y-2">
              <li>AI-enhanced test generation and performance insights.</li>
              <li>Comprehensive test series for multiple competitive exams.</li>
              <li>Beautiful, distraction-free interface for focused learning.</li>
              <li>Instant feedback and detailed score analytics.</li>
            </ul>
          </div>
        </div>

        <div className="mt-16 bg-white rounded-2xl shadow-xl p-10 text-center">
          <h2 className="text-3xl font-bold text-indigo-700 mb-4">Our Vision</h2>
          <p className="text-gray-700 text-lg max-w-2xl mx-auto">
            We aim to build India’s most trusted and intelligent online testing ecosystem — 
            one that motivates learners, simplifies progress tracking, and personalizes preparation
            for every goal, from high school exams to professional certifications.
          </p>
        </div>

        <div className="mt-16 text-center">
          <h3 className="text-2xl font-semibold text-gray-900 mb-6">Join Thousands of Learners</h3>
          <p className="text-gray-600 mb-8">
            Whether you&apos;re preparing for your next exam or sharpening your skills, Tayyari is here
            to guide you every step of the way.
          </p>
          <Link
            href="/signup"
            className="bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-3 px-8 rounded-lg text-lg transition duration-300 ease-in-out transform hover:scale-105"
          >
            Start Learning Free
          </Link>
        </div>
      </main>

      <footer className="bg-gray-800 text-white py-8 mt-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <p className="text-gray-300">
            &copy; 2024 Tayyari. Built with ❤️ by learners, for learners.
          </p>
        </div>
      </footer>
    </div>
  );
}

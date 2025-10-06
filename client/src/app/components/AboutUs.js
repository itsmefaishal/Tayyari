'use client';
import { useState } from 'react';
import Link from 'next/link';

export default function AboutPage() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const milestones = [
    { year: "2024", event: "Founded Tayyari with a vision to democratize test preparation" },
    { year: "2025", event: "Launched JEE and NEET preparation modules" },
    { year: "2025", event: "Expanded to SSC and other competitive exams" },
    { year: "2025", event: "more to come..." }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      {/* Navigation */}
      <nav className="bg-white shadow-lg">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-16">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <Link href="/" className="text-2xl font-bold text-indigo-600">Tayyari</Link>
              </div>
            </div>
            
            {/* Desktop Menu */}
            <div className="hidden md:flex items-center space-x-4">
              <Link href="/exams" className="text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-sm font-medium">
                Exams
              </Link>
              <Link href="/aboutus" className="text-indigo-600 px-3 py-2 rounded-md text-sm font-medium border-b-2 border-indigo-600">
                About
              </Link>
              <Link href="/contactus" className="text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-sm font-medium">
                Contact Us
              </Link>
              <Link href="/login" className="text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-sm font-medium">
                Login
              </Link>
              <Link href="/signup" className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md text-sm font-medium">
                Sign Up
              </Link>
            </div>

            {/* Mobile menu button */}
            <div className="md:hidden flex items-center">
              <button
                onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
                className="text-gray-700 hover:text-indigo-600 focus:outline-none focus:text-indigo-600"
              >
                <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
                </svg>
              </button>
            </div>
          </div>
        </div>

        {/* Mobile Menu */}
        {mobileMenuOpen && (
          <div className="md:hidden">
            <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3 bg-white border-t">
              <Link href="/exams" className="block text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-base font-medium">
                Exams
              </Link>
              <Link href="/about" className="block text-indigo-600 px-3 py-2 rounded-md text-base font-medium">
                About
              </Link>
              <Link href="/contact" className="block text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-base font-medium">
                Contact Us
              </Link>
              <Link href="/login" className="block text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-base font-medium">
                Login
              </Link>
              <Link href="/signup" className="block text-indigo-600 hover:text-indigo-800 px-3 py-2 rounded-md text-base font-medium">
                Sign Up
              </Link>
            </div>
          </div>
        )}
      </nav>

      {/* ... rest of your JSX ... */}

      <div className="bg-indigo-600 rounded-2xl shadow-xl p-8 text-center text-white">
        <h2 className="text-3xl font-bold mb-4">Ready to Start Your Journey?</h2>
        <p className="text-indigo-100 mb-6 max-w-2xl mx-auto">
          Join thousands of successful students who have achieved their dreams with Tayyari. 
          Begin your preparation today and take the first step towards your goals.
        </p>
        <Link 
          href="/signup"
          className="bg-white text-indigo-600 font-bold py-3 px-8 rounded-lg text-lg hover:bg-gray-100 transition duration-300 inline-block"
        >
          Get Started Free
        </Link>
      </div>

      {/* Footer */}
      <footer className="bg-gray-800 text-white py-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div>
              <h3 className="text-xl font-bold mb-4">Tayyari</h3>
              <p className="text-gray-300">Your ultimate destination for online testing and skill assessment.</p>
            </div>
            <div>
              <h4 className="text-lg font-semibold mb-4">Quick Links</h4>
              <ul className="space-y-2">
                <li><Link href="/aboutus" className="text-gray-300 hover:text-white">About Us</Link></li>
                <li><Link href="/contactus" className="text-gray-300 hover:text-white">Contact</Link></li>
                <li><Link href="/" className="text-gray-300 hover:text-white">Help Center</Link></li>
              </ul>
            </div>
            <div>
              <h4 className="text-lg font-semibold mb-4">Contact Info</h4>
              <p className="text-gray-300">Email: support@tayyari.com</p>
              <p className="text-gray-300">Phone: (555) 123-4567</p>
            </div>
          </div>
          <div className="border-t border-gray-700 mt-8 pt-8 text-center">
            <p className="text-gray-300">&copy; 2024 Tayyari. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  );
}

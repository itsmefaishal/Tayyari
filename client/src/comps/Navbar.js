'use client';
import { useState } from 'react';
import Link from 'next/link';
import { useAuth } from '@/contexts/AuthContext';
import { useRouter, usePathname } from 'next/navigation';
import Cookies from 'js-cookie';

export default function Navbar() {
  const { user, logout, isAuthenticated } = useAuth();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const router = useRouter();
  const pathname = usePathname();

  const handleLogout = () => {
    console.log("logout clicked");
    logout();
    localStorage.clear();
    Cookies.remove('token');
    router.push('/');
  };

  // Helper function for active link style
  const linkClass = (href) =>
    `px-3 py-2 rounded-md text-sm font-medium transition ${
      pathname === href
        ? "text-indigo-600 border-b-2 border-indigo-600"
        : "text-gray-700 hover:text-indigo-600"
    }`;

  return (
    <nav className="bg-white shadow-lg">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          {/* Logo */}
          <div className="flex items-center">
            <Link href="/" className="text-2xl font-bold text-indigo-600">Tayyari</Link>
          </div>

          {/* Desktop Menu */}
          <div className="hidden md:flex items-center space-x-4">
            <Link href="/exams" className={linkClass("/exams")}>
              Exams
            </Link>
            <Link href="/aboutus" className={linkClass("/aboutus")}>
              About
            </Link>
            <Link href="/contactus" className={linkClass("/contactus")}>
              Contact Us
            </Link>

            {console.log(isAuthenticated)}

            {isAuthenticated ? (
              <>
                <Link href="/dashboard" className={linkClass("/dashboard")}>
                  Dashboard
                </Link>
                <Link href="/tests" className={linkClass("/tests")}>
                  My Tests
                </Link>
                <button 
                  onClick={handleLogout}
                  className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-md text-sm font-medium"
                >
                  Logout
                </button>
              </>
            ) : (
              <>
                <Link href="/login" className={linkClass("/login")}>
                  Login
                </Link>
                <Link href="/signup" className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md text-sm font-medium">
                  Sign Up
                </Link>
              </>
            )}
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
            <Link href="/exams" className={linkClass("/exams")}>
              Exams
            </Link>
            <Link href="/aboutus" className={linkClass("/aboutus")}>
              About
            </Link>
            <Link href="/contactus" className={linkClass("/contactus")}>
              Contact Us
            </Link>

            {isAuthenticated ? (
              <>
                <Link href="/dashboard" className={linkClass("/dashboard")}>
                  Dashboard
                </Link>
                <Link href="/tests" className={linkClass("/tests")}>
                  My Tests
                </Link>
                <button 
                  onClick={handleLogout}
                  className="block w-full text-left text-red-600 hover:text-red-800 px-3 py-2 rounded-md text-base font-medium"
                >
                  Logout
                </button>
              </>
            ) : (
              <>
                <Link href="/login" className={linkClass("/login")}>
                  Login
                </Link>
                <Link href="/signup" className="block text-indigo-600 hover:text-indigo-800 px-3 py-2 rounded-md text-base font-medium">
                  Sign Up
                </Link>
              </>
            )}
          </div>
        </div>
      )}
    </nav>
  );
}

'use client';
import { useState, useEffect } from 'react';
import Link from 'next/link';
import { useAuth } from '@/contexts/AuthContext';
import { useRouter, usePathname } from 'next/navigation';
import Cookies from 'js-cookie';

export default function Navbar() {
  const { user, logout } = useAuth();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const router = useRouter();
  const pathname = usePathname();
  const [isAuth, setIsAuth] = useState(false);

  const handleLogout = () => {
    console.log("logout clicked");
    logout();
    localStorage.clear();
    Cookies.remove('token');
    setIsAuth(false);
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
          <div className={`hidden md:flex items-center space-x-4 ${mobileMenuOpen ? "hidden" : ""}`}>
            <Link href="/exams" className={linkClass("/exams")}>
              Exams
            </Link>
            <Link href="/aboutus" className={linkClass("/aboutus")}>
              About
            </Link>
            <Link href="/contactus" className={linkClass("/contactus")}>
              Contact Us
            </Link>

            {user ? (
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
                <Link 
                href="/signup"
                className="bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-3 px-8 rounded-lg text-m"
              >
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
      {/* Mobile Sidebar / Drawer */}
<div className={`fixed inset-0 z-50 flex ${mobileMenuOpen ? '' : 'pointer-events-none'}`}>
  {/* Overlay */}
  <div
    className={`fixed inset-0 bg-gray-900 bg-opacity-30 backdrop-blur-sm transition-opacity duration-300 ${
    mobileMenuOpen ? 'opacity-75' : 'opacity-0'
  }`}
    onClick={() => setMobileMenuOpen(false)}
  ></div>

  {/* Sidebar */}
  <div
    className={`relative ml-auto w-64 max-w-full bg-white shadow-xl transform transition-transform duration-300 ease-in-out ${
      mobileMenuOpen ? 'translate-x-0' : 'translate-x-full'
    }`}
  >
    {/* Close button */}
    <div className="absolute top-0 left-0 pt-4 pl-4">
      <button
        onClick={() => setMobileMenuOpen(false)}
        className="text-gray-600 hover:text-gray-900 focus:outline-none"
      >
        <svg
          className="h-6 w-6"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>
    </div>

    {/* Menu */}
    <nav className="mt-16 px-6 space-y-4">
      <Link
        href="/exams"
        className="block text-gray-700 text-lg font-medium hover:text-indigo-600"
        onClick={() => setMobileMenuOpen(false)}
      >
        Exams
      </Link>
      <Link
        href="/aboutus"
        className="block text-gray-700 text-lg font-medium hover:text-indigo-600"
        onClick={() => setMobileMenuOpen(false)}
      >
        About
      </Link>
      <Link
        href="/contactus"
        className="block text-gray-700 text-lg font-medium hover:text-indigo-600"
        onClick={() => setMobileMenuOpen(false)}
      >
        Contact Us
      </Link>

      {user ? (
        <>
          <Link
            href="/dashboard"
            className="block text-gray-700 text-lg font-medium hover:text-indigo-600"
            onClick={() => setMobileMenuOpen(false)}
          >
            Dashboard
          </Link>
          <Link
            href="/tests"
            className="block text-gray-700 text-lg font-medium hover:text-indigo-600"
            onClick={() => setMobileMenuOpen(false)}
          >
            My Tests
          </Link>
          <button
            onClick={() => {
              handleLogout();
              setMobileMenuOpen(false);
            }}
            className="w-full text-left text-red-600 hover:text-red-800 text-lg font-medium mt-2"
          >
            Logout
          </button>
        </>
      ) : (
        <>
          <Link
            href="/login"
            className="block text-gray-700 text-lg font-medium hover:text-indigo-600"
            onClick={() => setMobileMenuOpen(false)}
          >
            Login
          </Link>
          <Link
            href="/signup"
            className="block mt-2 text-center bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-3 rounded-md transition duration-300"
            onClick={() => setMobileMenuOpen(false)}
          >
            Get Started Free
          </Link>
        </>
      )}
    </nav>
  </div>
</div>

    </nav>
  );
}

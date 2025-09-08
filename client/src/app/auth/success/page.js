'use client';
import { useEffect, useState } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';

export default function AuthSuccess() {
  const [status, setStatus] = useState('processing');
  const [error, setError] = useState('');
  const router = useRouter();
  const searchParams = useSearchParams();

  useEffect(() => {
    const handleAuthCallback = async () => {
      try {
        // Get token from URL parameter
        const token = searchParams.get('token');
        const errorParam = searchParams.get('error');
        const name = searchParams.get('name');

        if (errorParam) {
          let errorMessage = 'Authentication failed';
          switch (errorParam) {
            case 'oauth_failed':
              errorMessage = 'Google authentication failed. Please try again.';
              break;
            case 'server_error':
              errorMessage = 'Server error occurred. Please try again later.';
              break;
            default:
              errorMessage = 'Authentication failed. Please try again.';
          }
          
          setError(errorMessage);
          setStatus('error');
          setTimeout(() => router.push('/login'), 3000);
          return;
        }

        if (token) {
          // Store JWT token in cookie and localStorage
          document.cookie = `token=${token}; path=/; max-age=${7 * 24 * 60 * 60}; secure; samesite=strict`;
          localStorage.setItem('token', token);
          localStorage.setItem('name', name);
          
          setStatus('success');
          
          setTimeout(() => router.push('/dashboard'), 1500);
          router.push('/dashboard',)
        } else {
          setError('No authentication token received');
          setStatus('error');
          setTimeout(() => router.push('/login'), 3000);
        }
      } catch (err) {
        console.error('Auth success page error:', err);
        setError('An unexpected error occurred');
        setStatus('error');
        setTimeout(() => router.push('/login'), 3000);
      }
    };

    handleAuthCallback();
  }, [searchParams, router]);

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center">
      <div className="bg-white p-8 rounded-xl shadow-lg text-center max-w-md w-full mx-4">
        {status === 'processing' && (
          <div>
            <svg className="animate-spin h-12 w-12 mx-auto mb-4 text-indigo-600" fill="none" viewBox="0 0 24 24">
              <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
              <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            <h2 className="text-xl font-semibold text-gray-900 mb-2">Completing your login...</h2>
            <p className="text-gray-600">Please wait while we finalize your authentication.</p>
          </div>
        )}

        {status === 'success' && (
          <div>
            <div className="mx-auto flex items-center justify-center h-12 w-12 rounded-full bg-green-100 mb-4">
              <svg className="h-6 w-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
              </svg>
            </div>
            <h2 className="text-xl font-semibold text-gray-900 mb-2">Login successful!</h2>
            <p className="text-gray-600">Redirecting you to your dashboard...</p>
          </div>
        )}

        {status === 'error' && (
          <div>
            <div className="mx-auto flex items-center justify-center h-12 w-12 rounded-full bg-red-100 mb-4">
              <svg className="h-6 w-6 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
            <h2 className="text-xl font-semibold text-gray-900 mb-2">Authentication failed</h2>
            <p className="text-gray-600 mb-4">{error}</p>
            <p className="text-sm text-gray-500">Redirecting you back to login...</p>
          </div>
        )}
      </div>
    </div>
  );
}
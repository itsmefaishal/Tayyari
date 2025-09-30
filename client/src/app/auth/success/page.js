'use client';
import { useEffect, useState } from 'react';
import { useSearchParams, useRouter } from 'next/navigation';
import { useAuth } from '@/contexts/AuthContext';

export default function AuthSuccessPage() {
    const searchParams = useSearchParams();
    const router = useRouter();
    const { handleGoogleCallback } = useAuth();
    const [processing, setProcessing] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const handleCallback = async () => {
            try {
                const token = searchParams.get('token');
                const error = searchParams.get('error');

                if (error) {
                    let errorMessage = 'Authentication failed';
                    switch (error) {
                        case 'oauth_failed':
                            errorMessage = 'Google OAuth authentication failed. Please try again.';
                            break;
                        case 'server_error':
                            errorMessage = 'Server error occurred during authentication. Please try again later.';
                            break;
                        default:
                            errorMessage = 'Authentication failed. Please try again.';
                    }
                    setError(errorMessage);
                    setProcessing(false);
                    
                    // Redirect to login after showing error
                    setTimeout(() => {
                        router.push('/login');
                    }, 3000);
                    return;
                }

                if (token) {
                    // Handle the Google OAuth callback
                    await handleGoogleCallback(token);
                    
                    // Redirect to dashboard on success
                    router.push('/dashboard');
                } else {
                    setError('No authentication token received');
                    setProcessing(false);
                    
                    // Redirect to login after showing error
                    setTimeout(() => {
                        router.push('/login');
                    }, 3000);
                }
            } catch (err) {
                console.error('Auth callback error:', err);
                setError(err.message || 'Authentication failed');
                setProcessing(false);
                
                // Redirect to login after showing error
                setTimeout(() => {
                    router.push('/login');
                }, 3000);
            }
        };

        handleCallback();
    }, [searchParams, handleGoogleCallback, router]);

    if (processing) {
        return (
            <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center">
                <div className="bg-white rounded-xl shadow-lg p-8 max-w-md w-full mx-4">
                    <div className="text-center">
                        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600 mx-auto mb-4"></div>
                        <h2 className="text-xl font-semibold text-gray-900 mb-2">
                            Completing Authentication
                        </h2>
                        <p className="text-gray-600">
                            Please wait while we complete your Google authentication...
                        </p>
                    </div>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center">
                <div className="bg-white rounded-xl shadow-lg p-8 max-w-md w-full mx-4">
                    <div className="text-center">
                        <div className="mx-auto flex items-center justify-center h-12 w-12 rounded-full bg-red-100 mb-4">
                            <svg className="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L3.732 16.5c-.77.833.192 2.5 1.732 2.5z" />
                            </svg>
                        </div>
                        <h2 className="text-xl font-semibold text-gray-900 mb-2">
                            Authentication Failed
                        </h2>
                        <p className="text-gray-600 mb-4">
                            {error}
                        </p>
                        <p className="text-sm text-gray-500">
                            Redirecting to login page in a few seconds...
                        </p>
                    </div>
                </div>
            </div>
        );
    }

    return null;
}
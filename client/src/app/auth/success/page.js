'use client';
import { Suspense } from 'react';
import AuthSuccessPage from '@/app/components/AuthSuccessPage';

export default function AuthSuccessPageWrapper() {
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <AuthSuccessPage />
    </Suspense>
  );
}

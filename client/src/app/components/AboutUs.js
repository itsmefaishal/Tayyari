'use client';
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLinkedin } from '@fortawesome/free-brands-svg-icons';

export default function AboutPage() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const founders = [
    {
      name: "Faishal Rahman",
      role: "Co-Founder",
      education: "BIT Sindri, 2024",
    //   experience: "15+ years in EdTech",
      image: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400&h=400&fit=crop&crop=face",
      linkedin: "https://www.linkedin.com/in/itsmefaishal/",
    //   bio: "Former Principal at a leading coaching institute, Dr. Kumar has helped thousands of students achieve their dreams. His vision for accessible quality education drives Tayyari's mission.",
      specialization: "Technology, Finance & Problem solving"
    },
    {
      name: "Sibte Ali",
      role: "Co-Founder",
      education: "AIIMS Delhi, PhD in Medical Education",
    //   experience: "18+ years in Medical Education",
      image: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400&h=400&fit=crop&crop=face",
      linkedin: "https://www.linkedin.com/in/itsmefaishal/",
    //   bio: "Renowned medical educator and former NEET examiner, Dr. Patel ensures our content meets the highest academic standards and reflects real exam patterns.",
      specialization: "Technology, Finance & Problem solving"
    }
  ];

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
                <a href="/" className="text-2xl font-bold text-indigo-600">Tayyari</a>
              </div>
            </div>
            
            {/* Desktop Menu */}
            <div className="hidden md:flex items-center space-x-4">
              <a href="/exams" className="text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-sm font-medium">
                Exams
              </a>
              <a href="/aboutus" className="text-indigo-600 px-3 py-2 rounded-md text-sm font-medium border-b-2 border-indigo-600">
                About
              </a>
              <a href="/contactus" className="text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-sm font-medium">
                Contact Us
              </a>
              <a href="/login" className="text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-sm font-medium">
                Login
              </a>
              <a href="/signup" className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md text-sm font-medium">
                Sign Up
              </a>
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
              <a href="/exams" className="block text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-base font-medium">
                Exams
              </a>
              <a href="/about" className="block text-indigo-600 px-3 py-2 rounded-md text-base font-medium">
                About
              </a>
              <a href="/contact" className="block text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-base font-medium">
                Contact Us
              </a>
              <a href="/login" className="block text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md text-base font-medium">
                Login
              </a>
              <a href="/signup" className="block text-indigo-600 hover:text-indigo-800 px-3 py-2 rounded-md text-base font-medium">
                Sign Up
              </a>
            </div>
          </div>
        )}
      </nav>

      {/* Hero Section */}
      <div className="max-w-7xl mx-auto py-16 px-4 sm:py-24 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h1 className="text-4xl font-extrabold text-gray-900 sm:text-5xl md:text-6xl">
            <span className="block">About</span>
            <span className="block text-indigo-600">Tayyari</span>
          </h1>
          <p className="mt-6 max-w-3xl mx-auto text-xl text-gray-600">
            Empowering students across India with comprehensive test preparation solutions and cutting-edge technology.
          </p>
        </div>

        {/* Company Story */}
        <div className="bg-white rounded-2xl shadow-xl p-8 mb-16">
          <h2 className="text-3xl font-bold text-gray-900 mb-6 text-center">Our Story</h2>
          <div className="prose max-w-4xl mx-auto text-gray-600 leading-relaxed">
            <p className="text-lg mb-6">
              Tayyari was born from a simple yet powerful belief: every student deserves access to quality test preparation, 
              regardless of their geographical location or economic background. Founded in 2020 by a team of passionate 
              educators and technologists, we set out to bridge the gap between aspiration and achievement.
            </p>
            <p className="text-lg mb-6">
              Our founders, having witnessed the struggles of countless students in traditional coaching systems, 
              envisioned a platform that combines the expertise of India's finest educators with the accessibility 
              of modern technology. Today, Tayyari stands as a testament to that vision, helping thousands of 
              students prepare for competitive examinations with confidence.
            </p>
            <p className="text-lg">
              We believe that success in competitive exams is not just about cramming facts, but about understanding 
              concepts, developing problem-solving skills, and building the confidence to face challenges. Our 
              platform is designed to nurture these qualities in every student who joins our community.
            </p>
          </div>
        </div>

        {/* Mission & Vision */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-16">
          <div className="bg-white rounded-xl shadow-lg p-8">
            <div className="flex items-center mb-6">
              <div className="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center mr-4">
                <svg className="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
                </svg>
              </div>
              <h3 className="text-2xl font-bold text-gray-900">Our Mission</h3>
            </div>
            <p className="text-gray-600 leading-relaxed">
              To democratize access to high-quality test preparation by leveraging technology and expertise, 
              ensuring every student has the opportunity to achieve their academic goals and realize their potential.
            </p>
          </div>
          
          <div className="bg-white rounded-xl shadow-lg p-8">
            <div className="flex items-center mb-6">
              <div className="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center mr-4">
                <svg className="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                </svg>
              </div>
              <h3 className="text-2xl font-bold text-gray-900">Our Vision</h3>
            </div>
            <p className="text-gray-600 leading-relaxed">
              To become India's most trusted and comprehensive online test preparation platform, 
              known for innovation, quality, and student success across all competitive examinations.
            </p>
          </div>
        </div>

        {/* Founders Section */}
        <div className="mb-16">
            <h2 className="text-3xl font-bold text-gray-900 mb-12 text-center">Meet Our Founders</h2>
            
            <div className="max-w-7xl mx-auto px-4">
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-12">
                    {founders.map((founder, index) => (
                        <div key={index} className="bg-white rounded-xl shadow-lg overflow-hidden hover:shadow-xl transition-shadow duration-300">
                            <div className="h-64 overflow-hidden">
                                <img 
                                src={founder.image} 
                                alt={founder.name}
                                className="w-full h-full object-cover"
                                />
                            </div>
                            <div className="p-6">
                                <h4 className="text-xl font-bold text-gray-900 mb-1">{founder.name}</h4>
                                <p className="text-indigo-600 font-medium mb-2">{founder.role}</p>
                                <p className="text-sm text-gray-600 mb-2">{founder.education}</p>
                                <p className="text-sm text-gray-600 mb-3 font-medium">{founder.experience}</p>
                                <p className="text-gray-700 text-sm leading-relaxed mb-3">{founder.bio}</p>
                                <div className="border-t pt-3">
                                <p className="text-xs text-indigo-600 font-medium">Specialization: {founder.specialization}</p>
                                <a href={founder.linkedin} target="_blank" rel="noopener noreferrer" className="text-indigo-600 hover:text-indigo-800">
                                    <FontAwesomeIcon icon={faLinkedin} className="h-10 w-10"/>
                                </a>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>

        {/* Journey Timeline */}
        <div className="bg-white rounded-2xl shadow-xl p-8 mb-16">
          <h2 className="text-3xl font-bold text-gray-900 mb-8 text-center">Our Journey</h2>
          <div className="relative">
            {milestones.map((milestone, index) => (
              <div key={index} className="flex items-center mb-8 last:mb-0">
                <div className="flex-shrink-0">
                  <div className="w-12 h-12 bg-indigo-600 rounded-full flex items-center justify-center text-white font-bold">
                    {milestone.year}
                  </div>
                </div>
                <div className="ml-6">
                  <p className="text-gray-800 font-medium">{milestone.event}</p>
                </div>
                {index < milestones.length - 1 && (
                  <div className="absolute left-6 mt-12 h-8 w-0.5 bg-gray-300"></div>
                )}
              </div>
            ))}
          </div>
        </div>

        {/* Values Section */}
        <div className="mb-16">
          <h2 className="text-3xl font-bold text-gray-900 mb-8 text-center">Our Values</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            <div className="bg-white rounded-lg shadow-md p-6 text-center">
              <div className="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <svg className="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <h3 className="text-lg font-semibold text-gray-900 mb-2">Excellence</h3>
              <p className="text-gray-600 text-sm">We strive for the highest quality in everything we do.</p>
            </div>

            <div className="bg-white rounded-lg shadow-md p-6 text-center">
              <div className="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <svg className="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                </svg>
              </div>
              <h3 className="text-lg font-semibold text-gray-900 mb-2">Accessibility</h3>
              <p className="text-gray-600 text-sm">Quality education should be accessible to everyone.</p>
            </div>

            <div className="bg-white rounded-lg shadow-md p-6 text-center">
              <div className="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <svg className="w-8 h-8 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
                </svg>
              </div>
              <h3 className="text-lg font-semibold text-gray-900 mb-2">Innovation</h3>
              <p className="text-gray-600 text-sm">We continuously innovate to enhance learning experiences.</p>
            </div>

            <div className="bg-white rounded-lg shadow-md p-6 text-center">
              <div className="w-16 h-16 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <svg className="w-8 h-8 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                </svg>
              </div>
              <h3 className="text-lg font-semibold text-gray-900 mb-2">Integrity</h3>
              <p className="text-gray-600 text-sm">We operate with complete transparency and honesty.</p>
            </div>
          </div>
        </div>

        {/* CTA Section */}
        <div className="bg-indigo-600 rounded-2xl shadow-xl p-8 text-center text-white">
          <h2 className="text-3xl font-bold mb-4">Ready to Start Your Journey?</h2>
          <p className="text-indigo-100 mb-6 max-w-2xl mx-auto">
            Join thousands of successful students who have achieved their dreams with Tayyari. 
            Begin your preparation today and take the first step towards your goals.
          </p>
          <a 
            href="/signup"
            className="bg-white text-indigo-600 font-bold py-3 px-8 rounded-lg text-lg hover:bg-gray-100 transition duration-300 inline-block"
          >
            Get Started Free
          </a>
        </div>
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
                <li><a href="/aboutus" className="text-gray-300 hover:text-white">About Us</a></li>
                <li><a href="/contactus" className="text-gray-300 hover:text-white">Contact</a></li>
                <li><a href="/" className="text-gray-300 hover:text-white">Help Center</a></li>
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
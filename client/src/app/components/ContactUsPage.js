"use client";

import { useState } from "react";
import emailjs from "emailjs-com";


export default function ContactUs() {
  const [formData, setFormData] = useState({ name: "", email: "", message: "" });
  const [status, setStatus] = useState("");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setStatus("loading");

    emailjs
      .send(
        process.env.NEXT_PUBLIC_EMAILJS_SERVICEID,
        process.env.NEXT_PUBLIC_EMAILJS_TEMPLATEID,
        formData,
        process.env.NEXT_PUBLIC_EMAILJS_PUBLICKEY
      )
      .then(
        () => {
          setStatus("success");
          setFormData({ name: "", email: "", message: "" });
        },
        (error) => {
          console.error(error.text);
          setStatus("error");
        }
      );
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-50 flex flex-col items-center px-6 py-16">
      
      <h1 className="text-5xl font-extrabold text-gray-900 mb-8 text-center">Contact Tayyari</h1>
      <p className="text-center max-w-2xl text-gray-700 mb-12">
        Have questions or suggestions? Fill out the form below or reach out to us directly.
      </p>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 w-full max-w-7xl">
        {/* Contact Form */}
        <form
          onSubmit={handleSubmit}
          className="bg-white shadow-lg rounded-3xl p-10 w-full"
        >
          <h2 className="text-2xl font-bold text-gray-800 mb-6">Send a Message</h2>

          <input
            type="text"
            name="name"
            placeholder="Your Name"
            value={formData.name}
            onChange={handleChange}
            required
            className="w-full text-gray-800 placeholder-gray-400 mb-4 p-4 border border-gray-300 rounded-xl focus:ring-2 focus:ring-indigo-500 focus:outline-none"
          />
          <input
            type="email"
            name="email"
            placeholder="Your Email"
            value={formData.email}
            onChange={handleChange}
            required
            className="w-full mb-4 text-gray-800 placeholder-gray-400 p-4 border border-gray-300 rounded-xl focus:ring-2 focus:ring-indigo-500 focus:outline-none"
          />
          <textarea
            name="message"
            placeholder="Your Message..."
            rows="6"
            value={formData.message}
            onChange={handleChange}
            required
            className="w-full mb-4 p-4 text-gray-800 placeholder-gray-400 border border-gray-300 rounded-xl focus:ring-2 focus:ring-indigo-500 focus:outline-none resize-none"
          />

          <button
            type="submit"
            disabled={status === "loading"}
            className={`w-full py-4 rounded-xl font-semibold transition duration-200 ${
              status === "loading"
                ? "bg-gray-400 cursor-not-allowed"
                : "bg-indigo-600 text-white hover:bg-indigo-700"
            }`}
          >
            {status === "loading" ? "Sending..." : "Send Message"}
          </button>

          {status === "success" && (
            <p className="text-green-600 mt-4 text-center font-medium">
              ✅ Thank you! Your message has been sent successfully.
            </p>
          )}
          {status === "error" && (
            <p className="text-red-600 mt-4 text-center font-medium">
              ❌ Oops! Something went wrong. Please try again later.
            </p>
          )}
        </form>

        {/* Contact Info */}
        <div className="bg-indigo-100 rounded-3xl p-10 flex flex-col justify-center">
          <h2 className="text-2xl font-bold text-gray-900 mb-6">Get in Touch</h2>

          <div className="mb-6">
            <h3 className="font-semibold text-gray-800 mb-1">📧 Email</h3>
            <p className="text-gray-700">support@tayyari.com</p>
          </div>

          <div className="mb-6">
            <h3 className="font-semibold text-gray-800 mb-1">📞 Phone</h3>
            <p className="text-gray-700">+91 98765 43210</p>
          </div>

          <div className="mb-6">
            <h3 className="font-semibold text-gray-800 mb-1">🏢 Office Address</h3>
            <p className="text-gray-700">
              Tayyari HQ, 123 Skill Street, Bengaluru, Karnataka, India
            </p>
          </div>

          <div>
            <h3 className="font-semibold text-gray-800 mb-1">⏰ Office Hours</h3>
            <p className="text-gray-700">Mon – Fri: 9:00 AM – 6:00 PM IST</p>
          </div>
        </div>
      </div>
    </div>
  );
}

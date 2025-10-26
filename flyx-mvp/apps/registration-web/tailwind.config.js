/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          purple: '#7C3AED',
        },
        secondary: {
          cyan: '#06B6D4',
        },
        accent: {
          pink: '#EC4899',
        },
        background: {
          light: '#F8FAFC',
        },
        text: {
          dark: '#1E293B',
        }
      }
    },
  },
  plugins: [],
}

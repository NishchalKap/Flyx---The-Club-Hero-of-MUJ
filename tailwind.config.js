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
          50: '#e6f2ff',
          100: '#b3d9ff',
          200: '#80bfff',
          300: '#4da6ff',
          400: '#1a8cff',
          500: '#007AFF',
          600: '#0062cc',
          700: '#004999',
          800: '#003066',
          900: '#001733',
        },
        secondary: {
          50: '#f4f0ff',
          100: '#e6d9ff',
          200: '#d1b3ff',
          300: '#bc8cff',
          400: '#a766ff',
          500: '#8B5FBF',
          600: '#6f4c99',
          700: '#533973',
          800: '#37264d',
          900: '#1b1326',
        },
        accent: {
          50: '#e6fff7',
          100: '#b3ffeb',
          200: '#80ffdf',
          300: '#4dffd3',
          400: '#1affc7',
          500: '#00FF88',
          600: '#00cc6d',
          700: '#009952',
          800: '#006637',
          900: '#00331b',
        },
        neutral: {
          50: '#f8f8f8',
          100: '#f0f0f0',
          200: '#e8e8e8',
          300: '#d8d8d8',
          400: '#c8c8c8',
          500: '#a8a8a8',
          600: '#888888',
          700: '#686868',
          800: '#484848',
          900: '#1C1C1E',
        },
        light: '#F2F2F7',
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'],
        display: ['Inter', 'system-ui', 'sans-serif'],
      },
      fontSize: {
        'xs': ['0.75rem', { lineHeight: '1rem' }],
        'sm': ['0.875rem', { lineHeight: '1.25rem' }],
        'base': ['1rem', { lineHeight: '1.5rem' }],
        'lg': ['1.125rem', { lineHeight: '1.75rem' }],
        'xl': ['1.25rem', { lineHeight: '1.75rem' }],
        '2xl': ['1.5rem', { lineHeight: '2rem' }],
        '3xl': ['1.875rem', { lineHeight: '2.25rem' }],
        '4xl': ['2.25rem', { lineHeight: '2.5rem' }],
        '5xl': ['3rem', { lineHeight: '1' }],
        '6xl': ['3.75rem', { lineHeight: '1' }],
      },
      spacing: {
        '18': '4.5rem',
        '88': '22rem',
        '128': '32rem',
      },
      borderRadius: {
        'xl': '0.75rem',
        '2xl': '1rem',
        '3xl': '1.5rem',
      },
      boxShadow: {
        'glass': '0 8px 32px 0 rgba(31, 38, 135, 0.37)',
        'card': '0 4px 20px 0 rgba(0, 0, 0, 0.1)',
        'elevated': '0 8px 40px 0 rgba(0, 0, 0, 0.12)',
      },
      backdropBlur: {
        'xs': '2px',
      },
      animation: {
        'fade-in': 'fadeIn 0.5s ease-in-out',
        'slide-up': 'slideUp 0.3s ease-out',
        'bounce-gentle': 'bounceGentle 2s infinite',
      },
      keyframes: {
        fadeIn: {
          '0%': { opacity: '0' },
          '100%': { opacity: '1' },
        },
        slideUp: {
          '0%': { transform: 'translateY(20px)', opacity: '0' },
          '100%': { transform: 'translateY(0)', opacity: '1' },
        },
        bounceGentle: {
          '0%, 100%': { transform: 'translateY(0)' },
          '50%': { transform: 'translateY(-10px)' },
        },
      },
    },
  },
  plugins: [
    require('@tailwindcss/forms'),
    require('@tailwindcss/typography'),
  ],
}


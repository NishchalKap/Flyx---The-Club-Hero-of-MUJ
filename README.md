# Flyx - Campus Engagement Platform

A comprehensive Progressive Web App (PWA) that transforms university campus engagement by combining event discovery, social interaction, and community building into one seamless platform.

## Features

- **Event Management**: Create, discover, and register for campus events
- **Club Management**: Join clubs, manage memberships, and showcase achievements
- **Social Features**: Instagram-style stories and live event feeds
- **Smart Discovery**: AI-powered personalized recommendations
- **Payment Integration**: Secure payment processing with multiple options
- **PWA Support**: Offline functionality and native app-like experience

## Tech Stack

- **Frontend**: React 18 + TypeScript + Vite
- **Styling**: Tailwind CSS with custom design system
- **Backend**: Supabase (PostgreSQL, Auth, Real-time, Storage)
- **PWA**: Vite PWA Plugin with Workbox
- **Animations**: Framer Motion
- **Icons**: Lucide React

## Getting Started

### Prerequisites

- Node.js 18+ 
- npm or yarn
- Supabase account

### Installation

1. Clone the repository
2. Install dependencies:
   ```bash
   npm install
   ```

3. Create a `.env` file with the following variables:
   ```env
   VITE_SUPABASE_URL=https://your-project.supabase.co
   VITE_SUPABASE_ANON_KEY=your-anon-key
   ```

4. Start the development server:
   ```bash
   npm run dev
   ```

5. Open [http://localhost:3000](http://localhost:3000) in your browser

### Building for Production

```bash
npm run build
```

The built files will be in the `dist` directory, ready for deployment.

## Project Structure

```
src/
├── components/          # Reusable UI components
├── pages/              # Page components
├── contexts/           # React contexts (Auth, Theme, etc.)
├── services/           # API services and external integrations
├── hooks/              # Custom React hooks
├── utils/              # Utility functions
├── types/              # TypeScript type definitions
└── main.tsx           # Application entry point
```

## Design System

The app uses a custom design system with:
- **Primary Color**: Electric Blue (#007AFF)
- **Secondary Color**: Vibrant Purple (#8B5FBF)  
- **Accent Color**: Neon Green (#00FF88)
- **Typography**: Inter font family
- **Effects**: Glassmorphism and smooth animations

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

MIT License - see LICENSE file for details




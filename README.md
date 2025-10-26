<!-- Banner -->
<p align="center">
  <img src="./assets/banner.png" alt="Flyx - Campus Engagement Platform" width="100%"/>
</p>

<h1 align="center">Flyx — The Club Hero of MUJ</h1>
<p align="center">A PWA for seamless campus engagement: events, clubs, stories, and smart discovery.</p>

<p align="center">
  <a href="#getting-started"><img src="https://img.shields.io/badge/Setup-Quickstart-4c9aff?style=for-the-badge" alt="Quickstart"/></a>
  <img src="https://img.shields.io/badge/TypeScript-5.4-3178C6?style=for-the-badge&logo=typescript&logoColor=white" alt="TypeScript"/>
  <img src="https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=061b2c" alt="React 18"/>
  <img src="https://img.shields.io/badge/Vite-PWA-646CFF?style=for-the-badge&logo=vite&logoColor=white" alt="Vite PWA"/>
  <img src="https://img.shields.io/badge/TailwindCSS-Design%20System-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white" alt="Tailwind"/>
  <img src="https://img.shields.io/badge/Supabase-Backend-3ECF8E?style=for-the-badge&logo=supabase&logoColor=223" alt="Supabase"/>
</p>

<hr/>

- Demo: Coming soon — add your GIF/MP4 at ./assets/demo.gif and link below.
- Architecture: Add ./assets/architecture.png and link below.

## Table of Contents
- Features
- Tech Stack
- Directory Structure
- Quickstart
- Configuration
- Demo & Screenshots
- Architecture
- Contributing
- License

## Features
- Event Management: Create, discover, and register for campus events
- Club Management: Join clubs, manage memberships, and showcase achievements
- Social: Instagram-style stories and live event feeds
- Smart Discovery: AI-powered personalized recommendations
- Payments: Secure payment processing with multiple options
- PWA Support: Offline-first, installable, native-like experience

## Tech Stack
- Frontend: React 18 + TypeScript + Vite
- Styling: Tailwind CSS (custom design system)
- Backend: Supabase (PostgreSQL, Auth, Realtime, Storage)
- PWA: Vite PWA Plugin with Workbox
- Animations: Framer Motion
- Icons: Lucide React

## Directory Structure
```bash
.
├─ App/
├─ flyx-mvp/
├─ src/
├─ assets/
│  ├─ banner.png        # Project banner (add your graphic)
│  ├─ demo.gif          # App demo (screen recording)
│  ├─ architecture.png  # System architecture diagram
│  └─ badges/           # Optional custom shields
├─ .gitignore
└─ README.md
```

## Quickstart
### Prerequisites
- Node.js 18+
- npm or yarn
- Supabase account

### Installation
1) Clone the repository
2) Install dependencies
```bash
npm install
```
3) Create a .env file in the project root
```env
VITE_SUPABASE_URL=https://your-project.supabase.co
VITE_SUPABASE_ANON_KEY=your-anon-key
```
4) Start the development server
```bash
npm run dev
```

## Configuration
- Update Supabase keys in .env
- Configure Tailwind in tailwind.config.js if needed
- PWA: Update name/description/icons in vite.config.ts and manifest

## Demo & Screenshots
<p align="center">
  <img src="./assets/demo.gif" alt="Flyx demo" width="75%"/>
</p>

If you don’t have a demo yet, record one with:
- macOS: Shift+Cmd+5 → capture window, export as GIF/MP4
- Windows: Win+G (Xbox Game Bar)
- Linux: Peek or OBS

## Architecture
<p align="center">
  <img src="./assets/architecture.png" alt="Architecture diagram" width="85%"/>
</p>

Suggested diagram sections:
- Client (React+Vite) → PWA Service Worker → API
- Supabase: Auth, Database (Postgres), Storage, Realtime
- External Providers: Payments, Notifications

## Contributing
We welcome contributions! Follow these steps:
1) Fork the repo
2) Create a feature branch: git checkout -b feat/your-feature
3) Commit: git commit -m "feat: add your feature"
4) Push: git push origin feat/your-feature
5) Open a Pull Request

Please follow conventional commits and include screenshots for UI changes.

## License
Copyright (c) 2025 Nishchal. All rights reserved.

---

Design assets folder added at ./assets. Replace placeholder links with your actual banner, demo, and architecture images for a polished README.

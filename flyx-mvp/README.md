# Flyx MVP

**Flyx: Where Campus Takes Flight** is a university event management platform designed to connect students with clubs and campus happenings. This monorepo contains the complete Minimum Viable Product (MVP), including the backend API, the web admin portal for event creation, and the mobile-optimized registration page.

## Tech Stack

- **Monorepo:** Turborepo with npm Workspaces
- **Backend:** Node.js, Express.js, TypeScript
- **Database:** PostgreSQL, Redis
- **Frontend (Admin):** React, TypeScript, Vite, Tailwind CSS
- **Frontend (Registration):** React, TypeScript, Vite, Tailwind CSS
- **File Storage:** Cloudflare R2 (S3-compatible)

---

## Getting Started

### Prerequisites

- Node.js (v18 or later)
- npm (v9 or later)
- Docker and Docker Compose

### 1. Clone the Repository

```bash
git clone <repository_url>
cd flyx-mvp
```

### 2. Set Up Environment Variables

Copy the example environment file and fill in the required values.

```bash
cp .env.example .env
```

You will need to provide credentials for:
- Cloudflare R2
- Razorpay
- A secure `JWT_SECRET`

For local development, the default `DATABASE_URL` and `REDIS_URL` should work with the provided Docker setup.

### 3. Install Dependencies

Install all dependencies for all workspaces from the root of the monorepo.

```bash
npm install
```

### 4. Start the Database and Caching Services

Run the PostgreSQL and Redis services using Docker Compose.

```bash
npm run docker:up
```

This will start the services in detached mode. The PostgreSQL container will automatically initialize the schema from `packages/database/schema.sql`.

### 5. Run the Development Servers

Start all applications (API, web-admin, registration-web) concurrently using Turborepo.

```bash
npm run dev
```

The services will be available at:
- **Backend API:** `http://localhost:3000`
- **Web Admin Portal:** `http://localhost:3001`
- **Registration Page:** `http://localhost:3002`

---

## Available Scripts

- `npm run dev`: Starts all applications in development mode.
- `npm run build`: Builds all applications for production.
- `npm run docker:up`: Starts the Docker containers for PostgreSQL and Redis.
- `npm run docker:down`: Stops and removes the Docker containers.
- `npm run db:migrate`: (Example) Applies a database migration.

---

## API Documentation

### Endpoints

- `POST /api/events`: Create a new event (requires admin auth).
- `GET /api/events/:id`: Get event details (public).
- `POST /api/events/:id/register`: Register for an event (requires student auth).
- `PATCH /api/events/:id`: Update an event (requires admin auth).

See the source code in `packages/api` for full details on request bodies and responses.

---

## Project Structure

- `apps/web-admin`: The React-based portal for club admins to create events.
- `apps/registration-web`: The mobile-optimized React page for students to register.
- `apps/mobile`: Placeholder for React Native integration docs.
- `packages/api`: The Express.js backend server.
- `packages/database`: SQL schema and migrations.
- `packages/shared`: Shared TypeScript types used across the monorepo.

---

This README provides all the necessary steps to get the Flyx MVP running locally.

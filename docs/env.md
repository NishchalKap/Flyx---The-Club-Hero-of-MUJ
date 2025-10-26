# Environment Variables Documentation

This document describes all environment variables used in the Flyx project.

## Core Variables

### Supabase Configuration
```env
VITE_SUPABASE_URL=https://your-project.supabase.co
# The URL of your Supabase project

VITE_SUPABASE_ANON_KEY=your-anon-key
# The anonymous API key for Supabase client-side access

VITE_UNIVERSITY_DOMAIN=muj.manipal.edu
# (Optional) Restrict Google sign-in to specific university domain
```

### Payment Integration
```env
RAZORPAY_KEY_ID=your-key-id
# Razorpay API key ID for payment processing

RAZORPAY_KEY_SECRET=your-key-secret
# Razorpay API key secret for payment verification
```

### Storage
```env
R2_ACCOUNT_ID=your-account-id
# Cloudflare R2 account ID for file storage

R2_ACCESS_KEY=your-access-key
# Cloudflare R2 access key

R2_SECRET_KEY=your-secret-key
# Cloudflare R2 secret key

R2_BUCKET_NAME=your-bucket-name
# Cloudflare R2 bucket name for storing files
```

### API Configuration
```env
API_PORT=3001
# Port number for the API server

NODE_ENV=development
# Environment mode (development/production)

JWT_SECRET=your-jwt-secret
# Secret key for JWT token generation
```

## Setting Up Environment Variables

1. Copy the example file:
   ```bash
   cp .env.example .env
   ```

2. Fill in your values for each variable

3. Never commit the .env file to version control

## Security Notes

- Keep your .env file secure and never share sensitive values
- Rotate secrets periodically
- Use different values for development and production
- Monitor access logs for suspicious activity
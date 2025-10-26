# External Integrations

This document describes how to set up and configure external service integrations used in Flyx.

## Supabase

### Setup
1. Create a new project at [Supabase](https://app.supabase.com)
2. Get your project URL and anon key from Settings > API
3. Add them to your .env file:
   ```
   VITE_SUPABASE_URL=your-url
   VITE_SUPABASE_ANON_KEY=your-key
   ```

### Database Schema
See [database schema](../packages/database/schema.sql) for table structures.

## Razorpay

### Setup
1. Create account at [Razorpay](https://razorpay.com)
2. Get API keys from Dashboard > Settings > API Keys
3. Add to environment:
   ```
   RAZORPAY_KEY_ID=your-key-id
   RAZORPAY_KEY_SECRET=your-secret
   ```

### Testing
Use test card numbers from [Razorpay docs](https://razorpay.com/docs/payments/payments/test-card-details/)

## Cloudflare R2

### Setup
1. Create R2 bucket in [Cloudflare dashboard](https://dash.cloudflare.com)
2. Create API token with R2 permissions
3. Configure environment:
   ```
   R2_ACCOUNT_ID=your-account-id
   R2_ACCESS_KEY=your-access-key
   R2_SECRET_KEY=your-secret-key
   R2_BUCKET_NAME=your-bucket
   ```

### Usage
See [uploadToR2.ts](../packages/api/src/utils/uploadToR2.ts) for implementation.

## Google OAuth

### Setup
1. Create project in [Google Cloud Console](https://console.cloud.google.com)
2. Enable OAuth2 API
3. Configure OAuth consent screen
4. Create OAuth client credentials
5. Add to Supabase Auth settings

### Domain Restriction
Set VITE_UNIVERSITY_DOMAIN to restrict sign-in domain

## Progressive Web App (PWA)

### Configuration
See [vite.config.ts](./vite.config.ts) for PWA settings.

### Assets Required
- favicon.ico (16x16, 32x32)
- apple-touch-icon.png (180x180)
- pwa-192x192.png
- pwa-512x512.png
- maskable-icon.png (adaptive icon)

## Testing Each Integration

1. **Supabase**:
   ```typescript
   await supabase.from('users').select('*')
   ```

2. **Razorpay**:
   ```typescript
   const order = await razorpay.orders.create({...})
   ```

3. **R2 Storage**:
   ```typescript
   await uploadToR2(file, 'images/')
   ```

4. **OAuth**:
   ```typescript
   await supabase.auth.signInWithOAuth({provider: 'google'})
   ```

## Troubleshooting

### Common Issues

1. **Supabase Connection**:
   - Check URL and key are correct
   - Verify IP is allowed
   - Check RLS policies

2. **Razorpay**:
   - Verify webhook signature
   - Test mode vs Live mode
   - Currency configuration

3. **R2 Upload**:
   - Check CORS configuration
   - Verify bucket permissions
   - File size limits

4. **OAuth**:
   - Authorized domains
   - Redirect URI configuration
   - Consent screen setup

### Getting Help

- Supabase: [Discord](https://discord.supabase.com)
- Razorpay: [Support](https://razorpay.com/support/)
- Cloudflare: [Community](https://community.cloudflare.com)
- PWA: [Vite PWA Plugin](https://vite-pwa-org.netlify.app/)
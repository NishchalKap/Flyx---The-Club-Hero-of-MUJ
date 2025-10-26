-- Enable pgcrypto for UUID generation if needed, though we use SERIAL here
-- CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Events Table: Stores all event information
CREATE TABLE events (
  id SERIAL PRIMARY KEY,
  club_id INTEGER NOT NULL, -- In a real app, this would be a foreign key to a 'clubs' table
  title VARCHAR(100) NOT NULL,
  description TEXT NOT NULL,
  banner_url VARCHAR(500) NOT NULL,
  venue VARCHAR(200) NOT NULL,
  registration_opens TIMESTAMPTZ NOT NULL,
  registration_closes TIMESTAMPTZ NOT NULL,
  event_starts TIMESTAMPTZ NOT NULL,
  event_ends TIMESTAMPTZ NOT NULL,
  is_paid BOOLEAN DEFAULT FALSE,
  price DECIMAL(10,2),
  capacity INTEGER,
  registered_count INTEGER DEFAULT 0,
  status VARCHAR(20) DEFAULT 'draft' CHECK (status IN ('draft', 'published', 'archived')),
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Event Registrations Table: Tracks student registrations for events
CREATE TABLE event_registrations (
  id SERIAL PRIMARY KEY,
  event_id INTEGER NOT NULL REFERENCES events(id) ON DELETE CASCADE,
  user_id INTEGER NOT NULL, -- In a real app, this would be a foreign key to a 'users' table
  ticket_id VARCHAR(50) UNIQUE NOT NULL,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(200) NOT NULL,
  phone VARCHAR(15) NOT NULL,
  branch VARCHAR(50) NOT NULL,
  year VARCHAR(10) NOT NULL,
  payment_status VARCHAR(20) DEFAULT 'pending' CHECK (payment_status IN ('pending', 'completed', 'failed')),
  payment_id VARCHAR(100),
  qr_code_url VARCHAR(500),
  registered_at TIMESTAMPTZ DEFAULT NOW()
);

-- Indexes for performance
CREATE INDEX idx_event_registrations_event_id ON event_registrations(event_id);
CREATE INDEX idx_event_registrations_user_id ON event_registrations(user_id);
CREATE INDEX idx_event_registrations_ticket_id ON event_registrations(ticket_id);

-- Trigger to update the 'updated_at' timestamp on event changes
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = NOW();
   RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_events_updated_at BEFORE UPDATE ON events FOR EACH ROW EXECUTE PROCEDURE update_updated_at_column();

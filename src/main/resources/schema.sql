CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL,
  loyalty_points INT NOT NULL DEFAULT 0,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT chk_users_role CHECK (role IN ('ADMIN', 'MANAGER', 'CUSTOMER'))
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS hotels (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  location VARCHAR(255) NOT NULL,
  description TEXT,
  manager_id BIGINT,
  status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
  average_rating DECIMAL(3, 2) NOT NULL DEFAULT 0.00,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT chk_hotels_status CHECK (status IN ('PENDING', 'UNDER_REVIEW', 'ACTIVE', 'REJECTED')),
  CONSTRAINT fk_hotels_manager_id FOREIGN KEY (manager_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS rooms (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  hotel_id BIGINT NOT NULL,
  room_number VARCHAR(20) NOT NULL,
  type VARCHAR(50),
  price DECIMAL(10, 2) NOT NULL,
  capacity INT NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
  CONSTRAINT chk_rooms_status CHECK (status IN ('AVAILABLE', 'BOOKED', 'MAINTENANCE')),
  CONSTRAINT fk_rooms_hotel_id FOREIGN KEY (hotel_id) REFERENCES hotels(id),
  CONSTRAINT uq_rooms_hotel_room_number UNIQUE (hotel_id, room_number)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS room_types (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  hotel_id BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  base_price DECIMAL(10, 2) NOT NULL,
  capacity INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_room_types_hotel_id FOREIGN KEY (hotel_id) REFERENCES hotels(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS room_inventory (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  room_type_id BIGINT NOT NULL,
  date DATE NOT NULL,
  total_rooms INT NOT NULL,
  available_rooms INT NOT NULL,
  CONSTRAINT fk_room_inventory_room_type_id FOREIGN KEY (room_type_id) REFERENCES room_types(id),
  CONSTRAINT uq_room_inventory_room_type_date UNIQUE (room_type_id, date)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS price_rules (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  room_type_id BIGINT NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  multiplier DECIMAL(5, 2) NOT NULL,
  CONSTRAINT fk_price_rules_room_type_id FOREIGN KEY (room_type_id) REFERENCES room_types(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS facilities (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  category VARCHAR(20) NOT NULL,
  CONSTRAINT chk_facilities_category CHECK (category IN ('BASIC', 'ROOM', 'PREMIUM'))
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS hotel_facilities (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  hotel_id BIGINT NOT NULL,
  facility_id BIGINT NOT NULL,
  value VARCHAR(20) NOT NULL,
  CONSTRAINT chk_hotel_facilities_value CHECK (value IN ('FREE', 'PAID', 'AVAILABLE')),
  CONSTRAINT fk_hotel_facilities_hotel_id FOREIGN KEY (hotel_id) REFERENCES hotels(id),
  CONSTRAINT fk_hotel_facilities_facility_id FOREIGN KEY (facility_id) REFERENCES facilities(id),
  CONSTRAINT uq_hotel_facilities_hotel_facility UNIQUE (hotel_id, facility_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS hotel_documents (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  hotel_id BIGINT NOT NULL,
  document_type VARCHAR(50) NOT NULL,
  document_url TEXT NOT NULL,
  verification_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
  uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  verified_at TIMESTAMP NULL,
  verified_by BIGINT,
  CONSTRAINT chk_hotel_documents_type CHECK (document_type IN ('LICENSE', 'GST_CERTIFICATE', 'OWNERSHIP_PROOF', 'ID_PROOF')),
  CONSTRAINT chk_hotel_documents_status CHECK (verification_status IN ('PENDING', 'VERIFIED', 'REJECTED')),
  CONSTRAINT fk_hotel_documents_hotel_id FOREIGN KEY (hotel_id) REFERENCES hotels(id),
  CONSTRAINT fk_hotel_documents_verified_by FOREIGN KEY (verified_by) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS promotions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(100) NOT NULL UNIQUE,
  type VARCHAR(20) NOT NULL,
  value DECIMAL(10, 2) NOT NULL,
  min_booking_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
  max_discount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  usage_limit INT NOT NULL DEFAULT 0,
  used_count INT NOT NULL DEFAULT 0,
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT chk_promotions_type CHECK (type IN ('PERCENTAGE', 'FIXED'))
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_promotions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  promotion_id BIGINT NOT NULL,
  used_count INT NOT NULL DEFAULT 0,
  CONSTRAINT fk_user_promotions_user_id FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_user_promotions_promotion_id FOREIGN KEY (promotion_id) REFERENCES promotions(id),
  CONSTRAINT uq_user_promotions_user_promotion UNIQUE (user_id, promotion_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS bookings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  reservation_number VARCHAR(50) NOT NULL UNIQUE,
  user_id BIGINT NOT NULL,
  room_id BIGINT,
  room_type_id BIGINT,
  check_in DATE NOT NULL,
  check_out DATE NOT NULL,
  total_price DECIMAL(10, 2) NOT NULL,
  discount_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
  final_price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
  promotion_id BIGINT,
  status VARCHAR(20) NOT NULL,
  parent_booking_id BIGINT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT chk_bookings_status CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED')),
  CONSTRAINT fk_bookings_user_id FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_bookings_room_id FOREIGN KEY (room_id) REFERENCES rooms(id),
  CONSTRAINT fk_bookings_room_type_id FOREIGN KEY (room_type_id) REFERENCES room_types(id),
  CONSTRAINT fk_bookings_promotion_id FOREIGN KEY (promotion_id) REFERENCES promotions(id),
  CONSTRAINT fk_bookings_parent_id FOREIGN KEY (parent_booking_id) REFERENCES bookings(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS reviews (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  hotel_id BIGINT NOT NULL,
  rating INT NOT NULL,
  comment TEXT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT chk_reviews_rating CHECK (rating BETWEEN 1 AND 5),
  CONSTRAINT fk_reviews_user_id FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_reviews_hotel_id FOREIGN KEY (hotel_id) REFERENCES hotels(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS email_logs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  booking_id BIGINT NOT NULL,
  email VARCHAR(150) NOT NULL,
  status VARCHAR(20) NOT NULL,
  sent_at TIMESTAMP NULL,
  CONSTRAINT chk_email_logs_status CHECK (status IN ('SENT', 'FAILED')),
  CONSTRAINT fk_email_logs_booking_id FOREIGN KEY (booking_id) REFERENCES bookings(id)
) ENGINE=InnoDB;

-- =========================================
-- 1. CREATE DATABASE
-- =========================================
CREATE DATABASE mrrs_db;
USE mrrs_db;

-- =========================================
-- 2. USERS TABLE
-- =========================================
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nrc_id VARCHAR(50) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'PASSENGER') NOT NULL DEFAULT 'PASSENGER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================================
-- 3. STATIONS TABLE
-- =========================================
CREATE TABLE stations (
    station_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    station_name VARCHAR(100) NOT NULL,
    city VARCHAR(100),
    code VARCHAR(10) NOT NULL UNIQUE
);

-- =========================================
-- 4. ROUTES TABLE
-- =========================================
CREATE TABLE routes (
    route_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    origin_station_id BIGINT NOT NULL,
    destination_station_id BIGINT NOT NULL,
    distance_km INT,

    CONSTRAINT fk_routes_origin
        FOREIGN KEY (origin_station_id) REFERENCES stations(station_id),

    CONSTRAINT fk_routes_destination
        FOREIGN KEY (destination_station_id) REFERENCES stations(station_id)
);

-- =========================================
-- 5. TRAINS TABLE
-- =========================================
CREATE TABLE trains (
    train_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(100) NOT NULL,
    train_code VARCHAR(20) NOT NULL UNIQUE,
    total_seats INT NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'
);

-- =========================================
-- 6. SCHEDULES TABLE
-- =========================================
CREATE TABLE schedules (
    schedule_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    train_id BIGINT NOT NULL,
    route_id BIGINT NOT NULL,
    travel_date DATE NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    price DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_schedules_train
        FOREIGN KEY (train_id) REFERENCES trains(train_id),

    CONSTRAINT fk_schedules_route
        FOREIGN KEY (route_id) REFERENCES routes(route_id)
);

-- =========================================
-- 7. SEATS TABLE
-- =========================================
CREATE TABLE seats (
    seat_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    train_id BIGINT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    seat_class ENUM('VIP', 'NORMAL') DEFAULT 'NORMAL',
    is_available BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_seats_train
        FOREIGN KEY (train_id) REFERENCES trains(train_id)
);

-- =========================================
-- 8. BOOKINGS TABLE
-- =========================================
CREATE TABLE bookings (
    booking_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDING', 'CONFIRMED', 'CANCELLED') DEFAULT 'PENDING',
    total_amount DECIMAL(10,2),

    CONSTRAINT fk_bookings_user
        FOREIGN KEY (user_id) REFERENCES users(user_id),

    CONSTRAINT fk_bookings_schedule
        FOREIGN KEY (schedule_id) REFERENCES schedules(schedule_id)
);

-- =========================================
-- 9. BOOKING SEATS TABLE (MANY-TO-MANY)
-- =========================================
CREATE TABLE booking_seats (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT NOT NULL,
    seat_id BIGINT NOT NULL,

    CONSTRAINT fk_bs_booking
        FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_bs_seat
        FOREIGN KEY (seat_id) REFERENCES seats(seat_id)
);

-- =========================================
-- 10. PAYMENTS TABLE
-- =========================================
CREATE TABLE payments (
    payment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT NOT NULL,
    payment_method VARCHAR(50),
    payment_status ENUM('PENDING', 'SUCCESS', 'FAILED') DEFAULT 'PENDING',
    transaction_ref VARCHAR(100),
    paid_at TIMESTAMP NULL,

    CONSTRAINT fk_payments_booking
        FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
        ON DELETE CASCADE
);

-- =========================================
-- 11. ADMIN LOGS TABLE
-- =========================================
CREATE TABLE admin_logs (
    log_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    admin_id BIGINT NOT NULL,
    action VARCHAR(255) NOT NULL,
    target_table VARCHAR(100),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_admin_logs_user
        FOREIGN KEY (admin_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);

-- =========================================
-- 12. TICKETS TABLE
-- =========================================
CREATE TABLE tickets (
    ticket_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT NOT NULL UNIQUE,
    pnr_number VARCHAR(50) NOT NULL UNIQUE,
    qr_code TEXT,
    issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'USED', 'CANCELLED') DEFAULT 'ACTIVE',

    CONSTRAINT fk_tickets_booking
        FOREIGN KEY (booking_id)
        REFERENCES bookings(booking_id)
        ON DELETE CASCADE
);

-- =========================================
-- 13. REFUNDS TABLE
-- =========================================
CREATE TABLE refunds (
    refund_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payment_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    reason VARCHAR(255),
    refund_status ENUM('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED')
        DEFAULT 'PENDING',
    requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP NULL,

    CONSTRAINT fk_refunds_payment
        FOREIGN KEY (payment_id)
        REFERENCES payments(payment_id)
        ON DELETE CASCADE
);

-- =========================================
-- 14. SEAT HOLDS TABLE
-- =========================================
CREATE TABLE seat_holds (
    hold_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    seat_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_seat_holds_seat
        FOREIGN KEY (seat_id)
        REFERENCES seats(seat_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_seat_holds_schedule
        FOREIGN KEY (schedule_id)
        REFERENCES schedules(schedule_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_seat_holds_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

-- =========================================
-- 15. ROUTE STOPS TABLE
-- =========================================
CREATE TABLE route_stops (
    route_stop_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    route_id BIGINT NOT NULL,
    station_id BIGINT NOT NULL,
    stop_order INT NOT NULL,
    arrival_time TIME NULL,
    departure_time TIME NULL,

    CONSTRAINT fk_route_stops_route
        FOREIGN KEY (route_id)
        REFERENCES routes(route_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_route_stops_station
        FOREIGN KEY (station_id)
        REFERENCES stations(station_id)
        ON DELETE CASCADE
);

-- =========================================
-- 16. NOTIFICATIONS TABLE
-- =========================================
CREATE TABLE notifications (
    notification_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    message TEXT NOT NULL,
    type ENUM('BOOKING', 'PAYMENT', 'REFUND', 'SYSTEM')
        DEFAULT 'SYSTEM',
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_notifications_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

-- =========================================
-- OPTIONAL INDEXES (RECOMMENDED)
-- =========================================

CREATE INDEX idx_tickets_booking
ON tickets(booking_id);

CREATE INDEX idx_refunds_payment
ON refunds(payment_id);

CREATE INDEX idx_seat_holds_schedule
ON seat_holds(schedule_id);

CREATE INDEX idx_seat_holds_user
ON seat_holds(user_id);

CREATE INDEX idx_route_stops_route
ON route_stops(route_id);

CREATE INDEX idx_notifications_user
ON notifications(user_id);
package com.example.mrrs.repository;

import com.example.mrrs.entity.Booking;
import com.example.mrrs.entity.BookingStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository
        extends JpaRepository<Booking, Long> {


    List<Booking> findByUserUserId(
            Long userId
    );

    List<Booking> findByScheduleScheduleId(
            Long scheduleId
    );


    List<Booking> findByStatus(
            BookingStatus status
    );


    List<Booking> findByUserUserIdAndStatus(
            Long userId,
            BookingStatus status
    );


    boolean existsByUserUserIdAndScheduleScheduleId(
            Long userId,
            Long scheduleId
    );


    List<Booking> findByBookingDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    long countByStatus(
            BookingStatus status
    );
}
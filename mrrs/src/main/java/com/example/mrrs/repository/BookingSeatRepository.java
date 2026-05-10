package com.example.mrrs.repository;

import com.example.mrrs.entity.BookingSeat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSeatRepository
        extends JpaRepository<BookingSeat, Long> {


    List<BookingSeat> findByBookingBookingId(
            Long bookingId
    );


    void deleteByBookingBookingId(
            Long bookingId
    );
}
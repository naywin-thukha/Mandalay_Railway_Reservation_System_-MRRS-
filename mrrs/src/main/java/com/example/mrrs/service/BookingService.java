package com.example.mrrs.service;

import com.example.mrrs.dto.booking.BookingRequest;
import com.example.mrrs.dto.booking.BookingResponse;

import java.util.List;

public interface BookingService {


    BookingResponse createBooking(
            BookingRequest request
    );

    BookingResponse getBookingById(
            Long bookingId
    );

    List<BookingResponse> getAllBookings();


    List<BookingResponse> getBookingsByUser(
            Long userId
    );

    List<BookingResponse> getBookingsBySchedule(
            Long scheduleId
    );

    void cancelBooking(
            Long bookingId
    );
}
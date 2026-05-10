package com.example.mrrs.controller;

import com.example.mrrs.dto.booking.BookingRequest;
import com.example.mrrs.dto.booking.BookingResponse;
import com.example.mrrs.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponse createBooking(
            @RequestBody BookingRequest request
    ) {

        return bookingService.createBooking(request);
    }

    @GetMapping("/{id}")
    public BookingResponse getBookingById(
            @PathVariable Long id
    ) {

        return bookingService.getBookingById(id);
    }

    @GetMapping
    public List<BookingResponse> getAllBookings() {

        return bookingService.getAllBookings();
    }

    @DeleteMapping("/{id}")
    public String cancelBooking(
            @PathVariable Long id
    ) {

        bookingService.cancelBooking(id);

        return "Booking cancelled successfully";
    }
}
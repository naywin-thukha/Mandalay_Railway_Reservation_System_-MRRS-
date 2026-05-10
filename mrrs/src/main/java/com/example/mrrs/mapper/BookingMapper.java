package com.example.mrrs.mapper;

import com.example.mrrs.dto.booking.BookingResponse;
import com.example.mrrs.entity.Booking;
import com.example.mrrs.entity.BookingSeat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingMapper {

    public BookingResponse toResponse(
            Booking booking,
            List<BookingSeat> bookingSeats
    ) {

        List<String> seats = (bookingSeats == null)
                ? List.of()
                : bookingSeats.stream()
                .map(bs -> bs.getSeat().getSeatNumber())
                .toList();

        var schedule = booking.getSchedule();
        var route = schedule.getRoute();

        String routeText =
                route.getOrigin().getStationName()
                        + " → "
                        + route.getDestination().getStationName();

        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .passengerName(booking.getUser().getFullName())
                .trainName(schedule.getTrain().getTrainName())
                .route(routeText)
                .bookingDate(booking.getBookingDate())
                .seats(seats)
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus().name())
                .build();
    }
}
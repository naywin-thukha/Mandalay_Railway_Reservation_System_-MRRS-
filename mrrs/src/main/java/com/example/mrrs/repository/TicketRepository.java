package com.example.mrrs.repository;

import com.example.mrrs.entity.Ticket;
import com.example.mrrs.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByPnrNumber(String pnrNumber);

    boolean existsByBooking_BookingId(Long bookingId);

    List<Ticket> findByStatus(TicketStatus status);
}
package com.example.mrrs.controller;

import com.example.mrrs.dto.ticket.TicketResponse;
import com.example.mrrs.dto.ticket.TicketValidationResponse;
import com.example.mrrs.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/generate/{bookingId}")
    public TicketResponse generateTicket(
            @PathVariable Long bookingId
    ) {

        return ticketService.generateTicket(bookingId);
    }

    @GetMapping("/{pnrNumber}")
    public TicketResponse getTicketByPnr(
            @PathVariable String pnrNumber
    ) {

        return ticketService.getTicketByPnr(pnrNumber);
    }

    @GetMapping
    public List<TicketResponse> getAllTickets() {

        return ticketService.getAllTickets();
    }

    @GetMapping("/validate/{pnrNumber}")
    public TicketValidationResponse validateTicket(
            @PathVariable String pnrNumber
    ) {

        return ticketService.validateTicket(pnrNumber);
    }

    @PutMapping("/use/{pnrNumber}")
    public String markTicketAsUsed(
            @PathVariable String pnrNumber
    ) {

        ticketService.markTicketAsUsed(pnrNumber);

        return "Ticket marked as USED";
    }

    @PutMapping("/cancel/{pnrNumber}")
    public String cancelTicket(
            @PathVariable String pnrNumber
    ) {

        ticketService.cancelTicket(pnrNumber);

        return "Ticket cancelled";
    }
}
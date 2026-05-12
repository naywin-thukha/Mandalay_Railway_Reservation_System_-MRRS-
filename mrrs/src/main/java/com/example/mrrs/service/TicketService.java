package com.example.mrrs.service;

import com.example.mrrs.dto.ticket.TicketResponse;
import com.example.mrrs.dto.ticket.TicketValidationResponse;

import java.util.List;

public interface TicketService {

    TicketResponse generateTicket(Long bookingId);

    TicketResponse getTicketByPnr(String pnrNumber);

    List<TicketResponse> getAllTickets();

    TicketValidationResponse validateTicket(String pnrNumber);

    void markTicketAsUsed(String pnrNumber);

    void cancelTicket(String pnrNumber);
}
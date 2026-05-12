package com.example.mrrs.dto.ticket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketValidationResponse {

    private String pnrNumber;

    private boolean valid;

    private String message;
}
package com.fotistsiskakis.betstrategist.models.requests;

import com.fotistsiskakis.betstrategist.models.Sport;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class UpdateMatchRequest {
    private String description;
    private LocalDate matchDate;
    private LocalTime matchTime;
    private String teamA;
    private String teamB;
    private Sport sport;
}

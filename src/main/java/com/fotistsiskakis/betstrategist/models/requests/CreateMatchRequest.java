package com.fotistsiskakis.betstrategist.models.requests;

import com.fotistsiskakis.betstrategist.models.Sport;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateMatchRequest {

    @NotNull(message = "description cannot be null")
    private String description;

    @NotNull(message = "matchDate cannot be null")
    private LocalDate matchDate;

    @NotNull(message = "matchTime cannot be null")
    private LocalTime matchTime;

    @NotNull(message = "teamA cannot be null")
    private String teamA;

    @NotNull(message = "teamB cannot be null")
    private String teamB;

    @NotNull(message = "sport cannot be null")
    private Sport sport;
}
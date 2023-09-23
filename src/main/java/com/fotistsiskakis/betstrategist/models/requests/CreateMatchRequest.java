package com.fotistsiskakis.betstrategist.models.requests;

import com.fotistsiskakis.betstrategist.models.Sport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchRequest {

    @NotNull(message = "description cannot be null")
    private String description;

    @NotNull(message = "matchDate cannot be null")
    private LocalDate matchDate;

    @NotNull(message = "matchTime cannot be null")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @Schema(type = "string", format = "time", example = "18:30:00")
    private LocalTime matchTime;

    @NotNull(message = "teamA cannot be null")
    private String teamA;

    @NotNull(message = "teamB cannot be null")
    private String teamB;

    @NotNull(message = "sport cannot be null")
    @Schema(allowableValues = { "FOOTBALL", "BASKETBALL" })
    private Sport sport;
}
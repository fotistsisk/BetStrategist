package com.fotistsiskakis.betstrategist.models.requests;

import com.fotistsiskakis.betstrategist.models.Sport;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class UpdateMatchRequest {
    private String description;
    private LocalDate matchDate;
    @DateTimeFormat(pattern = "HH:mm:ss")
    @Schema(type = "string", format = "time", example = "18:30:00")
    private LocalTime matchTime;
    private String teamA;
    private String teamB;
    @Schema(allowableValues = { "FOOTBALL", "BASKETBALL" })
    private Sport sport;
}

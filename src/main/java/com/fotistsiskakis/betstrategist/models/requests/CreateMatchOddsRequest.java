package com.fotistsiskakis.betstrategist.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchOddsRequest {
    @NotNull(message = "matchId cannot be null")
    private String matchId;
    @NotNull(message = "specifier cannot be null")
    private String specifier;
    @NotNull(message = "odd cannot be null")
    private BigDecimal odd;
}

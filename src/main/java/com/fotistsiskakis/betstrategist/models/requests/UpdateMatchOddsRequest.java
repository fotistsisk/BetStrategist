package com.fotistsiskakis.betstrategist.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMatchOddsRequest {
    @NotNull
    private String matchId;

    @NotNull
    private String specifier;

    @NotNull
    private BigDecimal odd;
}


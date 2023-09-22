package com.fotistsiskakis.betstrategist.models.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class UpdateMatchOddsRequest {
    @NotNull
    private String matchId;

    @NotNull
    private String specifier;

    @NotNull
    private BigDecimal odd;
}


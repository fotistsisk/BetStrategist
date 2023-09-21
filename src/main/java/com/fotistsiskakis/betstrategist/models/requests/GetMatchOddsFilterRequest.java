package com.fotistsiskakis.betstrategist.models.requests;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class GetMatchOddsFilterRequest {
    private String id;
    private String matchId;
    private String specifier;
    private BigDecimal odd;
}
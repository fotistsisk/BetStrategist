package com.fotistsiskakis.betstrategist.models.requests;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GetMatchOddsFilterRequest {
    private String id;
    private String matchId;
    private String specifier;
    private BigDecimal odd;
}
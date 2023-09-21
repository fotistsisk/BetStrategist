package com.fotistsiskakis.betstrategist.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateMatchOddsRequest {
    @NotNull(message = "matchId cannot be null")
    private UUID matchId;
    @NotNull(message = "specifier cannot be null")
    private String specifier;
    @NotNull(message = "odd cannot be null")
    private Long odd;
}

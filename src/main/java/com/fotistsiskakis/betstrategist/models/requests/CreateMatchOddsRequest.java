package com.fotistsiskakis.betstrategist.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMatchOddsRequest {

    @NotNull(message = "specifier cannot be null")
    private String specifier;
    @NotNull(message = "odd cannot be null")
    private Long odd;
}

package com.fotistsiskakis.betstrategist.models.responses;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateMatchResponse {
    private UUID id;
}

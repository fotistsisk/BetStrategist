package com.fotistsiskakis.betstrategist.models.requests;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MatchFilterRequest {
    private UUID id;
    private String description;
    private String matchDate;
    private String matchTime;
    private String teamA;
    private String teamB;
    private String sport;
    private String teamName;
}
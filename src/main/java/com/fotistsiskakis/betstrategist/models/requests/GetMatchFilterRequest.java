package com.fotistsiskakis.betstrategist.models.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMatchFilterRequest {
    private String id;
    private String description;
    private String matchDate;
    private String matchTime;
    private String teamA;
    private String teamB;
    private String sport;
    private String teamName;
}
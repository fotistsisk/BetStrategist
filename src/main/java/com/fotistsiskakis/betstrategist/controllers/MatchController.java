package com.fotistsiskakis.betstrategist.controllers;

import com.fotistsiskakis.betstrategist.models.Match;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchRequest;
import com.fotistsiskakis.betstrategist.models.requests.MatchFilterRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchResponse;
import com.fotistsiskakis.betstrategist.services.MatchFilterService;
import com.fotistsiskakis.betstrategist.services.MatchService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;
    private final MatchFilterService matchFilterService;

    @PostMapping
    public ResponseEntity<CreateMatchResponse> createMatch(@Valid @RequestBody CreateMatchRequest createMatchRequest) {
        CreateMatchResponse response = matchService.createMatch(createMatchRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Match>> getFilteredMatches(MatchFilterRequest matchFilterRequest) {
        List<Match> matches = matchFilterService.getFilteredMatches(matchFilterRequest);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }
}
package com.fotistsiskakis.betstrategist.controllers;

import com.fotistsiskakis.betstrategist.models.Match;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchRequest;
import com.fotistsiskakis.betstrategist.models.requests.GetMatchFilterRequest;
import com.fotistsiskakis.betstrategist.models.requests.UpdateMatchRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchResponse;
import com.fotistsiskakis.betstrategist.services.MatchFilterService;
import com.fotistsiskakis.betstrategist.services.MatchService;
import com.fotistsiskakis.betstrategist.services.Utils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;
    private final MatchFilterService matchFilterService;

    @Operation(summary = "Creates match", description = "Creates a match based on the request and generates a random UUID")
    @PostMapping
    public ResponseEntity<CreateMatchResponse> createMatch(@Valid @RequestBody CreateMatchRequest createMatchRequest) {
        CreateMatchResponse response = matchService.createMatch(createMatchRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Filters and returns matches", description = "Get all the matches and their odds, based on one or any combination of their attributes")
    @GetMapping
    public ResponseEntity<List<Match>> getFilteredMatches(GetMatchFilterRequest getMatchFilterRequest) {
        List<Match> matches = matchFilterService.getFilteredMatches(getMatchFilterRequest);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @Operation(summary = "Updates a match", description = "Updates a match given it's id, based on the request and returns the new object")
    @PutMapping("/{id}")
    public ResponseEntity<Match> updateMatch(@PathVariable String id, @RequestBody UpdateMatchRequest updateMatchRequest) {
        Match updatedMatch = matchService.updateMatch(Utils.getUuidFromString(id), updateMatchRequest);
        return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
    }

    @Operation(summary = "Deletes a match", description = "Deletes a match given it's id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable String id) {
        matchService.deleteMatch(Utils.getUuidFromString(id));
        return ResponseEntity.noContent().build();
    }
}
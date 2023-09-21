package com.fotistsiskakis.betstrategist.controllers;

import com.fotistsiskakis.betstrategist.models.Match;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchRequest;
import com.fotistsiskakis.betstrategist.models.requests.GetMatchFilterRequest;
import com.fotistsiskakis.betstrategist.models.requests.UpdateMatchRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchResponse;
import com.fotistsiskakis.betstrategist.services.MatchFilterService;
import com.fotistsiskakis.betstrategist.services.MatchService;
import com.fotistsiskakis.betstrategist.services.Utils;
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

    @PostMapping
    public ResponseEntity<CreateMatchResponse> createMatch(@Valid @RequestBody CreateMatchRequest createMatchRequest) {
        CreateMatchResponse response = matchService.createMatch(createMatchRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Match>> getFilteredMatches(GetMatchFilterRequest getMatchFilterRequest) {
        List<Match> matches = matchFilterService.getFilteredMatches(getMatchFilterRequest);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Match> updateMatch(@PathVariable String id, @RequestBody UpdateMatchRequest updateMatchRequest) {
        Match updatedMatch = matchService.updateMatch(Utils.getUuidFromString(id), updateMatchRequest);
        return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable String id) {
        matchService.deleteMatch(Utils.getUuidFromString(id));
        return ResponseEntity.noContent().build();
    }
}
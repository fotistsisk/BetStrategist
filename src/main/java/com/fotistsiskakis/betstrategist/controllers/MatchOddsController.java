package com.fotistsiskakis.betstrategist.controllers;

import com.fotistsiskakis.betstrategist.models.MatchOdds;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest;
import com.fotistsiskakis.betstrategist.models.requests.GetMatchOddsFilterRequest;
import com.fotistsiskakis.betstrategist.models.requests.UpdateMatchOddsRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchOddsResponse;
import com.fotistsiskakis.betstrategist.services.MatchOddsFilterService;
import com.fotistsiskakis.betstrategist.services.MatchOddsService;
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
@RequestMapping("/matches/odds")
public class MatchOddsController {

    private final MatchOddsService matchOddsService;
    private final MatchOddsFilterService matchOddsFilterService;

    @Operation(summary = "Creates matchOdds", description = "Creates a matchOdds object based on the request and generates a random UUID")
    @PostMapping
    public ResponseEntity<CreateMatchOddsResponse> createMatchOdds(@Valid @RequestBody CreateMatchOddsRequest createMatchOddsRequest) {
        CreateMatchOddsResponse response = matchOddsService.createMatchOdds(createMatchOddsRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Filters and returns matchOdds", description = "Get all the matchOdds based on one or any combination of their attributes")
    @GetMapping
    public ResponseEntity<List<MatchOdds>> getFilteredMatchOdds(GetMatchOddsFilterRequest getMatchOddsFilterRequest) {
        List<MatchOdds> matches = matchOddsFilterService.getFilteredMatchOdds(getMatchOddsFilterRequest);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @Operation(summary = "Updates a matchOdd", description = "Updates a matchOdd given it's id, based on the request and returns the new object")
    @PutMapping("/{id}")
    public ResponseEntity<MatchOdds> updateMatchOdds(@PathVariable String id, @RequestBody UpdateMatchOddsRequest updateMatchOddsRequest) {
        MatchOdds updatedMatch = matchOddsService.updateMatchOdds(Utils.getUuidFromString(id), updateMatchOddsRequest);
        return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
    }

    @Operation(summary = "Deletes a matchOdd", description = "Deletes a matchOdd given it's id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchOdds(@PathVariable String id) {
        matchOddsService.deleteMatchOdds(Utils.getUuidFromString(id));
        return ResponseEntity.noContent().build();
    }
}

package com.fotistsiskakis.betstrategist.controllers;

import com.fotistsiskakis.betstrategist.models.Match;
import com.fotistsiskakis.betstrategist.models.MatchOdds;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest;
import com.fotistsiskakis.betstrategist.models.requests.GetMatchOddsFilterRequest;
import com.fotistsiskakis.betstrategist.models.requests.UpdateMatchOddsRequest;
import com.fotistsiskakis.betstrategist.models.requests.UpdateMatchRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchOddsResponse;
import com.fotistsiskakis.betstrategist.services.MatchOddsFilterService;
import com.fotistsiskakis.betstrategist.services.MatchOddsService;
import com.fotistsiskakis.betstrategist.services.Utils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/matches/odds")
public class MatchOddsController {

    private final MatchOddsService matchOddsService;
    private final MatchOddsFilterService matchOddsFilterService;

    @PostMapping
    public ResponseEntity<CreateMatchOddsResponse> createMatchOdds(@Valid @RequestBody CreateMatchOddsRequest createMatchOddsRequest) {
        CreateMatchOddsResponse response = matchOddsService.createMatchOdds(createMatchOddsRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MatchOdds>> getFilteredMatchOdds(GetMatchOddsFilterRequest getMatchOddsFilterRequest) {
        List<MatchOdds> matches = matchOddsFilterService.getFilteredMatchOdds(getMatchOddsFilterRequest);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchOdds> updateMatchOdds(@PathVariable String id, @RequestBody UpdateMatchOddsRequest updateMatchOddsRequest) {
        MatchOdds updatedMatch = matchOddsService.updateMatchOdds(Utils.getUuidFromString(id), updateMatchOddsRequest);
        return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchOdds(@PathVariable String id) {
        matchOddsService.deleteMatchOdds(Utils.getUuidFromString(id));
        return ResponseEntity.noContent().build();
    }
}

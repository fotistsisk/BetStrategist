package com.fotistsiskakis.betstrategist.controllers;

import com.fotistsiskakis.betstrategist.models.Match;
import com.fotistsiskakis.betstrategist.models.MatchOdds;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchOddsResponse;
import com.fotistsiskakis.betstrategist.services.MatchOddsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/matches")
public class MatchOddsController {

    private final MatchOddsService matchOddsService;

    @PostMapping("/odds")
    public ResponseEntity<CreateMatchOddsResponse> createMatchOdds(@Valid @RequestBody CreateMatchOddsRequest createMatchOddsRequest) {
        CreateMatchOddsResponse response = matchOddsService.createMatchOdds(createMatchOddsRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/odds/{id}")
    public ResponseEntity<MatchOdds> getMatchOddsById(@PathVariable UUID id) {
        MatchOdds matchOdds = matchOddsService.getMatchOddsById(id);
        return new ResponseEntity<>(matchOdds, HttpStatus.FOUND);
    }
}

package com.fotistsiskakis.betstrategist.controllers;

import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchOddsResponse;
import com.fotistsiskakis.betstrategist.services.MatchOddsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/matches/odds")
public class MatchOddsController {

    private final MatchOddsService matchOddsService;

    @PostMapping
    public ResponseEntity<CreateMatchOddsResponse> createMatchOdds(@Valid @RequestBody CreateMatchOddsRequest createMatchOddsRequest) {
        CreateMatchOddsResponse response = matchOddsService.createMatchOdds(createMatchOddsRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

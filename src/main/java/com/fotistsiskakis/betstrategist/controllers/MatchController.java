package com.fotistsiskakis.betstrategist.controllers;

import com.fotistsiskakis.betstrategist.models.requests.CreateMatchRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchResponse;
import com.fotistsiskakis.betstrategist.services.MatchService;
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
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<CreateMatchResponse> createMatch(@Valid @RequestBody CreateMatchRequest createMatchRequest) {
        CreateMatchResponse response = matchService.createMatch(createMatchRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
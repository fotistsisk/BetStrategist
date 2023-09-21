package com.fotistsiskakis.betstrategist.services;

import com.fotistsiskakis.betstrategist.models.Match;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchResponse;
import com.fotistsiskakis.betstrategist.repositories.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final ModelMapper modelMapper;

    public CreateMatchResponse createMatch(CreateMatchRequest request) {
        Match match = modelMapper.map(request, Match.class);
        matchRepository.save(match);
        return CreateMatchResponse.builder()
                .id(match.getId())
                .build();
    }

    public Match getMatch(UUID id) {
        Optional<Match> matchOptional = matchRepository.findById(id);

        if (matchOptional.isPresent()) {
            return matchOptional.get();
        } else {
            throw new EntityNotFoundException();
        }
    }
}

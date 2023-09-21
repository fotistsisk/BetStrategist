package com.fotistsiskakis.betstrategist.services;

import com.fotistsiskakis.betstrategist.models.Match;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchRequest;
import com.fotistsiskakis.betstrategist.models.requests.UpdateMatchRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchResponse;
import com.fotistsiskakis.betstrategist.repositories.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final ModelMapper modelMapper;

    public CreateMatchResponse createMatch(CreateMatchRequest request) {
        Match match = modelMapper.map(request, Match.class);
        match = matchRepository.save(match);
        return CreateMatchResponse.builder()
                .id(match.getId())
                .build();
    }

    public Match updateMatch(UUID id, UpdateMatchRequest updateMatchRequest) {
        Match existingMatch = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Match not found with id: " + id));

        try {
            BeanUtils.copyProperties(updateMatchRequest, existingMatch);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the match.");
        }

        return matchRepository.save(existingMatch);
    }
}

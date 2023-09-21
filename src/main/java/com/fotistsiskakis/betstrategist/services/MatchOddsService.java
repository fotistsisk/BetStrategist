package com.fotistsiskakis.betstrategist.services;

import com.fotistsiskakis.betstrategist.models.Match;
import com.fotistsiskakis.betstrategist.models.MatchOdds;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchOddsResponse;
import com.fotistsiskakis.betstrategist.repositories.MatchOddsRepository;
import com.fotistsiskakis.betstrategist.repositories.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchOddsService {
    private final MatchOddsRepository matchOddsRepository;
    private final MatchRepository matchRepository;

    public CreateMatchOddsResponse createMatchOdds(CreateMatchOddsRequest request){
        Match match = matchRepository.findById(request.getMatchId())
                .orElseThrow(() -> new EntityNotFoundException("Match not found with id: " + request.getMatchId()));
        MatchOdds matchOdds = MatchOdds.builder()
                .match(match)
                .odd(request.getOdd())
                .specifier(request.getSpecifier())
                .build();
        log.debug(String.format("Saving %s matchOdds object in db", matchOdds));
        matchOdds = matchOddsRepository.save(matchOdds);
        return  CreateMatchOddsResponse.builder()
                .id(matchOdds.getId())
                .build();
    }

    public MatchOdds getMatchOddsById(UUID id) {
        Optional<MatchOdds> matchOddsOptional = matchOddsRepository.findById(id);

        if (matchOddsOptional.isPresent()) {
            return matchOddsOptional.get();
        } else {
            throw new EntityNotFoundException();
        }
    }
}

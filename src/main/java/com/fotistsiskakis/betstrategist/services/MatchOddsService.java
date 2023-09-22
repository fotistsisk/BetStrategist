package com.fotistsiskakis.betstrategist.services;

import com.fotistsiskakis.betstrategist.models.Match;
import com.fotistsiskakis.betstrategist.models.MatchOdds;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest;
import com.fotistsiskakis.betstrategist.models.requests.UpdateMatchOddsRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchOddsResponse;
import com.fotistsiskakis.betstrategist.repositories.MatchOddsRepository;
import com.fotistsiskakis.betstrategist.repositories.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public MatchOdds updateMatchOdds(UUID id, UpdateMatchOddsRequest updateRequest) {
        MatchOdds existingOdds = matchOddsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Match Odds not found with id: " + id));

        String newMatchId = updateRequest.getMatchId();
        if(newMatchId!=null){
            Match existingMatch = matchRepository.findById(Utils.getUuidFromString(newMatchId))
                    .orElseThrow(() -> new EntityNotFoundException("Match not found with id: " + newMatchId));
            existingOdds.setMatch(existingMatch);
        }

        if (updateRequest.getSpecifier() != null) {
            existingOdds.setSpecifier(updateRequest.getSpecifier());
        }
        if (updateRequest.getOdd() != null) {
            existingOdds.setOdd(updateRequest.getOdd());
        }

        return matchOddsRepository.save(existingOdds);
    }

}

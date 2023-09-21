package com.fotistsiskakis.betstrategist.services;

import com.fotistsiskakis.betstrategist.models.MatchOdds;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchOddsResponse;
import com.fotistsiskakis.betstrategist.repositories.MatchOddsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchOddsService {
    private final MatchOddsRepository matchOddsRepository;
    private final ModelMapper modelMapper;

    public CreateMatchOddsResponse createMatchOdds(CreateMatchOddsRequest request){
        MatchOdds matchOdds = modelMapper.map(request, MatchOdds.class);
        log.debug(String.format("Saving %s matchOdds object in db", matchOdds));
        try{
            matchOddsRepository.save(matchOdds);
            return  CreateMatchOddsResponse.builder()
                    .id(matchOdds.getId())
                    .build();
         } catch (DataIntegrityViolationException e) {
            log.error(String.format("Error while saving %s matchOdds object in db", matchOdds));
            throw new DataIntegrityViolationException("A match is required for this operation. Please provide a valid match ID.");
        }

    }
}

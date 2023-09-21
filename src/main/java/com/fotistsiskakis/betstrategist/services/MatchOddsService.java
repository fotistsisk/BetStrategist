package com.fotistsiskakis.betstrategist.services;

import com.fotistsiskakis.betstrategist.models.MatchOdds;
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest;
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchOddsResponse;
import com.fotistsiskakis.betstrategist.repositories.MatchOddsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchOddsService {
    private final MatchOddsRepository matchOddsRepository;
    private final ModelMapper modelMapper;

    public CreateMatchOddsResponse createMatchOdds(CreateMatchOddsRequest request){
        try{
            MatchOdds matchOdds = modelMapper.map(request, MatchOdds.class);
            matchOddsRepository.save(matchOdds);
            return  CreateMatchOddsResponse.builder()
                    .id(matchOdds.getId())
                    .build();
         } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("A match is required for this operation. Please provide a valid match ID.");
        }

    }
}

package com.fotistsiskakis.betstrategist

import com.fotistsiskakis.betstrategist.models.Match
import com.fotistsiskakis.betstrategist.models.MatchOdds
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchOddsResponse
import com.fotistsiskakis.betstrategist.repositories.MatchOddsRepository
import com.fotistsiskakis.betstrategist.services.MatchOddsService
import org.modelmapper.ModelMapper
import org.springframework.dao.DataIntegrityViolationException

import spock.lang.Specification

class MatchOddsServiceSpec extends Specification{

    def matchOddsRepository = Mock(MatchOddsRepository.class)
    def modelMapper = Mock(ModelMapper.class)
    def matchOddsService

    def setup(){
        matchOddsService = new MatchOddsService(matchOddsRepository, modelMapper)
    }

    def "Test createMatchOdds with valid request"() {
        given:
        def uuid = UUID.randomUUID()
        def request = CreateMatchOddsRequest.builder()
                .odd((long) 1.2)
                .specifier("X")
                .matchId(uuid)
                .build()

        def matchOdds = MatchOdds.builder()
                .specifier("X")
                .match(Match.builder().id(uuid).build())
                .odd((long) 1.2)
                .id(UUID.randomUUID())
                .build()

        def expectedResponse = CreateMatchOddsResponse.builder().id(matchOdds.getId()).build()

        1 * modelMapper.map(request, MatchOdds.class) >> matchOdds
        1 * matchOddsRepository.save(matchOdds) >> matchOdds

        when:
        def response = matchOddsService.createMatchOdds(request)

        then:
        response == expectedResponse
    }

    def "Test createMatchOdds with invalid request - matchId doesn't exist in match table"() {
        given:
        def uuid = UUID.randomUUID()
        def request = CreateMatchOddsRequest.builder()
                .odd((long) 1.2)
                .specifier("X")
                .matchId(uuid)
                .build()

        def matchOdds = MatchOdds.builder()
                .specifier("X")
                .match(Match.builder().id(uuid).build())
                .odd((long) 1.2)
                .id(UUID.randomUUID())
                .build()


        1 * modelMapper.map(request, MatchOdds.class) >> matchOdds
        1 * matchOddsRepository.save(matchOdds) >> { throw new DataIntegrityViolationException("error") }

        when:
        matchOddsService.createMatchOdds(request)

        then:
        DataIntegrityViolationException ex = thrown(DataIntegrityViolationException.class)
        ex.message == "A match is required for this operation. Please provide a valid match ID."
    }
}

package com.fotistsiskakis.betstrategist.services

import com.fotistsiskakis.betstrategist.models.Match
import com.fotistsiskakis.betstrategist.models.MatchOdds
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchOddsResponse
import com.fotistsiskakis.betstrategist.repositories.MatchOddsRepository
import com.fotistsiskakis.betstrategist.repositories.MatchRepository
import com.fotistsiskakis.betstrategist.services.MatchOddsService
import org.springframework.dao.DataIntegrityViolationException

import spock.lang.Specification

class MatchOddsServiceSpec extends Specification{

    def matchOddsRepository = Mock(MatchOddsRepository.class)
    def matchRepository = Mock(MatchRepository.class)
    def matchOddsService

    def setup(){
        matchOddsService = new MatchOddsService(matchOddsRepository, matchRepository)
    }

    def "Test createMatchOdds with valid request"() {
        given:
        def uuid = UUID.randomUUID()
        def request = CreateMatchOddsRequest.builder()
                .odd((long) 1.2)
                .specifier("X")
                .matchId(uuid)
                .build()

        def match = Match.builder()
                .id(uuid)
                .build()

        def matchOddsWIthOutUuid = MatchOdds.builder()
                .specifier("X")
                .match(match)
                .odd((long) 1.2)
                .build()

        def matchOdds = MatchOdds.builder()
                .specifier("X")
                .match(match)
                .odd((long) 1.2)
                .id(UUID.randomUUID())
                .build()

        def expectedResponse = CreateMatchOddsResponse.builder().id(matchOdds.getId()).build()

        1 * matchRepository.findById(uuid) >> Optional.of(match)
        1 * matchOddsRepository.save(matchOddsWIthOutUuid) >> matchOdds

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

        def match = Match.builder()
                .id(uuid)
                .build()

        def matchOddsWIthOutUuid = MatchOdds.builder()
                .specifier("X")
                .match(match)
                .odd((long) 1.2)
                .build()

        def matchOdds = MatchOdds.builder()
                .specifier("X")
                .match(match)
                .odd((long) 1.2)
                .id(UUID.randomUUID())
                .build()


        1 * matchRepository.findById(uuid) >> Optional.of(match)
        1 * matchOddsRepository.save(matchOddsWIthOutUuid) >> { throw new DataIntegrityViolationException("error") }

        when:
        matchOddsService.createMatchOdds(request)

        then:
        thrown(DataIntegrityViolationException.class)
    }
}

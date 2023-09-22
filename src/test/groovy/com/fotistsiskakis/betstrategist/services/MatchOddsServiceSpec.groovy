package com.fotistsiskakis.betstrategist.services

import com.fotistsiskakis.betstrategist.models.Match
import com.fotistsiskakis.betstrategist.models.MatchOdds
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest
import com.fotistsiskakis.betstrategist.models.requests.UpdateMatchOddsRequest
import com.fotistsiskakis.betstrategist.models.responses.CreateMatchOddsResponse
import com.fotistsiskakis.betstrategist.repositories.MatchOddsRepository
import com.fotistsiskakis.betstrategist.repositories.MatchRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.dao.DataIntegrityViolationException

import spock.lang.Specification
import spock.lang.Unroll

class MatchOddsServiceSpec extends Specification {

    def matchOddsRepository = Mock(MatchOddsRepository.class)
    def matchRepository = Mock(MatchRepository.class)
    def matchOddsService

    def setup() {
        matchOddsService = new MatchOddsService(matchOddsRepository, matchRepository)
    }

    def "Test createMatchOdds with valid request"() {
        given:
        def uuid = UUID.randomUUID()
        def request = CreateMatchOddsRequest.builder()
                .odd((long) 1.2)
                .specifier("X")
                .matchId(uuid.toString())
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
                .matchId(uuid.toString())
                .build()

        def match = Match.builder()
                .id(uuid)
                .build()

        def matchOddsWIthOutUuid = MatchOdds.builder()
                .specifier("X")
                .match(match)
                .odd((long) 1.2)
                .build()

        1 * matchRepository.findById(uuid) >> Optional.of(match)
        1 * matchOddsRepository.save(matchOddsWIthOutUuid) >> { throw new DataIntegrityViolationException("error") }

        when:
        matchOddsService.createMatchOdds(request)

        then:
        thrown(DataIntegrityViolationException.class)
    }

    @Unroll
    def "Update Match Odds - Update Match Specifier and Odd"(UUID newMatchUuid, String newSpecifier, BigDecimal newOdd) {
        given:
        def oldSpecifier = "Old Specifier"
        def oldOdd = 2.0
        UUID existingOddsId = UUID.randomUUID()
        def match = Match.builder()
                .id(UUID.randomUUID())
                .build()
        Match newMatch
        if (newMatchUuid != null) {
            newMatch = Match.builder()
                    .id(newMatchUuid)
                    .build()
        } else {
            newMatch = match
        }
        def existingOdds = MatchOdds.builder()
                .id(existingOddsId)
                .match(match)
                .specifier(oldSpecifier)
                .odd(oldOdd)
                .build()

        def newOdds = MatchOdds.builder()
                .id(existingOddsId)
                .match(newMatch)
                .specifier(newSpecifier != null ? newSpecifier : oldSpecifier)
                .odd(newOdd != null ? newOdd : oldOdd)
                .build()

        1 * matchOddsRepository.findById(existingOddsId) >> Optional.of(existingOdds)
        if (newMatchUuid != null) {
            1 * matchRepository.findById(newMatchUuid) >> Optional.of(newMatch)
        }
        1 * matchOddsRepository.save(newOdds) >> newOdds

        when:
        def updatedOdds = matchOddsService.updateMatchOdds(existingOddsId, UpdateMatchOddsRequest.builder()
                .matchId(newMatchUuid != null ? newMatchUuid.toString() : null)
                .specifier(newSpecifier)
                .odd(newOdd)
                .build())

        then:
        updatedOdds.match == newMatch
        updatedOdds.specifier == (newSpecifier != null ? newSpecifier : oldSpecifier)
        updatedOdds.odd == (newOdd != null ? newOdd : oldOdd)

        where:
        newMatchUuid      | newSpecifier    | newOdd
        UUID.randomUUID() | "New Specifier" | 2.5
        null              | "New Specifier" | 8
        UUID.randomUUID() | null            | 9
        UUID.randomUUID() | "New Specifier" | null
        null              | null            | 2.5
        UUID.randomUUID() | null            | null
        null              | "New Specifier" | null
    }

    def "Update Match Odds - no match odds found"() {
        given:
        def matchOddsId = UUID.randomUUID()

        1 * matchOddsRepository.findById(matchOddsId) >> Optional.empty()

        when:
        matchOddsService.updateMatchOdds(matchOddsId, UpdateMatchOddsRequest.builder()
                .matchId(UUID.randomUUID().toString())
                .specifier("specifier")
                .odd(2.1)
                .build())
        then:
        def ex = thrown(EntityNotFoundException.class)
        ex.getMessage() == "Match Odds not found with id: ${matchOddsId}"
    }

    def "Update Match Odds - no match found"() {
        given:
        def matchOddsId = UUID.randomUUID()
        def newMatchId = UUID.randomUUID()

        def match = Match.builder()
                .id(UUID.randomUUID())
                .build()

        def existingOdds = MatchOdds.builder()
                .id(UUID.randomUUID())
                .match(match)
                .specifier("oldSpecifier")
                .odd(1.4)
                .build()

        1 * matchOddsRepository.findById(matchOddsId) >> Optional.of(existingOdds)
        1 * matchRepository.findById(newMatchId) >> Optional.empty()

        when:
        matchOddsService.updateMatchOdds(matchOddsId, UpdateMatchOddsRequest.builder()
                .matchId(newMatchId.toString())
                .specifier("specifier")
                .odd(2.1)
                .build())
        then:
        def ex = thrown(EntityNotFoundException.class)
        ex.getMessage() == "Match not found with id: ${newMatchId}"
    }

    def "Delete existing match odds by ID"() {
        given:
        UUID existingMatchOddsId = UUID.randomUUID()
        def match = Match.builder()
                .id(UUID.randomUUID())
                .build()
        MatchOdds existingMatchOdds = MatchOdds.builder()
                .id(existingMatchOddsId)
                .match(match)
                .specifier("specifier")
                .odd(BigDecimal.ONE)
                .build()

        1 * matchOddsRepository.findById(existingMatchOddsId) >> Optional.of(existingMatchOdds)

        when:
        matchOddsService.deleteMatchOdds(existingMatchOddsId)

        then:
        1 * matchOddsRepository.deleteById(existingMatchOddsId)
    }

    def "Attempt to delete non-existing match odds"() {
        given:
        UUID nonExistingMatchOddsId = UUID.randomUUID()

        1 * matchOddsRepository.findById(nonExistingMatchOddsId) >> Optional.empty()

        when:
        matchOddsService.deleteMatchOdds(nonExistingMatchOddsId)

        then:
        0 * matchOddsRepository.deleteById(nonExistingMatchOddsId)
        def ex = thrown(EntityNotFoundException.class)
        ex.message == "Match Odds not found with id: ${nonExistingMatchOddsId}"
    }
}

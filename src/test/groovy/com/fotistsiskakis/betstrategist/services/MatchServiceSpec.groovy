package com.fotistsiskakis.betstrategist.services

import com.fasterxml.jackson.databind.util.BeanUtil
import com.fotistsiskakis.betstrategist.models.Match
import com.fotistsiskakis.betstrategist.models.Sport
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchRequest
import com.fotistsiskakis.betstrategist.models.requests.UpdateMatchRequest
import com.fotistsiskakis.betstrategist.repositories.MatchRepository
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.modelmapper.ModelMapper
import org.springframework.beans.BeanUtils
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

@ExtendWith(MockitoExtension)
class MatchServiceSpec extends Specification {

    MatchRepository matchRepository = Mock()
    ModelMapper modelMapper = Mock()
    BeanUtils beanUtils = Mock()

    MatchService matchService

    void setup() {
        matchService = new MatchService(matchRepository, modelMapper)
    }

    def "Test createMatch"() {
        given:
        CreateMatchRequest request = CreateMatchRequest.builder()
            .description("desc")
            .matchDate(LocalDate.now())
            .matchTime(LocalTime.now())
            .sport(Sport.BASKETBALL)
            .teamA("teamA")
            .teamB("teamB")
            .build()
        UUID uuid = UUID.randomUUID()
        Match match = Match.builder()
                .description("desc")
                .matchDate(LocalDate.now())
                .matchTime(LocalTime.now())
                .sport(Sport.BASKETBALL)
                .teamA("teamA")
                .teamB("teamB")
                .build()
        Match matchWithUuid = Match.builder()
                .id(uuid)
                .description("desc")
                .matchDate(LocalDate.now())
                .matchTime(LocalTime.now())
                .sport(Sport.BASKETBALL)
                .teamA("teamA")
                .teamB("teamB")
                .build()
        1 * modelMapper.map(request, Match.class) >> matchWithUuid
        1 * matchRepository.save(matchWithUuid) >> match

        when:
        def response = matchService.createMatch(request)

        then:
        response.getId() == match.getId()
    }

    def "Test updating a match in MatchService"() {
        given:
        def id = UUID.fromString("99ae700e-fd2f-461b-bf54-ae962751d50a")
        Match match = Match.builder()
                .id(id)
                .description("Sample Match")
                .matchDate(LocalDate.parse("2023-09-20"))
                .matchTime(LocalTime.parse("18:30:00"))
                .teamA("Team Fotis")
                .teamB("Team Bravo")
                .sport(Sport.FOOTBALL)
                .build()
        def updateMatchRequest = UpdateMatchRequest.builder()
                .description("New Sample Match")
                .matchDate(LocalDate.parse("2023-09-21"))
                .matchTime(LocalTime.parse("18:30:02"))
                .teamA("Team Fotis 2")
                .teamB("Team Bravo 2")
                .sport(Sport.BASKETBALL)
                .build()
        Match updatedMatch = Match.builder()
                .id(id)
                .description("New Sample Match")
                .matchDate(LocalDate.parse("2023-09-21"))
                .matchTime(LocalTime.parse("18:30:02"))
                .teamA("Team Fotis 2")
                .teamB("Team Bravo 2")
                .sport(Sport.BASKETBALL)
                .build()

        1 * matchRepository.findById(id) >> Optional.of(match)

        when:
        matchService.updateMatch(id, updateMatchRequest)

        then:
        1 * matchRepository.save(updatedMatch)
    }

    def "Test updating a match in MatchService - no match found"() {
        given:
        def id = UUID.fromString("99ae700e-fd2f-461b-bf54-ae962751d50a")
        def updateMatchRequest = UpdateMatchRequest.builder()
                .description("New Sample Match")
                .matchDate(LocalDate.parse("2023-09-21"))
                .matchTime(LocalTime.parse("18:30:02"))
                .teamA("Team Fotis 2")
                .teamB("Team Bravo 2")
                .sport(Sport.BASKETBALL)
                .build()

        1 * matchRepository.findById(id) >> Optional.empty()

        when:
        matchService.updateMatch(id, updateMatchRequest)

        then:
        def ex = thrown(EntityNotFoundException.class)
        ex.message == "Match not found with id: ${id}"
    }

    def "Delete existing match by ID"() {
        given:
        UUID existingMatchId = UUID.randomUUID()
        1 * matchRepository.findById(existingMatchId) >> Optional.of(Match.builder()
                .id(existingMatchId)
                .description("Sample Match")
                .matchDate(LocalDate.parse("2023-09-20"))
                .matchTime(LocalTime.parse("18:30:00"))
                .teamA("Team Fotis")
                .teamB("Team Bravo")
                .sport(Sport.FOOTBALL)
                .build())

        when:
        matchService.deleteMatch(existingMatchId)

        then:
        1 * matchRepository.deleteById(existingMatchId)
    }

    def "Attempt to delete non-existing match"() {
        given:
        UUID nonExistingMatchId = UUID.randomUUID()

        1 * matchRepository.findById(nonExistingMatchId) >> Optional.empty()

        when:
        matchService.deleteMatch(nonExistingMatchId)

        then:
        0 * matchRepository.deleteById(nonExistingMatchId)
        def exception = thrown(EntityNotFoundException.class)
        exception.message == "Match not found with id: ${nonExistingMatchId}"
    }
}

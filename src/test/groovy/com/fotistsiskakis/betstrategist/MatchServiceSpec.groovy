package com.fotistsiskakis.betstrategist

import com.fotistsiskakis.betstrategist.models.Match
import com.fotistsiskakis.betstrategist.models.Sport
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchRequest
import com.fotistsiskakis.betstrategist.repositories.MatchRepository
import com.fotistsiskakis.betstrategist.services.MatchService
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.modelmapper.ModelMapper
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

@ExtendWith(MockitoExtension)
class MatchServiceSpec extends Specification {

    MatchRepository matchRepository = Mock()

    ModelMapper modelMapper = Mock()

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
}

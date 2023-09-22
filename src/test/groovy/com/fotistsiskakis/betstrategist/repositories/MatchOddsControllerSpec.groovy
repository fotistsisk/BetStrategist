package com.fotistsiskakis.betstrategist.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fotistsiskakis.betstrategist.controllers.MatchController
import com.fotistsiskakis.betstrategist.controllers.MatchOddsController
import com.fotistsiskakis.betstrategist.models.Sport
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchOddsRequest
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchRequest
import com.fotistsiskakis.betstrategist.models.requests.GetMatchOddsFilterRequest
import com.fotistsiskakis.betstrategist.models.requests.UpdateMatchOddsRequest
import com.fotistsiskakis.betstrategist.services.MatchFilterService
import com.fotistsiskakis.betstrategist.services.MatchOddsFilterService
import com.fotistsiskakis.betstrategist.services.MatchOddsService
import com.fotistsiskakis.betstrategist.services.MatchService
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class MatchOddsControllerSpec extends Specification {
    def mockMvc
    def matchOddsService = Mock(MatchOddsService.class)
    def matchOddsFilterService = Mock(MatchOddsFilterService.class)
    ObjectMapper mapper

    MatchOddsController matchOddsController

    def setup() {
        mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule());
        matchOddsController = new MatchOddsController(matchOddsService, matchOddsFilterService)
        mockMvc = MockMvcBuilders.standaloneSetup(matchOddsController).build()
    }

    def "test createMatchOdds"() {
        given:
        CreateMatchOddsRequest createMatchOddsRequest = CreateMatchOddsRequest.builder()
                .matchId(UUID.randomUUID().toString())
                .specifier("Sample specifier")
                .odd(1.2)
                .build()

        def jsonRequest = mapper.writeValueAsString(createMatchOddsRequest)

        when:
        def response = mockMvc.perform(post("/matches/odds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn()
        then:
        response != null
    }

    def "test getFilteredMatchOdds"() {
        given:
        GetMatchOddsFilterRequest getMatchOddsFilterRequest = GetMatchOddsFilterRequest.builder()
                .matchId(UUID.randomUUID().toString())
                .specifier("New Sample specifier")
                .odd(2.2)
                .build()

        def jsonRequest = mapper.writeValueAsString(getMatchOddsFilterRequest)

        when:
        def response = mockMvc.perform(get("/matches/odds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn()
        then:
        response != null
    }

    def "test updateMatchOdds"() {
        given:
        String id = UUID.randomUUID().toString()
        UpdateMatchOddsRequest updateMatchOddsRequest = UpdateMatchOddsRequest.builder()
                .matchId(UUID.randomUUID().toString())
                .specifier("New Sample specifier")
                .odd(2.2)
                .build()

        def jsonRequest = mapper.writeValueAsString(updateMatchOddsRequest)

        when:
        def response = mockMvc.perform(put("/matches/odds/${id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn()
        then:
        response != null
    }

    def "test deleteMatchOdds"() {
        given:
        String id = UUID.randomUUID().toString()

        when:
        def response = mockMvc.perform(delete("/matches/odds/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn()
        then:
        response != null
    }
}

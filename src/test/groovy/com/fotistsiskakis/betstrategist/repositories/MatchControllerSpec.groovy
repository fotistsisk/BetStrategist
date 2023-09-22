package com.fotistsiskakis.betstrategist.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fotistsiskakis.betstrategist.controllers.MatchController
import com.fotistsiskakis.betstrategist.models.Sport
import com.fotistsiskakis.betstrategist.models.requests.CreateMatchRequest
import com.fotistsiskakis.betstrategist.models.requests.GetMatchFilterRequest
import com.fotistsiskakis.betstrategist.models.requests.UpdateMatchRequest
import com.fotistsiskakis.betstrategist.services.MatchFilterService
import com.fotistsiskakis.betstrategist.services.MatchService
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import java.time.LocalDate
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class MatchControllerSpec extends Specification {

    def mockMvc
    def matchService = Mock(MatchService.class)
    def matchFilterService = Mock(MatchFilterService.class)
    ObjectMapper mapper

    MatchController matchController

    def setup() {
        mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule());
        matchController = new MatchController(matchService, matchFilterService)
        mockMvc = MockMvcBuilders.standaloneSetup(matchController).build()
    }

    def "test createMatch"() {
        given:
        CreateMatchRequest createMatchRequest = CreateMatchRequest.builder()
                .description("Sample Match")
                .matchDate(LocalDate.parse("2023-09-22"))
                .matchTime(LocalTime.parse("18:30:00"))
                .teamA("Team Amalia")
                .teamB("Team Dimitris")
                .sport(Sport.FOOTBALL)
                .build()

        def jsonRequest = mapper.writeValueAsString(createMatchRequest)

        when:
        def response = mockMvc.perform(post("/matches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn()
        then:
        response != null
    }

    def "test getFilteredMatches"() {
        given:
        GetMatchFilterRequest filterRequest = GetMatchFilterRequest.builder()
                .id(UUID.randomUUID().toString())
                .build()

        def jsonRequest = mapper.writeValueAsString(filterRequest)

        when:
        def response = mockMvc.perform(get("/matches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn()
        then:
        response != null
    }

    def "test updateMatch"() {
        given:
        String id = UUID.randomUUID().toString()
        UpdateMatchRequest filterRequest = UpdateMatchRequest.builder()
                .description("new description")
                .build()

        def jsonRequest = mapper.writeValueAsString(filterRequest)

        when:
        def response = mockMvc.perform(put("/matches/${id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn()
        then:
        response != null
    }

    def "test deleteMatch"() {
        given:
        String id = UUID.randomUUID().toString()

        when:
        def response = mockMvc.perform(delete("/matches/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn()
        then:
        response != null
    }
}
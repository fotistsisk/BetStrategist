package com.fotistsiskakis.betstrategist.services

import spock.lang.Specification
import com.fotistsiskakis.betstrategist.models.Match
import com.fotistsiskakis.betstrategist.repositories.MatchRepository
import com.fotistsiskakis.betstrategist.models.requests.GetMatchFilterRequest

class MatchFilterServiceSpec extends Specification {

    MatchRepository matchRepository = Mock()
    MatchFilterService matchFilterService
    def setup(){
        matchFilterService = new MatchFilterService(matchRepository)
    }


    def "Test getFilteredMatches when request is null"() {
        when:
        List<Match> result = matchFilterService.getFilteredMatches(null)

        then:
        result == matchRepository.findAll()
    }

    def "Test getFilteredMatches when request has no filter criteria"() {
        when:
        GetMatchFilterRequest filterRequest = GetMatchFilterRequest.builder().build()
        List<Match> result = matchFilterService.getFilteredMatches(filterRequest)

        then:
        result == matchRepository.findAll()
    }
}

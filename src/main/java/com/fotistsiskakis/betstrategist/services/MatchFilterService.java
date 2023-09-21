package com.fotistsiskakis.betstrategist.services;

import com.fotistsiskakis.betstrategist.models.Match;
import com.fotistsiskakis.betstrategist.models.Sport;
import com.fotistsiskakis.betstrategist.models.requests.MatchFilterRequest;
import com.fotistsiskakis.betstrategist.repositories.MatchRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MatchFilterService {

    private MatchRepository matchRepository;

    public List<Match> getFilteredMatches(MatchFilterRequest filterRequest) {
        if(isRequestNullOrEmpty(filterRequest)){
            return matchRepository.findAll();
        }

        return matchRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            addPredicate(filterRequest.getId(), root.get("id"), predicates, cb);
            addPredicate(filterRequest.getDescription(), root.get("description"), predicates, cb);
            if (filterRequest.getMatchDate() != null) {
                LocalDate matchDate = LocalDate.parse(filterRequest.getMatchDate());
                predicates.add(cb.equal(root.get("matchDate"), matchDate));
            }
            addPredicate(filterRequest.getTeamA(), root.get("teamA"), predicates, cb);
            addPredicate(filterRequest.getTeamB(), root.get("teamB"), predicates, cb);

            String matchTimeStr = filterRequest.getMatchTime();
            if(matchTimeStr!=null){
                LocalTime matchTime = LocalTime.parse(matchTimeStr);
                addPredicate(matchTime, root.get("matchTime"), predicates, cb);
            }

            if(filterRequest.getSport()!=null) {
                try {
                    addPredicate(Sport.valueOf(filterRequest.getSport()), root.get("sport"), predicates, cb);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Sport value is incorrect");
                }
            }


            if (filterRequest.getTeamA() == null && filterRequest.getTeamB() == null && filterRequest.getTeamName()!=null) {
                Predicate teamNamePredicate = cb.or(
                        cb.equal(root.get("teamA"), filterRequest.getTeamName()),
                        cb.equal(root.get("teamB"), filterRequest.getTeamName())
                );
                predicates.add(teamNamePredicate);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    private <T> void addPredicate(T value, Path<T> expression, List<Predicate> predicates, CriteriaBuilder cb) {
        if (value != null) {
            predicates.add(cb.equal(expression, value));
        }
    }

    private static boolean isRequestNullOrEmpty(MatchFilterRequest filterRequest) {
        return filterRequest == null ||
                (filterRequest.getId() == null &&
                        filterRequest.getDescription() == null &&
                        filterRequest.getMatchDate() == null &&
                        filterRequest.getMatchTime() == null &&
                        filterRequest.getTeamA() == null &&
                        filterRequest.getTeamB() == null &&
                        filterRequest.getSport() == null &&
                        filterRequest.getTeamName() == null);
    }
}

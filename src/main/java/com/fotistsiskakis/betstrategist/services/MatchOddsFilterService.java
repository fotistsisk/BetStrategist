package com.fotistsiskakis.betstrategist.services;

import com.fotistsiskakis.betstrategist.models.MatchOdds;
import com.fotistsiskakis.betstrategist.models.requests.GetMatchOddsFilterRequest;
import com.fotistsiskakis.betstrategist.repositories.MatchOddsRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MatchOddsFilterService {

    private MatchOddsRepository matchOddsRepository;

    public List<MatchOdds> getFilteredMatchOdds(GetMatchOddsFilterRequest filterRequest) {
        if (isRequestNullOrEmpty(filterRequest)) {
            return matchOddsRepository.findAll();
        }

        return matchOddsRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            String id = filterRequest.getId();
            if (id!= null) {
                addPredicate(Utils.getUuidFromString(id), root.get("id"), predicates, cb);
            }
            String matchId = filterRequest.getMatchId();
            if (matchId!=null) {
                addPredicate(Utils.getUuidFromString(matchId), root.get("match").get("id"), predicates, cb);
            }
            addPredicate(filterRequest.getSpecifier(), root.get("specifier"), predicates, cb);
            addPredicate(filterRequest.getOdd(), root.get("odd"), predicates, cb);

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    private <T> void addPredicate(T value, Path<T> expression, List<Predicate> predicates, CriteriaBuilder cb) {
        if (value != null) {
            predicates.add(cb.equal(expression, value));
        }
    }

    private static boolean isRequestNullOrEmpty(GetMatchOddsFilterRequest filterRequest) {
        return filterRequest == null ||
                (filterRequest.getId() == null &&
                        filterRequest.getMatchId() == null &&
                        filterRequest.getSpecifier() == null &&
                        filterRequest.getOdd() == null);
    }
}

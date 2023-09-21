package com.fotistsiskakis.betstrategist.repositories;

import com.fotistsiskakis.betstrategist.models.Match;
import com.fotistsiskakis.betstrategist.models.MatchOdds;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchOddsRepository extends JpaRepository<MatchOdds, UUID> {
    List<MatchOdds> findAll(Specification<Match> spec);
}
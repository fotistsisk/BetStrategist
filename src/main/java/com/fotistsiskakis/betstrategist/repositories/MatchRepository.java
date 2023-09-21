package com.fotistsiskakis.betstrategist.repositories;

import com.fotistsiskakis.betstrategist.models.Match;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepository extends JpaRepository<Match, UUID> {
    List<Match> findAll(Specification<Match> spec);
}
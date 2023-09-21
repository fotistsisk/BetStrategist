package com.fotistsiskakis.betstrategist.repositories;

import com.fotistsiskakis.betstrategist.models.Match;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepository extends JpaRepository<Match, UUID> {
    @Query("SELECT m FROM Match m WHERE m.teamA = :teamName OR m.teamB = :teamName")
    List<Match> findByTeamAOrTeamB(@Param("teamName") String teamName);

    List<Match> findAll(Specification<Match> spec);
}
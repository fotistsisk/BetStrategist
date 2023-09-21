package com.fotistsiskakis.betstrategist.repositories;

import com.fotistsiskakis.betstrategist.models.MatchOdds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MatchOddsRepository extends JpaRepository<MatchOdds, UUID> {
}
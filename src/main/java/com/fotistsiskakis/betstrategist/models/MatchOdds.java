package com.fotistsiskakis.betstrategist.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "match_odds")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchOdds {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false, insertable = false, updatable = false)
    private Match match;

    @Column(name = "specifier")
    @NotNull
    private String specifier;

    @Column(name = "odd")
    @NotNull
    private Long odd;
}

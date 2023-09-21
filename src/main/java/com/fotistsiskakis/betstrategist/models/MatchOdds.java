package com.fotistsiskakis.betstrategist.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "match_odds", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"match_id", "specifier"})
})
public class MatchOdds {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "match_id")
    private Match match;

    @Column(name = "specifier")
    @NotNull
    private String specifier;

    @Column(name = "odd")
    @NotNull
    private BigDecimal odd;
}

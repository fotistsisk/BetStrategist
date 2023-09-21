package com.fotistsiskakis.betstrategist.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "match_odds", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"match.id", "specifier"})
})
public class MatchOdds {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch=FetchType.LAZY)
    @NotNull
    @ToString.Exclude
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "match.id")
    private Match match;

    @Column(name = "specifier")
    @NotNull
    private String specifier;

    @Column(name = "odd")
    @NotNull
    private BigDecimal odd;
}

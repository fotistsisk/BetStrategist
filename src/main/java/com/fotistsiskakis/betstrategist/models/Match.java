package com.fotistsiskakis.betstrategist.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "matches")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "match_date")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    @NotNull
    private LocalDate matchDate;

    @Column(name = "match_time")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @NotNull
    private LocalTime matchTime;

    @Column(name = "team_a")
    @NotNull
    private String teamA;

    @Column(name = "last_b")
    @NotNull
    private String teamB;

    @Column(name = "sport")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private Sport sport;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<MatchOdds> matchOddsList;
}

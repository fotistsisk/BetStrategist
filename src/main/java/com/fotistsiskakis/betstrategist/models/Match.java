package com.fotistsiskakis.betstrategist.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "matches")
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
    @Schema(type = "string", format = "time", example = "18:30:00")
    private LocalTime matchTime;

    @Column(name = "team_a")
    @NotNull
    private String teamA;

    @Column(name = "team_b")
    @NotNull
    private String teamB;

    @Column(name = "sport")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    @Schema(allowableValues = { "FOOTBALL", "BASKETBALL" })
    private Sport sport;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "match", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<MatchOdds> matchOddsList;
}

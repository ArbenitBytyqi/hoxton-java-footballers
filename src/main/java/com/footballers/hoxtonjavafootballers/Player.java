package com.footballers.hoxtonjavafootballers;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Player {
    @Id
    @GeneratedValue
    public Integer id;
    public String name;
    public String nationality;
    public Integer scoreOutOfTen;
    public boolean isReplacement;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "teamId", nullable = true)
    public Team team;

    public Player() {
    }
}

@RestController
class PlayerController {
    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private TeamRepo teamRepo;

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerRepo.findAll();
    }

    @PostMapping("/teams/{teamId}/players")
    public Player createPlayer(@RequestBody Player playerData, @PathVariable Integer teamId) {
        playerData.team = teamRepo.findById(teamId).get();
        return playerRepo.save(playerData);
    }
}

interface PlayerRepo extends JpaRepository<Player, Integer> {
}

interface TeamRepo extends JpaRepository<Team, Integer> {
}
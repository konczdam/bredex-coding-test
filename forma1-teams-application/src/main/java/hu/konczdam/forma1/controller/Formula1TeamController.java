package hu.konczdam.forma1.controller;

import hu.konczdam.forma1.model.Formula1Team;
import hu.konczdam.forma1.service.Formula1TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class Formula1TeamController {
    private final Formula1TeamService formula1TeamService;

    @GetMapping
    public ResponseEntity<List<Formula1Team>> getAllTeams() {
        List<Formula1Team> teams = formula1TeamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formula1Team> getTeamById(@PathVariable Integer id) {
        Optional<Formula1Team> team = formula1TeamService.getTeamById(id);
        return team.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Formula1Team> createTeam(@RequestBody Formula1Team team) {
        Formula1Team createdTeam = formula1TeamService.saveTeam(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) {
        formula1TeamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}

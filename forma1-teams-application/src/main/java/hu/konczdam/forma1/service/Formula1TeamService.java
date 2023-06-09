package hu.konczdam.forma1.service;

import hu.konczdam.forma1.model.Formula1Team;
import hu.konczdam.forma1.repository.Formula1TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Formula1TeamService {
    private final Formula1TeamRepository formula1TeamRepository;

    public List<Formula1Team> getAllTeams() {
        return formula1TeamRepository.findAll();
    }

    public Optional<Formula1Team> getTeamById(Integer id) {
        return formula1TeamRepository.findById(id);
    }

    public Formula1Team saveTeam(Formula1Team team) {
        return formula1TeamRepository.save(team);
    }

    public void deleteTeam(Integer id) {
        formula1TeamRepository.findById(id).ifPresent(team -> formula1TeamRepository.deleteById(id));
    }
}

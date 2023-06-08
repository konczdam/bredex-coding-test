package hu.konczdam.forma1.repository;

import hu.konczdam.forma1.model.Formula1Team;
import hu.konczdam.forma1.service.Formula1TeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Formula1TeamRepositoryTest {
    @Mock
    private Formula1TeamRepository formula1TeamRepository;

    @InjectMocks
    private Formula1TeamService formula1TeamService;

    @Test
    public void testGetAllTeams() {
        // Create a list of Formula1Team objects
        List<Formula1Team> teams = new ArrayList<>();
        teams.add(new Formula1Team(1, "Team A", Year.of(2000), 5, true));
        teams.add(new Formula1Team(2, "Team B", Year.of(1995), 3, false));

        // Mock the behavior of the repository
        when(formula1TeamRepository.findAll()).thenReturn(teams);

        // Call the service method
        List<Formula1Team> result = formula1TeamService.getAllTeams();

        // Verify the result
        assertEquals(2, result.size());
        assertEquals("Team A", result.get(0).getName());
        assertEquals(Year.of(2000), result.get(0).getYearFounded());
        assertEquals(5, result.get(0).getChampionshipsWon());
        assertTrue(result.get(0).isEntryFeePaid());
        assertEquals("Team B", result.get(1).getName());
        assertEquals(Year.of(1995), result.get(1).getYearFounded());
        assertEquals(3, result.get(1).getChampionshipsWon());
        assertFalse(result.get(1).isEntryFeePaid());

        // Verify that the repository method was called
        verify(formula1TeamRepository, times(1)).findAll();
    }

    @Test
    public void testGetTeamById() {
        // Create a Formula1Team object
        Formula1Team team = new Formula1Team(1, "Team A", Year.of(2000), 5, true);

        // Mock the behavior of the repository
        when(formula1TeamRepository.findById(1)).thenReturn(Optional.of(team));

        // Call the service method
        Optional<Formula1Team> result = formula1TeamService.getTeamById(1);

        // Verify the result
        assertTrue(result.isPresent());
        assertEquals(team, result.get());

        // Verify that the repository method was called
        verify(formula1TeamRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveTeam() {
        // Create a Formula1Team object
        Formula1Team team = new Formula1Team("Team A", Year.of(2000), 5, true);

        // Mock the behavior of the repository
        when(formula1TeamRepository.save(team)).thenReturn(team);

        // Call the service method
        Formula1Team savedTeam = formula1TeamService.saveTeam(team);

        // Verify the result
        assertEquals("Team A", savedTeam.getName());
        assertEquals(Year.of(2000), savedTeam.getYearFounded());
        assertEquals(5, savedTeam.getChampionshipsWon());
        assertTrue(savedTeam.isEntryFeePaid());

        // Verify that the repository method was called
        verify(formula1TeamRepository, times(1)).save(team);
    }

    @Test
    public void testDeleteTeam() {
        // Create a Formula1Team object
        Formula1Team team = new Formula1Team(1, "Team A", Year.of(2000), 5, true);

        // Mock the behavior of the repository
        when(formula1TeamRepository.findById(1)).thenReturn(Optional.of(team));

        // Call the service method
        formula1TeamService.deleteTeam(1);

        // Verify that the repository method was called
        verify(formula1TeamRepository, times(1)).findById(1);
        verify(formula1TeamRepository, times(1)).deleteById(1);
    }
}

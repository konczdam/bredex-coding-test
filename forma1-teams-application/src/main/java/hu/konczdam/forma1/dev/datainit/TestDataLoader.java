package hu.konczdam.forma1.dev.datainit;

import hu.konczdam.forma1.model.Formula1Team;
import hu.konczdam.forma1.repository.Formula1TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class TestDataLoader implements CommandLineRunner {
    private final Formula1TeamRepository formula1TeamRepository;

    @Override
    public void run(String... args) {
        // Create and save test data
        Formula1Team team1 = new Formula1Team("Team A", Year.of(2000), 5, true);
        Formula1Team team2 = new Formula1Team("Team B", Year.of(1995), 3, false);

        formula1TeamRepository.save(team1);
        formula1TeamRepository.save(team2);
    }
}

package hu.konczdam.forma1.repository;

import hu.konczdam.forma1.model.Formula1Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Formula1TeamRepository extends JpaRepository<Formula1Team, Integer> {
}

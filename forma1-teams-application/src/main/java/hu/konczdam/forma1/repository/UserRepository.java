package hu.konczdam.forma1.repository;

import hu.konczdam.forma1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Nullable User findByUsername(String username);
}

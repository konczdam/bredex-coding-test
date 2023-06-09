package hu.konczdam.forma1.repository;

import hu.konczdam.forma1.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Nullable Role findByName(String name);
}
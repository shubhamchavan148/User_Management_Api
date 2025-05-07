package springboot_project1.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot_project1.demo.Model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}


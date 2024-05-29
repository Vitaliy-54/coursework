package RestApiNews.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import RestApiNews.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername (String username);
}
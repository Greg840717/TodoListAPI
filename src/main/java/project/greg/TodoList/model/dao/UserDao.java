package project.greg.TodoList.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.greg.TodoList.model.entity.User;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Integer> {
    //@Query(value = "select id, name, gender, rate from \"user\" order by id", nativeQuery = true)
    //List<Map<String, Object>> findAllUsers();

    //List<User> findAllUsers();

    Optional<User> findByName(String name);

    Optional<User> findByNameAndPassword(String name, String password);
}

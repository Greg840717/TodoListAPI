package project.greg.TodoList.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.greg.TodoList.model.entity.Todo;

import java.util.List;

public interface TodoDao extends JpaRepository<Todo, Integer> {
    List<Todo> findByStatus(Integer status);

    List<Todo> findByTodoId(Integer todoId);

    List<Todo> findByUserId(Integer userId);
}

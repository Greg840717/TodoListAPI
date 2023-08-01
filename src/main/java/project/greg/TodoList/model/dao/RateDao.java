package project.greg.TodoList.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.greg.TodoList.model.entity.Rate;

import java.util.List;

public interface RateDao extends JpaRepository<Rate, Integer> {
    List<Rate> findByUserId(Integer userId);
}

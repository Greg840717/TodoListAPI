package project.greg.TodoList.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.greg.TodoList.model.entity.Tag;

import java.util.Optional;

public interface TagDao extends JpaRepository<Tag, Integer> {
    Tag findByTagName(String tagName);

    Optional<Tag> findByTagId(Integer tagId);
}

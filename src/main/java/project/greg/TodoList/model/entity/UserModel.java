package project.greg.TodoList.model.entity;

import java.util.Set;

public interface UserModel {
    Integer getId();

    String getName();

    Integer getRate();

    Set<Todo> getTodos();

}

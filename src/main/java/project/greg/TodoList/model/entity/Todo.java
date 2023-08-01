package project.greg.TodoList.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer todoId;
    @Column
    String todoName;
    @Column
    Integer status;

    @Column(name = "UserId")
    private Integer userId;

    @ManyToMany
    @JoinTable(name = "todo_tag",
            joinColumns = @JoinColumn(name = "todo_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<Tag> assignedTags = new ArrayList<>();
}

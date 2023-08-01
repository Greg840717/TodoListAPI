package project.greg.TodoList.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer tagId;
    @Column
    public String tagName;

    @JsonIgnore
    @ManyToMany(mappedBy = "assignedTags")
    Set<Todo> todoSet = new HashSet<>();
}

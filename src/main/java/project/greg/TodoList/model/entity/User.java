package project.greg.TodoList.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "`USER`")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    public String name;

    @Column(insertable = true, columnDefinition = "int default 1")
    Integer gender;

    @Column
    public String password;

    @OneToMany
    @JoinColumn(name = "UserId")
    private Set<Todo> todos;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Rate rate;
}

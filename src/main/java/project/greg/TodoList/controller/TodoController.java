package project.greg.TodoList.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.greg.TodoList.model.entity.Todo;
import project.greg.TodoList.service.TodoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class TodoController {
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    @Autowired
    TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity getTodoList() {
        List<Todo> todoList = todoService.getTodo();
        //logger.info("Get all todo list: {}", todoList);
        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity getTodo(@PathVariable Integer id) {
        Optional<Todo> todo = todoService.findById(id);
        logger.info(String.format("Get %d todo data: %s", id, todo));
        return ResponseEntity.status(HttpStatus.OK).body(todo);
    }

    @PostMapping("/todos")
    public ResponseEntity createTodo(@RequestBody Todo todo) {
        Integer id = todoService.createTodo(todo);
        logger.info(String.format("Create todo: %s, id: %d", todo, id));
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @ResponseBody
    @PutMapping("/todos/{id}")
    public ResponseEntity updateTodo(@PathVariable Integer id, @RequestBody Todo todo) {
        Boolean rlt = todoService.updateTodo(id, todo);
        if (!rlt) {
            logger.error(String.format("Update todo failed, id: %d, request: %s", id, todo));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status 欄位不能為空");
        }
        logger.info(String.format("update todo success, id: %d, request: %s", id, todo));
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @ResponseBody
    @DeleteMapping("/todos/{id}")
    public ResponseEntity deleteTodo(@PathVariable Integer id) {
        Boolean rlt = todoService.deleteTodo(id);
        if (!rlt) {
            logger.error(String.format("Delete todo failed, id: %d", id));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID不存在");
        }
        logger.info(String.format("Delete todo success, id: %d", id));
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/todos/status/{status}")
    public ResponseEntity getTodoByStatus(@PathVariable Integer status) {
        Iterable<Todo> todo = todoService.getTodoByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(todo);
    }

    @ResponseBody
    @PutMapping("/todos/{id}/tags/{tagId}")
    public ResponseEntity updateTodo(@PathVariable Integer id, @PathVariable Integer tagId) {
        List<Integer> tagIds = new ArrayList<>();
        tagIds.add(tagId);
        Boolean rlt = todoService.updateTodoTags(id, tagIds);
        if (!rlt) {
            logger.error(String.format("Update todo failed, database does not exist tag id: %d", tagId));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag id 不存在");
        }
        logger.info(String.format("update todo success, id: %d, Tag id: %d", id, tagId));
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}

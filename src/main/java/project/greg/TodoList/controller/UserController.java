package project.greg.TodoList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.greg.TodoList.model.entity.Todo;
import project.greg.TodoList.model.entity.User;
import project.greg.TodoList.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users/all")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @GetMapping("/users/{id}/todos")
    public ResponseEntity getTodosByUserId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getTodosByUserId(id));
    }

    @GetMapping(value = {"/login", "/user/login"})
    public ResponseEntity login(@RequestBody User user) {
        Integer userId = userService.getUser(user);
        switch (userId) {
            case -1:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用戶不存在，請先註冊");
            case -2:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密碼錯誤");
            default:
                return ResponseEntity.status(HttpStatus.OK).body(userId);
        }
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user) {
        Integer id = userService.createUser(user);
        if (id.equals(-1)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用戶已存在");
        }
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PostMapping("/users/{id}/todos")
    public ResponseEntity createUser(@PathVariable Integer id, @RequestBody Todo todo) {
        Integer index = userService.createTodo(id, todo);
        return ResponseEntity.status(HttpStatus.OK).body(index);
    }

    @DeleteMapping("/users/{id}/todos/clear")
    public ResponseEntity deleteTodos(@PathVariable Integer id) {
        userService.clearTodo(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}

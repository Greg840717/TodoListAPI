package project.greg.TodoList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.greg.TodoList.model.dao.TodoDao;
import project.greg.TodoList.model.dao.UserDao;
import project.greg.TodoList.model.entity.Tag;
import project.greg.TodoList.model.entity.Todo;
import project.greg.TodoList.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    TodoDao todoDao;
    @Autowired
    RateService rateService;
    @Autowired
    TodoService todoService;

    public Iterable<User> getAllUsers() {
        return userDao.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userDao.findById(id);
    }

    public Integer getUser(User user) {
        Optional<User> isExistUser = userDao.findByName(user.getName());
        // 找不到用戶
        if (!isExistUser.isPresent()) {
            return -1;
        }
        isExistUser = userDao.findByNameAndPassword(user.getName(), user.getPassword());
        // 密碼不正確
        if (!isExistUser.isPresent()) {
            return -2;
        }
        // 找到用戶，返回user id
        return isExistUser.get().getId();
    }

    public Set<Todo> getTodosByUserId(Integer id) {
        Optional<User> data = userDao.findById(id);
        if (!data.isPresent()) {
            return null;
        }
        Set<Todo> todos = data.get().getTodos();
        return todos;
    }

    public Integer createUser(User user) {
        Optional<User> isExist = userDao.findByName(user.getName());
        if (isExist.isPresent()) {
            return -1;
        }
        User newUser = userDao.save(user);
        rateService.createRate(newUser.getId(), 0);
        return newUser.getId();
    }

    public Integer createTodo(Integer id, Todo todo) {
        User user = userDao.findById(id).get();
        Set<Todo> todos = null;
        todos = user.getTodos();
        Todo newTodo = new Todo();
        newTodo.setTodoName(todo.getTodoName());
        newTodo.setUserId(todo.getUserId());
        newTodo.setStatus(todo.getStatus());
        newTodo = todoDao.save(newTodo);
        if (todo.getAssignedTags().size() != 0) {
            List<Integer> tagIds = new ArrayList<>();
            for (Tag tag : todo.getAssignedTags()) {
                tagIds.add(tag.getTagId());
            }
            todoService.updateTodoTags(newTodo.getTodoId(), tagIds);
        }
        todos.add(newTodo);
        user.setTodos(todos);
        return userDao.save(user).getId();
    }

    public void clearTodo(Integer id) {
        User user = userDao.findById(id).get();
        Set<Todo> todos = user.getTodos();
        for (Todo todo : todos) {
            todoDao.deleteById(todo.getTodoId());
        }
    }
}

package project.greg.TodoList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.greg.TodoList.model.dao.RateDao;
import project.greg.TodoList.model.dao.TagDao;
import project.greg.TodoList.model.dao.TodoDao;
import project.greg.TodoList.model.entity.Rate;
import project.greg.TodoList.model.entity.Tag;
import project.greg.TodoList.model.entity.Todo;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    TodoDao todoDao;
    @Autowired
    TagDao tagDao;
    @Autowired
    RateDao rateDao;

    public List<Todo> getTodo() {
        return todoDao.findAll();
    }

    public Integer createTodo(Todo todo) {
        Todo newTodo = todoDao.save(todo);
        return newTodo.getTodoId();
    }

    public Boolean updateTodo(Integer id, Todo todo) {
        Optional<Todo> isExistTodo = findById(id);
        // todo_id不存在，返回false
        if (!isExistTodo.isPresent())
            return false;
        Todo newTodo = isExistTodo.get();
        // Request body內沒有status值，返回false
        if (todo.getStatus() == null)
            return false;
        Integer newStatus = todo.getStatus();
        Rate rate = rateDao.findByUserId(newTodo.getUserId()).get(0);
        // status原本不等於2，更新status, rate分數加10分
        if (!newTodo.getStatus().equals(2) && newStatus.equals(2)) {
            newTodo.setStatus(newStatus);
            Integer newRate = rate.getRate() + 10;
            rate.setRate(newRate);
            rateDao.save(rate);
            todoDao.save(newTodo);
        }
        //newTodo.setUpdateTime(todo.getUpdateTime());
        return true;
    }

    public Optional<Todo> findById(Integer id) {
        Optional<Todo> todo = todoDao.findById(id);
        return todo;
    }

    public Boolean deleteTodo(Integer id) {
        Optional<Todo> isExistTodo = findById(id);
        if (!isExistTodo.isPresent())
            return false;
        todoDao.deleteById(id);
        return true;
    }

    public Iterable<Todo> getTodoByStatus(Integer status) {
        return todoDao.findByStatus(status);
    }

    public Boolean updateTodoTags(Integer todoId, List<Integer> tagIds) {
        List<Tag> tags = null;
        Todo todo = todoDao.findById(todoId).get();
        tags = todo.getAssignedTags();
        for (Integer tagId : tagIds) {
            Optional<Tag> tag = tagDao.findById(tagId);
            if (!tag.isPresent()) {
                return false;
            }
            tags.add(tag.get());
        }
        todo.setAssignedTags(tags);
        todoDao.save(todo);
        return true;
    }
}

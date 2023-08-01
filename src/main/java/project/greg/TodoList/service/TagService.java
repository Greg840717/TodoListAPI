package project.greg.TodoList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.greg.TodoList.model.dao.TagDao;
import project.greg.TodoList.model.entity.Tag;

import java.util.List;

@Service
public class TagService {
    @Autowired
    TagDao tagDao;

    public Iterable<Tag> getAllTags() {
        return tagDao.findAll();
    }

    public void createTag(List<Tag> tags) {
        for (Tag tag : tags) {
            Tag newTag = tagDao.save(tag);
        }
    }
}

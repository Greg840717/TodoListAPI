package project.greg.TodoList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.greg.TodoList.model.entity.Tag;
import project.greg.TodoList.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public ResponseEntity getAllTags() {
        Iterable<Tag> data = tagService.getAllTags();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PostMapping("/tags")
    public ResponseEntity createTag(@RequestBody List<Tag> tags) {
        tagService.createTag(tags);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sucess");
    }
}

package com.example.demo.controller;

import com.example.demo.exception.DataNotFoundException;
import com.example.demo.model.GeneralResponse;
import com.example.demo.model.ToDo;
import com.example.demo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ToDoController {

    @Autowired
    ToDoRepository todoRepo;

    @GetMapping("/todos")
    public GeneralResponse<Iterable<ToDo>> browse() {
        GeneralResponse<Iterable<ToDo>> resp = new GeneralResponse<>();
        return resp.generateSuccess("200", todoRepo.findAll());
    }

    @GetMapping("/todos/{id}")
    public GeneralResponse<ToDo> read(@PathVariable Long id) {
        Optional<ToDo> data = todoRepo.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException();
        }
        GeneralResponse<ToDo> resp = new GeneralResponse<>();
        return resp.generateSuccess("200", data.get());
    }

    @PutMapping("/todos/{id}")
    public GeneralResponse<ToDo> edit(@PathVariable Long id, @Valid @RequestBody ToDo body) {
        Optional<ToDo> existingData = todoRepo.findById(id);
        if (existingData.isEmpty()) {
            throw new DataNotFoundException();
        }
        body.setId(id);
        GeneralResponse<ToDo> resp = new GeneralResponse<>();
        return resp.generateSuccess("200", todoRepo.save(body));
    }

    @PostMapping("/todo")
    public GeneralResponse<ToDo> add(@Valid @RequestBody ToDo body) {
        GeneralResponse<ToDo> resp = new GeneralResponse<>();
        return resp.generateSuccess("200", todoRepo.save(body));
    }

    @DeleteMapping("/todos/{id}")
    public GeneralResponse<ToDo> destroy(@PathVariable Long id) {
        Optional<ToDo> existingData = todoRepo.findById(id);
        if (existingData.isEmpty()) {
            throw new DataNotFoundException();
        }
        todoRepo.delete(existingData.get());
        GeneralResponse<ToDo> resp = new GeneralResponse<>();
        return resp.generateSuccess("200", null);
    }

}

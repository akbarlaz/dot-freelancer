package com.example.demo.repository.mock;

import com.example.demo.model.mock.MockModel;
import org.springframework.data.repository.CrudRepository;

public interface MockRepository extends CrudRepository<MockModel, Long> {
}

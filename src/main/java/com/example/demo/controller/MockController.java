package com.example.demo.controller;

import com.example.demo.model.GeneralResponse;
import com.example.demo.model.mock.MockModel;
import com.example.demo.repository.mock.MockRepository;
import com.example.demo.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

@RestController
public class MockController {

    @Value("${env.mock_url}")
    private String mockUrl;

    @Autowired
    MockRepository mockRepository;

    private Retrofit getRetrofit() {
        System.out.println(mockUrl);
        return new Retrofit.Builder()
                .baseUrl(mockUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @GetMapping("/mocks-database")
    public GeneralResponse<Iterable<MockModel>> browseFromDatabaseOnly() {
        GeneralResponse<Iterable<MockModel>> resp = new GeneralResponse<>();
        return resp.generateSuccess("200", mockRepository.findAll());
    }

    @GetMapping("/mocks")
    public GeneralResponse<Iterable<MockModel>> browse() throws IOException {
        GeneralResponse<Iterable<MockModel>> resp = new GeneralResponse<>();
        Retrofit retrofit = getRetrofit();
        if (mockRepository.count() == 0) {
            MockService service = retrofit.create(MockService.class);
            List<MockModel> response = service.browse().execute().body();
            mockRepository.saveAll(response);
            return resp.generateSuccess("200", response);
        } else {
            return resp.generateSuccess("200", mockRepository.findAll());
        }
    }
}

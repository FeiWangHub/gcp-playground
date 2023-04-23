package com.feiwanghub.subcontroller.jpa;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestTableService {

    private final TestTableRepository testTableRepository;

    public TestTableService(TestTableRepository testTableRepository) {
        this.testTableRepository = testTableRepository;
    }

    public TestTable save(TestTable testTable) {
        return testTableRepository.save(testTable);
    }

    public List<TestTable> findByName(String name) {
        return testTableRepository.findAllByName(name);
    }
}

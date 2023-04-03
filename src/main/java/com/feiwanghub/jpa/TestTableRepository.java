package com.feiwanghub.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestTableRepository extends JpaRepository<TestTable, Long> {
    List<TestTable> findAllByName(String name);
}

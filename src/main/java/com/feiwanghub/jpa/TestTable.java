package com.feiwanghub.jpa;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "test_table")
public class TestTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
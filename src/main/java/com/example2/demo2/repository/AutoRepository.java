package com.example2.demo2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example2.demo2.model.AutoModel;

@Repository
public interface AutoRepository extends CrudRepository<AutoModel, Long> {

}

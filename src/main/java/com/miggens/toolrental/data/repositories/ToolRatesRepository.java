package com.miggens.toolrental.data.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

import com.miggens.toolrental.data.models.ToolRateEntity;

public interface ToolRatesRepository extends CrudRepository<ToolRateEntity, String>{
    Optional<ToolRateEntity> findById(String id); 
    List<ToolRateEntity> findAll();
}
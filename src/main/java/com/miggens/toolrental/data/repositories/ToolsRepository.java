package com.miggens.toolrental.data.repositories;

import java.util.List;
import java.util.Optional;
import com.miggens.toolrental.data.models.ToolEntity;

import org.springframework.data.repository.CrudRepository;

public interface ToolsRepository extends CrudRepository<ToolEntity, String>{
   //Optional<ToolEntity> findById(String id);
   Optional<ToolEntity> findById(String id); 
   List<ToolEntity> findAll();
}


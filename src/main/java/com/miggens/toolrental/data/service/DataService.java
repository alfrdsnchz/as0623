package com.miggens.toolrental.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miggens.toolrental.data.models.ToolEntity;
import com.miggens.toolrental.data.models.ToolRateEntity;
import com.miggens.toolrental.data.repositories.ToolRatesRepository;
import com.miggens.toolrental.data.repositories.ToolsRepository;

@Service
public class DataService {
    
    @Autowired
    private ToolsRepository toolsRepository; 

    @Autowired
    private ToolRatesRepository toolRatesRepository; 

    public ToolEntity getToolById(String id) {
        Optional<ToolEntity> toolOpt = toolsRepository.findById(id); 

        if (toolOpt.isEmpty()) return null; 

        return toolOpt.get(); 
    }

    public List<ToolEntity> getAllTools() {
        return toolsRepository.findAll(); 
    }

    public ToolRateEntity getToolRatesById(String id) {
        Optional<ToolRateEntity> rateOpt = toolRatesRepository.findById(id);

        if (rateOpt.isEmpty()) return null; 

        return rateOpt.get(); 
    }

    public List<ToolRateEntity> getAllToolRates() {
        return toolRatesRepository.findAll(); 
    }

}

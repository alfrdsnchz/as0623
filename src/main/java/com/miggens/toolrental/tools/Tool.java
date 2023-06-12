package com.miggens.toolrental.tools;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Tool {
    private String toolCode; 
    private String toolType; 
    private String toolBrand;
    private ToolRate toolRate; 
    
    public Tool(String code, String type, String brand, ToolRate rate) {
        this.toolCode = code;
        this.toolType = type; 
        this.toolBrand = brand; 
        this.toolRate = rate; 
    }
}

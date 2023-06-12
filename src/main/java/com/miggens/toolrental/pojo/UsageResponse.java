package com.miggens.toolrental.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsageResponse {

    public UsageResponse(String msg) {
        this.message = msg; 
    }

    public String message;     
}

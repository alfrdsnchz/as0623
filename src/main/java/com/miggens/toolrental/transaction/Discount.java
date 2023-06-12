package com.miggens.toolrental.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Discount {
    private Integer percent;
    private Integer amount;

    public Discount(Integer percent, Integer amount) {
        this.percent = percent; 
        this.amount = amount; 
    }
}

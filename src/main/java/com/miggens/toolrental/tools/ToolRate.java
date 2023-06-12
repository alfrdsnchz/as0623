package com.miggens.toolrental.tools;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ToolRate {
    private Integer dailyRate; 
    private Boolean weekdayCharge; 
    private Boolean weekendCharge; 
    private Boolean holidayCharge; 

    public ToolRate(Integer rate, Boolean weekday, Boolean weekend, Boolean holiday) {
        this.dailyRate = rate; 
        this.weekdayCharge = weekday; 
        this.weekendCharge = weekend; 
        this.holidayCharge = holiday;
    }
}
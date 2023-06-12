package com.miggens.toolrental.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tool_rates")
public class ToolRateEntity {
    
    @Id
    @Column(name = "tool_type")
    private String type; 
    
    @Column(name = "daily_rate")
    private Integer rate; 
    
    @Column(name = "weekday_charge")
    private Boolean weekdayCharge;
    
    @Column(name = "weekend_charge")
    private Boolean weekendCharge;

    @Column(name = "holiday_charge")
    private Boolean holidayCharge;
    

}
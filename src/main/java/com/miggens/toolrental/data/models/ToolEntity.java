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
@Entity(name = "tool")
@Table(name = "tools")
public class ToolEntity {
    @Id
    @Column(name = "tool_code")
    private String code;

    @Column(name = "tool_type")
    private String type; 

    @Column(name = "tool_brand")
    private String brand; 
}

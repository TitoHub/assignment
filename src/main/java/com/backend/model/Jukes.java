/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author pearl
 */
@Getter
@Setter
@ToString
public class Jukes {
    
    public String id;
    public String model;
    public Component[] components;

    public Jukes(String id, String model, Component[] components) {
        this.id = id;
        this.model = model;
        this.components = components;
    }

    
}

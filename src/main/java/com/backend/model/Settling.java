/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author pearl
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Settling {
    public String id;
    public String[] requires;

    public Settling(String id, String[] requires) {
        this.id = id;
        this.requires = requires;
    }  
}

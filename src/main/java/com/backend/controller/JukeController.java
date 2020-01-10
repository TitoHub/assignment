/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.controller;

import com.backend.model.Jukes;
import com.backend.service.JukeService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JukeController {
      
    @Autowired
    private JukeService jukeService;
    
    
    @RequestMapping("/jukeboxes")
    @GetMapping
    public ResponseEntity getJukes(@RequestParam(value = "settingId") String settingId,
                        @RequestParam(value = "model", required = false) String model, 
                        @RequestParam(value = "offset", required = false) Integer offset, 
                        @RequestParam(value = "limit", required = false) Integer limit) throws IOException {
        return jukeService.getJukeboxes(settingId, model, offset, limit);
            
    }
    
}

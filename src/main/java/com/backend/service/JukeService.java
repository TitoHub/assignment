/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.service;

import com.backend.model.Jukes;
import com.backend.model.Settings;
import com.backend.model.Settling;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author pearl
 */
@Service
public class JukeService {
        
    @Value("${jukes.api}")
    private String jukes;
    
    @Value("${settings.api}")
    private String settings;
    
    private final  int Limit = 10;
   private static final String INVALID_PAGINATION = "neither offset nor limit should be a negative number";
    
    public ResponseEntity getJukeboxes(String settingId, String model, Integer offset, Integer limit) throws IOException {      
        if(offset != null && offset < 0 || limit != null && limit < 0){
            return new ResponseEntity(INVALID_PAGINATION, HttpStatus.BAD_REQUEST);
        }
        List<String[]> requires = Arrays.stream(getSettings().getSettings()).filter(s -> s.getId().equalsIgnoreCase(settingId))
                                .map(Settling :: getRequires).collect(Collectors.toList());
       
        List<Jukes> juks = Arrays.stream(getjukes()).filter(juke -> Arrays.stream(juke.getComponents()).map(component -> component.getName())
                        .collect(Collectors.toList()).containsAll(Arrays.asList(requires.get(0)))).collect(Collectors.toList());
        
        List<Jukes> jukeboxes = juks.stream().filter(StringUtils.isNotBlank(model) ? j -> j.getModel().equalsIgnoreCase(model) : j -> true)
                .skip(offset != null ? offset : 0).limit(limit != null ? limit : Limit).collect(Collectors.toList());
        
        if(jukeboxes == null || jukeboxes.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity(jukeboxes, HttpStatus.OK);
    }
    
    public Settings getSettings() {
        try {
            HttpURLConnection httpcon = (HttpURLConnection) new URL(settings).openConnection();
            httpcon.setRequestMethod("GET");
            httpcon.connect();
            
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return  new Gson().fromJson(sb.toString(), Settings.class);
        } catch (IOException ex) {
        }
        return new Settings();
    }
    public Jukes[] getjukes() {
        try {
            HttpURLConnection httpcon = (HttpURLConnection) new URL(jukes).openConnection();
            httpcon.setRequestMethod("GET");
            httpcon.connect();
            
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            } 
            return  new Gson().fromJson(sb.toString(), Jukes[].class);
        } catch (IOException ex) {
        }
        return new Jukes[]{};
    }

}

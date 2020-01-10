/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.service;

import com.backend.model.Component;
import com.backend.model.Jukes;
import com.backend.model.Settings;
import com.backend.model.Settling;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
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
    public List<Jukes> getJukeboxes(String settingId, String model, Integer offset, Integer limit) throws IOException {
          
        //Arrays.stream(initialData)
//                             .filter(v -> Arrays.stream(filters)
//                                                .allMatch(f -> f.test(v)))
//                             .toArray();
        List<String[]> requires = Arrays.stream(getSettings().getSettings())
                .filter(s -> s.getId().equalsIgnoreCase(settingId))
                .map(Settling :: getRequires).collect(Collectors.toList());
        System.out.println("REQUIRES "+Arrays.toString(requires.get(0)));
        //Arrays.asList(j.getComponents()).contains(Arrays.asList(requires))
        return Arrays.stream(getjukes())
                .filter(j -> Arrays.stream(j.getComponents()).allMatch(r -> requires.))
                .filter((j) -> (j.getModel().equalsIgnoreCase(model))).skip(offset != null ? offset : 0)
                .limit(limit != null ? limit : Limit)
                .collect(Collectors.toList());
//        for(Jukes j : getjukes()){
//            for(Component c : j.getComponents()){
//                if(Arrays.asList(c.getName()).contains("money_storage")){
//                 System.out.println(j.toString());
//             }
//            }
//        }
     
//       return jukeboxList.stream().filter((j) -> (j.getModel().equalsIgnoreCase(model))).skip(offset != null ? offset : 0)
//                .limit(limit != null ? limit : Limit)
//                .collect(Collectors.toList());
      return new ArrayList()    ;
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
            System.out.println(String.format("details for settings  : "+ sb.toString()));
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
            System.out.println(String.format("details for jukes  : "+ sb.toString()));
            return  new Gson().fromJson(sb.toString(), Jukes[].class);
        } catch (IOException ex) {
        }
        return new Jukes[]{};
    }

}

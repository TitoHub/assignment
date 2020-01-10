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
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author pearl
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class JukeServiceUnitTest {
    
     @Before
    public void setup() {
		MockitoAnnotations.initMocks(this);
    }
    @Spy
//    @InjectMocks
    private JukeService jukeService;
    
    @Test
    public void testGetJukeboxes() throws IOException{
        
        String settingId= "b43f247a-8478-4f24-8e28-792fcfe539f5";
        String model = "fusion";
        
        Settling[] settling = new Settling[2];
        settling[0] = new Settling("b43f247a-8478-4f24-8e28-792fcfe539f5", new String[]{"camera","amplifier"});
        settling[1] = new Settling("db886f37-16e3-4a55-80e4-cfffc9e4e464", new String[]{"touchscreen","money_pcb","led_panel","money_receiver"});
        Settings settings = new Settings();
        settings.setSettings(settling);
        
        when(jukeService.getSettings()).thenReturn(settings);
        
        
        
        Component[] component = new Component[2];
        component[0] = new Component("camera");
        component[1] = new Component("amplifier");
        
        Component[] component1 = new Component[2];
        component1[0] = new Component("speaker");
        component1[1] = new Component("touchscreen");
        
        Jukes[] juke = new Jukes[2];
        juke[0] = new Jukes("5ca94a8ab592da6c6f2d562e", "fusion", component);
        juke[1] = new Jukes("5ca94a8ab592da6c6f2d562e", "fusion", component1);
        
        when(jukeService.getjukes()).thenReturn(juke);
        
         List<Jukes> jukeboxes = jukeService.getJukeboxes(settingId, model, null, null);
         
        assertNotNull(jukeboxes);
        assertEquals(1, jukeboxes.size());
        assertEquals(juke[0], jukeboxes.get(0));
    }
    
    private Settings getSettings(){
        Settling[] settling = new Settling[2];
        settling[0] = new Settling("b43f247a-8478-4f24-8e28-792fcfe539f5", new String[]{"camera","amplifier"});
        
        Settings settings = new Settings();
        settings.setSettings(settling);
        return settings;
    }
    
    private Jukes[] getJukes(){
        Component[] component = new Component[2];
        component[0] = new Component("camera");
        component[1] = new Component("amplifier");
        
        Component[] component1 = new Component[2];
        component1[0] = new Component("speaker");
        component1[1] = new Component("touchscreen");
        
        Jukes[] juke = new Jukes[]{};
        juke[0] = new Jukes("5ca94a8ab592da6c6f2d562e", "fusion", component);
        juke[1] = new Jukes("5ca94a8ab592da6c6f2d562e", "fusion", component1);
        return juke;
    }
}

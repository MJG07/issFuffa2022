package it.unibo.SonarObservable.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.json.JSONObject;
import org.json.JSONTokener;

import it.unibo.radarSystem22.domain.utils.ColorsOut;

public class DomainSystemConfig {
	
	public static boolean simulation     = true;
	public static boolean tracing        = false;	
	public static boolean testing        = false;	
	
	public static void setTheConfiguration(  ) throws FileNotFoundException {
		setTheConfiguration("../DomainSystemConfig.json");
	}
	
	public static void setTheConfiguration( String resourceName ) throws FileNotFoundException {
		FileInputStream fis = null;
		
		if(  fis == null ) {
			 fis = new FileInputStream(new File(resourceName));
			 
		}
		Reader reader = new InputStreamReader(fis);
        JSONTokener tokener = new JSONTokener(reader);
        JSONObject object   = new JSONObject(tokener);
        
        simulation = object.getBoolean("simulation");
        tracing    = object.getBoolean("tracing");
        testing    = object.getBoolean("testing");
	
	}
	
	
}

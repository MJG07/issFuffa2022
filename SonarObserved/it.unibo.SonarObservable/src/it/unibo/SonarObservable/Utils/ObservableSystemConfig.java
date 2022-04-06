package it.unibo.SonarObservable.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.json.JSONObject;
import org.json.JSONTokener;

import it.unibo.comm2022.ProtocolType;
import it.unibo.radarSystem22.domain.utils.ColorsOut;

public class ObservableSystemConfig {

	public static boolean tracing         = false;	
	public static boolean testing         = false;			    	
	public static boolean  RadarGuiRemote = false;	
 
	public static ProtocolType protcolType= ProtocolType.tcp;		
	public static int sonarObPort		  = 8030;
	public static String raspAddr		  = "localhost";
	
	public static void setTheConfiguration(  ) {
		setTheConfiguration("../ObservableSystemConfig.json");
	}
	
	public static void setTheConfiguration( String resourceName ) {
		FileInputStream fis = null;
		
		try {
			if(  fis == null ) {
				 fis = new FileInputStream(new File(resourceName));
				 
			}
			Reader reader       = new InputStreamReader(fis);
			JSONTokener tokener = new JSONTokener(reader);      
	        JSONObject object   = new JSONObject(tokener);
	        
	        tracing          = object.getBoolean("tracing");
	        testing          = object.getBoolean("testing");
	        RadarGuiRemote   = object.getBoolean("RadarGuiRemote");
	        
	        switch( object.getString("protocolType") ) {
	        case "tcp"  : protcolType = ProtocolType.tcp; break;
	        case "coap" : protcolType = ProtocolType.coap; break;
	        case "mqtt" : protcolType = ProtocolType.mqtt; break;
	        case "udp"  : protcolType = ProtocolType.udp; break;
	        }
	        
	        sonarObPort = object.getInt("sonarObPort");
	        raspAddr    = object.getString("raspAddr");
			
		}catch(FileNotFoundException e) {
 			ColorsOut.outerr("Errore nella configurazione");
		}
	
	}
}

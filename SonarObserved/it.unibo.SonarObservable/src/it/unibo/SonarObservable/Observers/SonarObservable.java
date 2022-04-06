package it.unibo.SonarObservable.Observers;

import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SonarObservable {
	
	private List<IObserver> osservatori;
	private ISonar sonar;
	private IDistance d;
	private int limit=5;
	private int o;
	
	
	public SonarObservable(ISonar sonar) {
		this.sonar=sonar;
		osservatori = new ArrayList<IObserver>(); 
	}
	
	
	public static SonarObservable create (ISonar sonar) {
		return new SonarObservable(sonar);
	}
	
	
	public void addObserver(IObserver observer) {
		osservatori.add(observer);
	}
	
	
	public void start( ) {
		ColorsOut.outappl("Inizializzo SonarObseravle", ColorsOut.GREEN);
		sonar.activate();
		activate();
	}
	
	
	protected void activate() {
		   new Thread() {
		   public void run() { 
		    BasicUtils.aboutThreads("SonarObservable attivato");
		    try {
		       boolean sonarActive = sonar.isActive();
		     if(sonarActive) {
		      while( sonar.isActive() ) {
		       d = sonar.getDistance(); //potrebbe essere bloccante
		       System.out.println(d);
		        for(IObserver ob: osservatori) {
		        	if(ob.getCont()==0) {
		        		update(d,ob);
			    	}
		        	System.out.println("DISTANZA OB " + ob.getDistance());
		        	if(d.getVal() > ob.getDistance())
				         o = d.getVal() - ob.getDistance();
				    else 
				         o = ob.getDistance() - d.getVal();
				    if(o>=limit) {
				    	System.out.println("cambiamento di distanza per:"+ob.getName());
				    	update(d,ob);
				   }
		        }    
		       ColorsOut.outappl("Distanza attuale: " +d, ColorsOut.GREEN  );
		       BasicUtils.delay(DomainSystemConfig.sonarDelay);//Al ritmo della generazione ...
		       }
		     }
		    } catch (Exception e) {
		      ColorsOut.outerr("ERRORA"+e.getMessage());
		    }     
		   }
		  }.start();
		  
		 }
	
	public void update(IDistance d,IObserver ob){
		try {
			if(ob.getType()==0) {
	    		ob.Update(d);
	    	}else {
	    		ob.Update(d);
	    		Socket skt = new Socket(ob.getIP(), ob.getPort());
				PrintWriter output = new PrintWriter(skt.getOutputStream());
				String msg= "{ \"distance\" : D , \"angle\" : 90 }";
				msg=msg.replace("D" , String.valueOf(d) );
				output.print(msg);
				output.flush();
				output.close();
	    	}
		}catch(Exception e) {
		   ColorsOut.outerr("ERRORB"+e.getMessage());
		   System.out.println(e);
		}   
	}
		
	
}

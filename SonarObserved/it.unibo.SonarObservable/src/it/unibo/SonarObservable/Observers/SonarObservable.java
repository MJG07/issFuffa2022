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

public class SonarObservable{
	
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
		sonar.activate();
		System.out.println("Benvenuto nel sistema " + observer.getName());
		activate(observer);
		
	}
	
	protected void activate(IObserver observer) {
		   new Thread() {
		   public void run() {   
		    BasicUtils.aboutThreads("SonarObservable attivato per "+ observer.getName());
		    try {
		     boolean sonarActive = sonar.isActive();
		     if(sonarActive) {
		      while( sonar.isActive() ) {
		       d = sonar.getDistance(); //potrebbe essere bloccante
		       System.out.println(d);
		        	if(observer.getCont()==0) {
		        		System.out.println("prima volta per " + observer.getName());
		        		update(d,observer);
			    	}
		        	System.out.println("LA DISTANZA DI " + observer.getName() + " E' DI " + observer.getDistance());
		        	if(d.getVal() > observer.getDistance())
				         o = d.getVal() - observer.getDistance();
				    else 
				         o = observer.getDistance() - d.getVal();
				    if(o>=limit) {
				    	System.out.println("cambiamento di distanza per:"+observer.getName());
				    	update(d,observer);
				    }
		       BasicUtils.delay(DomainSystemConfig.sonarDelay);//Al ritmo della generazione ...
		       }
		     }
		    } catch (Exception e) {
		      ColorsOut.outerr("ERRORA"+e.getMessage());
		      Thread.interrupted();
		    }     
		   }
		  }.start();
		  
		 }
	
	public void update(IDistance d,IObserver ob){
		try {
			if(ob.getType()==0) {
				System.out.println("local");
	    		ob.Update(d);
	    	}else {
	    		System.out.println("remot");
	    		ob.Update(d);
	    		Socket skt = new Socket(ob.getIP(), ob.getPort());
				PrintWriter output = new PrintWriter(skt.getOutputStream());
				String msg= "{ \"distance\" : D , \"angle\" : 90 }";
				msg=msg.replace("D" , String.valueOf(d));
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

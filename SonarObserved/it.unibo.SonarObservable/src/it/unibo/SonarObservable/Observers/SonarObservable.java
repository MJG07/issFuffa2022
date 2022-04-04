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

		        for(IObserver ob: osservatori) {
		        	if(ob.getType()==0) {
		        		if(ob.getCont()==0) {
		        			System.out.println("fisrt time for" + ob.getName());
			        		ob.Inizialize(d);
		        		}
			    	}else {
			    		if(ob.getCont()==0) {
			    			System.out.println("fisrt time for" + ob.getName());
			    			ob.Inizialize(d);
				    		Socket skt = new Socket(ob.getIP(), ob.getPort());
				    		
							PrintWriter output = new PrintWriter(skt.getOutputStream());
							String msg= "{ \"distance\" : D , \"angle\" : 90 }";
							msg=msg.replace("D" , String.valueOf(d) );
							output.print(msg);
							output.flush();
							output.close();
			    		}
			    	}
		        	System.out.println("DISTANZA OB " + ob.getDistance());
		        	if(d.getVal() > ob.getDistance().getVal())
				         o = d.getVal() - ob.getDistance().getVal();
				    else 
				         o = ob.getDistance().getVal() - d.getVal();
				    if(o>=ob.getLimit()) {
				    	if(ob.getType()==0) {
				    		ob.Update(d);
				        	ColorsOut.outappl("cambiamento sensibile per " + ob.getName(), ColorsOut.GREEN);
				    	}else {
				    		ColorsOut.outappl("cambiamento sensibile per " + ob.getName()+ "invio dei dati", ColorsOut.GREEN);
				    		ob.Update(d);
				    		Socket skt = new Socket(ob.getIP(), ob.getPort());
				    		if(skt.isConnected()) {
				    			System.out.println("ok");
				    		}else {
				    			System.out.println("no");
				    		}
							PrintWriter output = new PrintWriter(skt.getOutputStream());
							String msg= "{ \"distance\" : D , \"angle\" : 90 }";
							msg=msg.replace("D" , String.valueOf(d) );
							output.print(msg);
							output.flush();
							output.close();
				    	}
			        	
				   }
		        }    
		       ColorsOut.outappl("Distanza attuale: " +d, ColorsOut.GREEN  );
		       BasicUtils.delay(DomainSystemConfig.sonarDelay);//Al ritmo della generazione ...
		       }
		     }
		    } catch (Exception e) {
		      ColorsOut.outerr("ERROR"+e.getMessage());
		    }     
		   }
		  }.start();
		  
		 }
	
}

package it.unibo.SonarObservable.main;

import java.io.FileNotFoundException;

import it.unibo.SonarObservable.Handler.SonarObservableApplHandler;
import it.unibo.SonarObservable.Observers.IObserver;
import it.unibo.SonarObservable.Observers.ObserverLocale;
import it.unibo.SonarObservable.Observers.SonarObservable;

import it.unibo.SonarObservable.Utils.ObservableSystemConfig;
import it.unibo.comm2022.ProtocolType;
import it.unibo.comm2022.enablers.EnablerAsServer;
import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;


public class SonarObservableMainOnRasp {
	
	private ISonar sonar;
	private SonarObservable sonarOb;
	private IObserver ob;
	private EnablerAsServer sonarObEnabler; 
	
	public void doJob(String domainConfig, String observableConfig) throws FileNotFoundException {
		setup(domainConfig,   observableConfig);
		configure();
		sonarObEnabler.start();
		sonarOb.start();
	}
	
	public void setup( String domainConfig, String observableConfig ) throws FileNotFoundException  {
	    BasicUtils.aboutThreads("Before setup ");
		if( domainConfig != null ) {
			DomainSystemConfig.setTheConfiguration(domainConfig);
		}
		if( observableConfig != null ) {
			ObservableSystemConfig.setTheConfiguration(observableConfig);
		}
		
		if( domainConfig == null && observableConfig == null) {
			DomainSystemConfig.simulation  = false;
	    	DomainSystemConfig.testing     = false;			
	    	DomainSystemConfig.tracing     = false;				
					
	    	ObservableSystemConfig.RadarGuiRemote    = true;		
	    	ObservableSystemConfig.protcolType       = ProtocolType.tcp;		
	    	ObservableSystemConfig.sonarObPort       = 8030;	
		}
	}
	
	protected void configure() {		
 	    sonar = DeviceFactory.createSonar();
 	    ob = new ObserverLocale("ObsFuffa",6);
	    sonarOb = SonarObservable.create(sonar);
	    IApplMsgHandler sonarObh = new SonarObservableApplHandler("sonarObh", sonarOb);
	    sonarObEnabler=new EnablerAsServer("sonarObEnabler",ObservableSystemConfig.sonarObPort,ObservableSystemConfig.protcolType,sonarObh);
	    ob.registrami(sonarOb);
	}
	
	public ISonar getSonar() { return sonar; }
	
	public static void main(String[] args) throws FileNotFoundException {
		new SonarObservableMainOnRasp().doJob("DomainSystemConfig.json", "ObservableSystemConfig.json");
	}
	
}

package it.unibo.SonarObservable.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

import it.unibo.SonarObservable.Handler.RadarApplHandler;
import it.unibo.SonarObservable.Utils.IApplication;
import it.unibo.SonarObservable.Utils.ObservableSystemConfig;
import it.unibo.comm2022.ProtocolType;
import it.unibo.comm2022.enablers.EnablerAsServer;
import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.tcp.TcpServer;
import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.utils.BasicUtils;

/*
 * Attiva il Controller (vedi sprint1) e il RadarDisplay (vedi domain)
 * e due proxy al Led e al Sonar.
 * 
 */
public class ObservableGuyOnPc implements IApplication{
	private IRadarDisplay radar;
	private EnablerAsServer radarEnabler ;
	
	@Override
	public void doJob(String domainConfig, String systemConfig ) {
		setup( );
		configure();
		connect();
		execute();
	}
	
	private void connect() {
		try {
			Socket skt = new Socket(ObservableSystemConfig.raspAddr, ObservableSystemConfig.sonarObPort );
			PrintWriter output = new PrintWriter(skt.getOutputStream());                                                   
			String name="radarguy";
			InetAddress localhost = InetAddress.getLocalHost();
			int limit =5;
			int port=ObservableSystemConfig.sonarObPort;
			String mess = "{ \"name\" : N , \"limit\" : Z ,\"ipadress\" : K,\"port\" : P }" ;
			mess=mess.replace("N" , name );
			mess=mess.replace("Z" , String.valueOf(limit) );
			mess=mess.replace("K" , localhost.getHostAddress()).trim();
			mess=mess.replace("P",  String.valueOf(port));
			
			output.print(mess);
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setup(  )  {	
		ObservableSystemConfig.sonarObPort      	= 8030;		
		ObservableSystemConfig.protcolType 		= ProtocolType.tcp;
		ObservableSystemConfig.raspAddr  	 	= "192.168.205.189";
	}
	
	public void configure(  )  {
		ProtocolType protocol = ObservableSystemConfig.protcolType;
 		int port = ObservableSystemConfig.sonarObPort;		
   		radar  	 = DeviceFactory.createRadarGui();
  	    IApplMsgHandler radarh = new RadarApplHandler("radarh", radar);
  	    radarEnabler = new EnablerAsServer("radarSrv", port,
	              protocol, radarh );
	}
	public void execute() {
		radarEnabler.start();
	}
	public void terminate() {
 		BasicUtils.aboutThreads("Before deactivation | ");
 		System.out.println("ciao ciao");
		System.exit(0);
	}	
	
	@Override
	public String getName() {
		return this.getClass().getName() ; //"RadarSystemSprint2OnPcMain";
	}
	
	

	public static void main( String[] args) throws Exception {
		BasicUtils.aboutThreads("At INIT with NO CONFIG files| ");
		new ObservableGuyOnPc().doJob( null,null );
  	}	
}

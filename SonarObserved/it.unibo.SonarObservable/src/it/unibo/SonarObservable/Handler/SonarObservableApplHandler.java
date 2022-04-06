package it.unibo.SonarObservable.Handler;

import org.json.JSONObject;

import it.unibo.SonarObservable.Observers.ObserverRemoto;
import it.unibo.SonarObservable.Observers.SonarObservable;
import it.unibo.comm2022.ApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.radarSystem22.domain.utils.ColorsOut;

public class SonarObservableApplHandler extends ApplMsgHandler {
private SonarObservable sob;
private ObserverRemoto obR;
	
	
	public SonarObservableApplHandler(String name,SonarObservable ob) {
		super(name);
		this.sob=ob;
	}
	
	//{"name": "nome", "ipadress": "ip", "port" : "port" }
	public void elaborate(String message, Interaction2021 conn) {
		ColorsOut.out(name + " | elaborate " + message + " conn=" + conn);
		JSONObject jsonObj   = new JSONObject(message.trim());
		String name = ""+jsonObj.getString("name");
		String ip = ""+jsonObj.getString("ipadress");
		int port  = jsonObj.getInt("port");
		obR=new ObserverRemoto(name,ip,port);
		sob.addObserver(obR);
	}

}

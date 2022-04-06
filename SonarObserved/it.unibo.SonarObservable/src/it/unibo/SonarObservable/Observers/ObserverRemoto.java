package it.unibo.SonarObservable.Observers;

import it.unibo.SonarObservable.Interfaces.IMisura;
import it.unibo.SonarObservable.Utils.Misura;
import it.unibo.radarSystem22.domain.interfaces.IDistance;

public class ObserverRemoto implements IObserver{
	
	public String name;
	public String IP;
	public IMisura misura=new Misura();
	public int port;
	public int cont = 0;
	public int type=1;
	
	public ObserverRemoto(String name, String IP,int port) {
		this.name=name;
		this.IP=IP;
		this.port=port;
	}

	public String getName() {
		return name;
	} 
	
	public String getIP() {
		return IP;
	}
	
	public int getPort() {
		return port;
	}	
	
	@Override
	public int getDistance() {
		return misura.getVal();
	}
	
	public void registrami(SonarObservable sonarOb) {	
		sonarOb.addObserver(this);
	}
	
	public void Update(IDistance d) {
		misura.setValue(d);
		cont++;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public int getCont() {
		return cont;
	}

	
	

}

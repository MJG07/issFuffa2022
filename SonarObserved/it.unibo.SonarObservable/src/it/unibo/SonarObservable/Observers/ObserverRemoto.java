package it.unibo.SonarObservable.Observers;

import it.unibo.radarSystem22.domain.interfaces.IDistance;

public class ObserverRemoto implements IObserver{
	
	public String name;
	public String IP;
	public int limit;
	public IDistance misura;
	public int port;
	public int cont = 0;
	public int type=1;
	
	public ObserverRemoto(String name, String IP, int limit,int port) {
		this.limit=limit;
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
	
	public IDistance getDistance() {
		return misura;
	}


	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
	public void registrami(SonarObservable sonarOb) {
		
		sonarOb.addObserver(this);
	}
	
	public void Update(IDistance d) {
		misura=d;
	}
	
	public void Inizialize (IDistance d) {
		if(cont==0) {
			misura = d;
			cont=1;
		}
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

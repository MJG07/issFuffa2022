package it.unibo.SonarObservable.Observers;

import it.unibo.radarSystem22.domain.interfaces.IDistance;

public class ObserverLocale implements IObserver{
	
	public String name;
	public int limit;
	public IDistance misura;
	public int cont = 0;
	public int type=0;
	
	public ObserverLocale(String name, int limit) {
		this.limit=limit;
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	public IDistance getDistance() {
		return misura;
	}

	public void setName(String name) {
		this.name = name;
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
			cont++;
		}
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public String getIP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCont() {
		return cont;
	}

}

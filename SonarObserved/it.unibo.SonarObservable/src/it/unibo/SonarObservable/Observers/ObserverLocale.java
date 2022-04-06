package it.unibo.SonarObservable.Observers;

import it.unibo.SonarObservable.Interfaces.IMisura;
import it.unibo.SonarObservable.Utils.Misura;
import it.unibo.radarSystem22.domain.interfaces.IDistance;

public class ObserverLocale implements IObserver{
	
	public String name;
	public int limit;
	public IMisura misura= new Misura();
	public int cont = 0;
	public int type=0;
	
	public ObserverLocale(String name, int limit) {
		this.limit=limit;
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	public int getDistance() {
		return misura.getVal();
	}

	public void setName(String name) {
		this.name = name;
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

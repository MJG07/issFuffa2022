package it.unibo.SonarObservable.Observers;

import it.unibo.radarSystem22.domain.interfaces.IDistance;

public interface IObserver {
	
	public String getIP();
	
	public int getPort();

	public String getName();
	
	public IDistance getDistance();
	
	public int getCont();

	public int getLimit();
	
	public void setLimit(int limit);
	
	public void registrami(SonarObservable sonarOb);
	
	public void Update(IDistance d);
	
	public void Inizialize (IDistance d);
	
	public int getType();

}

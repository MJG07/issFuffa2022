package it.unibo.SonarObservable.Observers;

import it.unibo.radarSystem22.domain.interfaces.IDistance;

public interface IObserver{
	
	public String getIP();
	
	public int getPort();

	public String getName();
	
	public int getDistance();
	
	public int getCont();
		
	public void registrami(SonarObservable sonarOb);
	
	public int getType();
	
	public void Update(IDistance d);

}

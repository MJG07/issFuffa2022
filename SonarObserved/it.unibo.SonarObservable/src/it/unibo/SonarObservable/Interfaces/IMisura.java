package it.unibo.SonarObservable.Interfaces;

import it.unibo.radarSystem22.domain.interfaces.IDistance;

public interface IMisura extends IDistance {
	public void setValue(IDistance d);
	public IDistance getDistance();
}

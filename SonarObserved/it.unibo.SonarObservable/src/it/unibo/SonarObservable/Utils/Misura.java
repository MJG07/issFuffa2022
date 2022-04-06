package it.unibo.SonarObservable.Utils;

import it.unibo.SonarObservable.Interfaces.IMisura;
import it.unibo.radarSystem22.domain.interfaces.IDistance;

public class Misura implements IMisura{
private IDistance dista;

	@Override
	public int getVal() {
		return dista.getVal();
	}

	@Override
	public void setValue(IDistance d) {
		dista=d;
		
	}

	@Override
	public IDistance getDistance() {
		return dista;
	}

}

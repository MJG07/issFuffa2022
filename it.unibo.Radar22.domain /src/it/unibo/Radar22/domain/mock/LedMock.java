package it.unibo.Radar22.domain.mock;

import it.unibo.Radar22.domain.interfaces.ILed;
import it.unibo.Radar22.domain.interfaces.ISonar;

public class LedMock implements ILed{
	
	private boolean state=false;
	
	public LedMock() {}
	
	@Override
	public void turnOn() {
		state=true;
		
	}

	@Override
	public void turnOff() {
		state=false;
		
	}

	@Override
	public boolean getState() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}

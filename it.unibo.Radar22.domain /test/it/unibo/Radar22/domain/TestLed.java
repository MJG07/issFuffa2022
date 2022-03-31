package it.unibo.Radar22.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.unibo.Radar22.domain.interfaces.ILed;
import it.unibo.Radar22.domain.mock.LedMock;

public class TestLed {
	
	
	@Test 
	 public void testLedMockOff() { 
	  System.out.print("test Led Mock Off"); 
	  ILed led = new LedMock(); 
	  assertTrue(!led.getState()); 
	 } 
	  
	 @Test  
	 public void TestLedMockOn() { 
	  System.out.print("test Led Mock On"); 
	  ILed led = new LedMock(); 
	  led.turnOn(); 
	  assertTrue(led.getState()); 
	 }	
}



import lejos.nxt.Button;
import lejos.robotics.Color;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;


public class Part1ii {
	private final static DifferentialPilot pilot = new DifferentialPilot(1.13, 5.20, Motor.A, Motor.B, false);
	private static int lightThreshold = 43;
	private static int steerValue = 10000;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Button.waitForAnyPress();
			runLightSensor();
			
	}

	private static void runLightSensor() {
		// TODO Auto-generated method stub
		LightSensor m_lightSensorR = new LightSensor(SensorPort.S1, true);
		LightSensor m_lightSensorL = new LightSensor(SensorPort.S2, true);
		m_lightSensorR.setFloodlight(Color.RED);
		m_lightSensorL.setFloodlight(Color.RED);
		//m_lightSensorR.setFloodlight(true);
		//m_lightSensorL.setFloodlight(true);
		//pilot.setTravelSpeed(((double) distance / (double) max_dist)*max_speed);
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/4);
		
		while(true){
			System.out.println(m_lightSensorR.getLightValue());
			pilot.forward();
			Thread.yield();
			makeDecision(m_lightSensorL.getLightValue(), m_lightSensorR.getLightValue());
		}
		//over brown sensor = 47
		//on black sensor = 45
	}
	
	private static void makeDecision(int lightValueL, int lightValueR) {
		// TODO Auto-generated method stub
		if(lightValueL < lightThreshold)
		{
			//turn right
			
			//Double turnRate = calcTurnRate(lightValueL);
			pilot.steer(-steerValue);
			Thread.yield();
		}
		else if(lightValueR < lightThreshold)
		{
			// turn right
			
			//Double turnRate = calcTurnRate(lightValueR);
			pilot.steer(steerValue);
			Thread.yield();
		}
		
	}


}

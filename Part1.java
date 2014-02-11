

import lejos.nxt.Button;
import lejos.robotics.Color;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;


public class Part1 {
	private final static int  max_dist = 900;
	private final static int target_dist = 200;
	private final static int  min_dist = 1;
	private final static DifferentialPilot pilot = new DifferentialPilot(1.13, 5.20, Motor.A, Motor.B, false);
	private final static double max_speed = pilot.getMaxTravelSpeed();
	private boolean m_suppressed;
	private static int lightThreshold = 43;
	private static int steerValue = 10000;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Button.waitForAnyPress();
			runDistance();
			//runLightSensor();
			
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
		if(lightValueL < lightThreshold && lightValueR < lightThreshold)
		{
			//pilot.steer(20000);
			pilot.forward();
			Delay.msDelay(500);
			pilot.rotate(90);
		}
		else if(lightValueL < lightThreshold)
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

	public void goForward()
	{
		pilot.forward();
		while(!m_suppressed)
		{
			Thread.yield();
		}
		pilot.stop();
		m_suppressed = false;
	}

	private static void runDistance() {
		// TODO Auto-generated method stub
		
		
		OpticalDistanceSensor m_opticalDistanceSensor = new OpticalDistanceSensor(SensorPort.S4);
		int distance = 0;
		//System.out.println(pilot.getMaxTravelSpeed());
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed());
		while(true)
		{
				distance = m_opticalDistanceSensor.getDistance();
				//System.out.println(distance);
				setSpeed(distance);
				if(distance > target_dist){
					pilot.forward();
					
				} else if(((double) distance < (target_dist * 1.1) && distance > target_dist) || ((double) distance > (target_dist * 0.9) && distance < target_dist)){
					pilot.stop();
				}
				else {
					pilot.backward();
				}
			
				
				//Delay.msDelay(10);
		}
	}

	private static void setSpeed(int distance) {
		// TODO Auto-generated method stub
		/*if (distance > target_dist){
			pilot.setTravelSpeed(((double) distance / (double) max_dist)*max_speed);
		} else {
			pilot.setTravelSpeed(((double) distance * (double) max_dist)*max_speed);
		}*/
		double error = (Math.abs((double)target_dist - (double)distance)/(double)max_dist);
		System.out.println(error * max_speed);
		pilot.setTravelSpeed(error * max_speed);
		
		//System.out.println((distance / max_dist)*pilot.getMaxTravelSpeed());
	}
	
	

}

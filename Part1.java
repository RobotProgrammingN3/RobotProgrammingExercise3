

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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Button.waitForAnyPress();
			runDistance();
			
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

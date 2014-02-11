import lejos.nxt.Button;
import lejos.robotics.Color;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class Part2 {

	private final static DifferentialPilot pilot = new DifferentialPilot(1.13, 5.20, Motor.A, Motor.B, false);
	private final static double max_speed = pilot.getMaxTravelSpeed();
	private boolean m_suppressed;
	private static int lightThreshold = 43;
	private static int count = 0;
	private static int[] decisionList = new int[10];
	private static boolean notBusy = true;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 0 = forward
		// 1 = left
		// 2 = right
		// 3 = turn around
		decisionList[0] = 1;
		decisionList[1] = 0;
		decisionList[2] = 1;
		decisionList[3] = 0;
		decisionList[4] = 2;
		decisionList[5] = 0;
		decisionList[6] = 1;
		decisionList[7] = 1;
		decisionList[8] = 0;
		decisionList[9] = 3;
		             
		
			Button.waitForAnyPress();
			//runDistance();
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
			if(notBusy){
				makeDecision(m_lightSensorL.getLightValue(), m_lightSensorR.getLightValue());
			}
		}
		//over brown sensor = 47
		//on black sensor = 45
	}
	
	private static void makeDecision(int lightValueL, int lightValueR) {
		// TODO Auto-generated method stub
		if(notBusy){
			if(lightValueL < lightThreshold && lightValueR < lightThreshold)
			{
				//pilot.steer(20000);
				atJunction();
				//Delay.msDelay(1000);
			}
			else if(lightValueL < lightThreshold)
			{
				//turn right
				
				//Double turnRate = calcTurnRate(lightValueL);
				pilot.steer(-8000);
				Thread.yield();
			}
			else if(lightValueR < lightThreshold)
			{
				// turn right
				
				//Double turnRate = calcTurnRate(lightValueR);
				pilot.steer(8000);
				Thread.yield();
			} 
			
		}
		
	}
	
	private static void atJunction()
	{
		
		/*switch(decisionList[count])
		{
		case 1: goLeft();
		case 2: goRight();
		case 3: turnAround();
		}*/
		
		if(decisionList[count] == 1)
		{
			goLeft();
		} else if(decisionList[count] == 2)
		{
			goRight();
		} else if(decisionList[count] == 3)
		{
			turnAround();
		}
		count++;
		Delay.msDelay(1000);
		
	}


	private static void turnAround() {
		// TODO Auto-generated method stub
		notBusy = false;
		pilot.stop();
		pilot.rotate(200);
		Delay.msDelay(1000);
		pilot.stop();
		notBusy = true;
	}

	private static void goRight() {
		// TODO Auto-generated method stub
		notBusy = false;
		pilot.forward();
		Delay.msDelay(1500);
		pilot.stop();
		pilot.rotate(110);
		//Delay.msDelay(500);
		pilot.stop();
		notBusy = true;
	}

	private static void goLeft() {
		// TODO Auto-generated method stub
		notBusy = false;
		pilot.forward();
		Delay.msDelay(1500);
		pilot.stop();
		pilot.rotate(-110);
		//Delay.msDelay(500);
		pilot.stop();
		notBusy = true;
	}

	public static void goForward()
	{
		pilot.forward();
		Thread.yield();
		pilot.stop();
	}


}

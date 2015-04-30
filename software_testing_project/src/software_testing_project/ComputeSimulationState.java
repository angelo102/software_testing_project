package software_testing_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ComputeSimulationState {
	private static int timeUntilLanding;
	private static int speed;
	private static int altitude;
	private static String gearPosition;
	private static String selectedGearPosition;
	
	public static boolean gearOverrideWarningStatus;
	public static boolean airBrakeWarningStatus;
	public static boolean gearNotDownAlarmStatus;
	public static boolean gearAirSpeedAlarmStatus;
	
	/*
	public int getTimeUntilLanding() {
		return timeUntilLanding;
	}
	public void setTimeUntilLanding(int timeUntilLanding) {
		this.timeUntilLanding = timeUntilLanding;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getAltitude() {
		return altitude;
	}
	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}
	public String getGearPosition() {
		return gearPosition;
	}
	public void setGearPosition(String gearPosition) {
		this.gearPosition = gearPosition;
	}
	public String getSelectedGearPosition() {
		return selectedGearPosition;
	}
	public void setSelectedGearPosition(String selectedGearPosition) {
		this.selectedGearPosition = selectedGearPosition;
	}
	public boolean isGearOverrideWarningStatus() {
		return gearOverrideWarningStatus;
	}
	public void setGearOverrideWarningStatus(boolean gearOverrideWarningStatus) {
		this.gearOverrideWarningStatus = gearOverrideWarningStatus;
	}
	public boolean isAirBrakeWarningStatus() {
		return airBrakeWarningStatus;
	}
	public void setAirBrakeWarningStatus(boolean airBrakeWarningStatus) {
		this.airBrakeWarningStatus = airBrakeWarningStatus;
	}
	public boolean isGearNotDownAlarmStatus() {
		return gearNotDownAlarmStatus;
	}
	public void setGearNotDownAlarmStatus(boolean gearNotDownAlarmStatus) {
		this.gearNotDownAlarmStatus = gearNotDownAlarmStatus;
	}
	public boolean isGearAirSpeedAlarmStatus() {
		return gearAirSpeedAlarmStatus;
	}
	public void setGearAirSpeedAlarmStatus(boolean gearAirSpeedAlarmStatus) {
		this.gearAirSpeedAlarmStatus = gearAirSpeedAlarmStatus;
	}
	*/
	
	public static void Process(int inputTimeUntilLanding, int inputSpeed, int inputAltitude, String inputGearPosition, String inputSelectedGearPosition,
			String inputThrottleCmd, String inputElevonCmd){
		
		//set input parameters to class variables
		timeUntilLanding = inputTimeUntilLanding;
		speed = inputSpeed;
		altitude = inputAltitude;
		gearPosition = inputGearPosition;
		selectedGearPosition = inputSelectedGearPosition;
		
		//method variables
		int speedIncrement;
		int altitudeIncrement;
		
		if (inputThrottleCmd == "+")
			speedIncrement=10;
			else {
			  if (inputThrottleCmd == "-")
				  speedIncrement=-10;
			  else
				  speedIncrement=0;
			}
		
			if (inputElevonCmd == "+")
				altitudeIncrement=20;
			else {
			  if (inputElevonCmd == "-")
				  altitudeIncrement=-20;
			  else
				  altitudeIncrement=0;
			}
			
			if (!gearOverrideWarningStatus)
			    gearPosition = selectedGearPosition;
			
			speed += speedIncrement;
			altitude += altitudeIncrement;
			
			airBrakeWarningStatus = (speed >= 250) && (timeUntilLanding < 60);
			gearOverrideWarningStatus = (gearPosition == "down") && (speed > 400);
			gearNotDownAlarmStatus = (gearPosition == "up") && ((timeUntilLanding <= 120) || altitude < 1000);
			gearAirSpeedAlarmStatus = (gearPosition == "down") && (speed > 300);
		
	}
	
	/*
	// For testing Purposes
	
	*/
	ArrayList<TestInputOutput> testArray;
	
	/*
	 * Reads csv file to obtain inputs and expected outputs of 37 test cases
	 */
	public void readFile(){
		//Initialize Arraylist
		testArray = new ArrayList<TestInputOutput>();
		
		 //Input file which needs to be parsed
        String fileToParse = "testcase.csv";
        BufferedReader fileReader = null;
         
        //Delimiter used in CSV file
        final String DELIMITER = ",";
        try
        {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));
             
            //Read the file line by line
            while ((line = fileReader.readLine()) != null)
            {
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                
                TestInputOutput t = new TestInputOutput();
                
                //inputs
                t.speed = Integer.parseInt(tokens[3]);
                t.gearPosition = tokens[4] == "Y" ? "up" : "down"; 
                t.altitude = Integer.parseInt(tokens[5]);
                t.timeUntilLanding = Integer.parseInt(tokens[6]);
                
                //expected outputs
                t.gearNotDownAlarmStatus = tokens[7].equals("Y") ? true : false;
                t.gearAirSpeedAlarmStatus = tokens[8].equals("Y") ? true : false;
                t.airBrakeWarningStatus = tokens[9].equals("Y") ? true : false;
                t.gearOverrideWarningStatus = tokens[10].equals("Y") ? true : false;
                
                //Add to the arraylist
                testArray.add(t);
      
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
	}
	
	/*
	 * Asserts class method outputs with the expected outputs
	 */
	public void myAssert(boolean output, boolean expectedOutput){
		if(output == expectedOutput){
			System.out.println("Pass");
		}
		else
			System.out.println("Failed");
	}
	
	/*
	 * Computes outputs and the assert each output
	 */
	public void testProcess(TestInputOutput t) {
		
		ComputeSimulationState.Process(t.timeUntilLanding, t.speed, t.altitude, t.gearPosition, t.gearPosition, "Nothing", "Nothing");
		
		myAssert(ComputeSimulationState.gearNotDownAlarmStatus, t.gearNotDownAlarmStatus);
		myAssert(ComputeSimulationState.gearAirSpeedAlarmStatus, t.gearAirSpeedAlarmStatus);
		myAssert(ComputeSimulationState.airBrakeWarningStatus, t.airBrakeWarningStatus);
		myAssert(ComputeSimulationState.gearOverrideWarningStatus, t.gearOverrideWarningStatus);
		
	}
	
	
	//For testing
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ComputeSimulationStateTest tester = new ComputeSimulationStateTest();
		tester.readFile();
		//Run loop 37 times, once for each test case
		for (int i = 0; i < 37; i++){
			System.out.println("Running Test Case: " + String.valueOf(i+1));
			TestInputOutput t = tester.testArray.get(i);
			
			tester.testProcess(t);
			
		}

	}

}

package software_testing_project;

public class ComputeSimulationState {
	private int timeUntilLanding;
	private int speed;
	private int altitude;
	private String gearPosition;
	private String selectedGearPosition;
	private boolean gearOverrideWarningStatus;
	private boolean airBrakeWarningStatus;
	private boolean gearNotDownAlarmStatus;
	private boolean gearAirSpeedAlarmStatus;
	
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
	
	public void Process(int inputTimeUntilLanding, int inputSpeed, int inputAltitude, String inputGearPosition, String inputSelectedGearPosition,
			String inputThrottleCmd, String inputElevonCmd){
		
		//set input parameters to class variables
		this.timeUntilLanding = inputTimeUntilLanding;
		this.speed = inputSpeed;
		this.altitude = inputAltitude;
		this.gearPosition = inputGearPosition;
		this.selectedGearPosition = inputSelectedGearPosition;
		
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
			
			if (!this.gearOverrideWarningStatus)
			    this.gearPosition = this.selectedGearPosition;
			
			this.speed += speedIncrement;
			this.altitude += altitudeIncrement;
			
			this.airBrakeWarningStatus = (this.speed >= 250) && (this.timeUntilLanding < 60);
			this.gearOverrideWarningStatus = (this.gearPosition == "down") && (this.speed > 400);
			this.gearNotDownAlarmStatus = (this.gearPosition == "up") && ((this.timeUntilLanding <= 120) || this.altitude < 1000);
			this.gearAirSpeedAlarmStatus = (this.gearPosition == "down") && (speed > 300);
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

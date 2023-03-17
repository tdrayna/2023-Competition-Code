/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

    import edu.wpi.first.networktables.NetworkTableInstance;
    import edu.wpi.first.wpilibj.motorcontrol.Spark;
    import edu.wpi.first.wpilibj2.command.SubsystemBase;
    import frc.robot.RobotContainer;

public class blinkin extends SubsystemBase {

	static Spark blinkin;

	public blinkin() {
		blinkin = new Spark(0);
	}
		
	/**
	 * if the robot is not in hatMode and in normal drive, the LED turns solid white (0.93)
	 */
	public static void lightsNormal() {
		blinkin.set(0.01);
	}
	
	/**
	 * if the robot detects the cube, the LED blinks gold (-0.07)
	 */
	public void lightsHasCube() {
		blinkin.set(-0.07); 
	}
}

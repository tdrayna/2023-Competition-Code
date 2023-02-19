package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);//xbox controller
    private final Joystick Thrustmaster = new Joystick(1);
    //private final Joystick driver2 = new Joystick(1);
    

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;
    
    
    

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton button8 = new JoystickButton(Thrustmaster,8);
    private final JoystickButton button7 = new JoystickButton(Thrustmaster,7);
    private final JoystickButton button2 = new JoystickButton(Thrustmaster, 2);

    private final JoystickButton button14 = new JoystickButton(Thrustmaster, 14);
    private final JoystickButton button15 = new JoystickButton(Thrustmaster, 15);
    private final JoystickButton button16 = new JoystickButton(Thrustmaster, 16);


    private final JoystickButton trigger = new JoystickButton(Thrustmaster, 1);
    private final JoystickButton backButton = new JoystickButton(driver, XboxController.Button.kBack.value);


    private final JoystickButton button9 = new JoystickButton(Thrustmaster,9);
    private final JoystickButton button6 = new JoystickButton(Thrustmaster,6);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    //Thrust Master Buttons
    //private final boolean trigger = driver2.getRawButton(1);
    /* Subsystems */
   // private final liftSub m_lift = new liftSub();
    private final armSub m_arm = new armSub();
    private final liftSub m_lift = new liftSub();
    private final Swerve s_Swerve = new Swerve();
    private final intakeSub m_Intake = new intakeSub();


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        
        /* Driver Buttons */
        
        button16.whileTrue(new InstantCommand(() -> m_arm.armUp()));
        button15.whileTrue(new InstantCommand(() -> m_arm.armMiddle()));
        button14.whileTrue(new InstantCommand(() -> m_arm.armDown()));
       // backButton.whileTrue(new InstantCommand(() -> m_arm.armMovement(double setpoint + .025)));
        button9.whileTrue(new InstantCommand(() -> m_lift.liftUp())).whileFalse(new InstantCommand(() -> m_lift.liftStop()));
        button6.whileTrue(new InstantCommand(() -> m_lift.liftDown())).whileFalse(new InstantCommand(() -> m_lift.liftStop()));
        trigger.whileTrue(new InstantCommand(() -> m_Intake.intakein())).whileFalse(new InstantCommand(() -> m_Intake.intakeStop()));
        button2.whileTrue(new InstantCommand(() -> m_Intake.intakeout())).whileFalse(new InstantCommand(() -> m_Intake.intakeStop()));

       
        
        


        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new exampleAuto(s_Swerve);
    }
}

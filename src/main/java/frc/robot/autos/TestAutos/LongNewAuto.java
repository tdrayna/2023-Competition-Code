// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos.TestAutos;

import java.sql.Time;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.waitTime;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.intakeSub;
import frc.robot.subsystems.liftSub;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Vision.ObjectToTarget;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LongNewAuto extends SequentialCommandGroup {
  private final Swerve m_Swerve;
  private final Vision m_Vision;
  private final intakeSub m_intake;
  private final liftSub m_lift;
  private final armSub m_arm;
  //private final ObjectToTarget m_ObjectToTarget;

  /** Creates a new DriveSequential. */
  public LongNewAuto(Swerve swerve, Vision vision, intakeSub intake, liftSub lift, armSub arm) {
    this.m_Swerve = swerve;
    this.m_intake = intake;
    this.m_lift = lift;
    this.m_Vision = vision;
    this.m_arm = arm;
   // this.m_ObjectToTarget = object;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      //Reset ODO and begin looking for floor cone
      
      new InstantCommand(() -> m_Swerve.zeroGyro()),
      new InstantCommand(() -> m_Swerve.zeroPosition(new Pose2d(new Translation2d(0, 0), new Rotation2d(0)), 0)),
      new InstantCommand(() -> m_Vision.setObject(ObjectToTarget.FLOOR_CONE)),
      //Drop Cone into low goal
      new InstantCommand(() -> m_intake.AutoIntakeIn()),
      new WaitCommand(1),
      new InstantCommand(() -> m_intake.AutoIntakeOff()),
      new DriveToAndAlign(m_Swerve, m_Vision, m_Swerve::getPose, new Pose2d(new Translation2d(-4.35,0), new Rotation2d(0)), ObjectToTarget.NONE, 0, 0, false),
      new DriveToAndAlign(m_Swerve, m_Vision, m_Swerve::getPose, new Pose2d(new Translation2d(-4.45,0), new Rotation2d(Math.PI)), ObjectToTarget.NONE, 0, 0, false),
      new InstantCommand(() -> m_Swerve.zeroGyro()),
      new InstantCommand(() -> m_lift.liftLittleUp()),
      new InstantCommand(() -> m_Swerve.drive(new Translation2d(0,0), 0, true,false)),
      new WaitCommand(50)


      //Move lift and arm into intake position
     


      






      

     // new DriveToAndAlign(m_Swerve, m_Vision, m_Swerve::getPose, new Pose2d(new Translation2d(0,0), new Rotation2d(0)), ObjectToTarget.APRIL_TAG, .8, 0)
      // SmartDashboard.putData("Autonomous Chooser", autoChooser);      //new DriveTo(m_Swerve, new Translation2d(-1, 0), 0)
     // new DriveToAndAlign(m_Swerve, m_Vision, ObjectToTarget.APRIL_TAG)
    );
  }
}

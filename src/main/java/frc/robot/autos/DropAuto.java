package frc.robot.autos;

import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
public class DropAuto extends SequentialCommandGroup {
    
    public DropAuto(Swerve s_Swerve, armSub m_arm, liftSub m_lift){
        TrajectoryConfig config = 
    new TrajectoryConfig(
            Constants.AutoConstants.kMaxSpeedMetersPerSecond,
            Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        .setKinematics(Constants.Swerve.swerveKinematics);
        Trajectory exampleTrajectory5 =
        TrajectoryGenerator.generateTrajectory(
            // Start at node, put code to place cone before this
            new Pose2d(0, 0, new Rotation2d(0)),
            // move over charging station, moving 190in putting us in front of cone by a bit
            List.of(),//new Translation2d(2.95, 0)
            // drive onto charging station, reaching the theoretical center
            new Pose2d(.3, 0, new Rotation2d(0)),
            config
            );
     Trajectory exampleTrajectory =
            TrajectoryGenerator.generateTrajectory(
                // Start at node, put code to place cone before this
                new Pose2d(s_Swerve.getPose().getX(), s_Swerve.getPose().getY(), new Rotation2d(s_Swerve.getYaw().getDegrees())),
                // move over charging station, moving 190in putting us in front of cone by a bit
                List.of(),//new Translation2d(2.95, 0)
                // drive onto charging station, reaching the theoretical center
                new Pose2d(-.3, 0, new Rotation2d(0)),
                config
                );
    var thetaController =
        new ProfiledPIDController(
            Constants.AutoConstants.kPThetaController, 0, 0, Constants.AutoConstants.kThetaControllerConstraints);
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    SwerveControllerCommand swerveControllerCommand =
            new SwerveControllerCommand(
                exampleTrajectory5,
                s_Swerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                s_Swerve::setModuleStates,
                s_Swerve);
         SwerveControllerCommand swerveControllerCommand2 =
                new SwerveControllerCommand(
                    exampleTrajectory,
                    s_Swerve::getPose,
                    Constants.Swerve.swerveKinematics,
                    new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                    new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                    thetaController,
                    s_Swerve::setModuleStates,
                    s_Swerve);
    

        addCommands(
       
        new littleUponLift(m_lift), 
       // new InstantCommand(() -> m_arm.armUp()), 
       // new InstantCommand(() -> m_lift.liftUp()),
       // new InstantCommand(() -> m_lift.liftDown()),
        new WaitCommand(3), 
        swerveControllerCommand,
        new InstantCommand(() -> m_lift.liftMiddle()), 
        new WaitCommand(5),
        swerveControllerCommand2, 
        new InstantCommand(() -> m_lift.liftUp()));
        
    }
}
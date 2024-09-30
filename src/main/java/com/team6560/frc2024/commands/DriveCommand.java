package com.team6560.frc2024.commands;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveCommand extends Command {

    private final Drivetrain drivetrain;
    private ManualControls controls;

    /* Manual driving command */
    public DriveCommand(Drivetrain drivetrainSubsystem, ManualControls controls) {
        this.drivetrain = drivetrainSubsystem;
        this.controls = controls;
        addRequirements(drivetrainSubsystem);
    }

    /* No initialization necessary */
    @Override
    public void initialize() {}

    /* Move drivetrain based on controller input and alliance side. */
    @Override
    public void execute() {

        var isRedAlliance = Constants.Global.IS_RED_ALLIANCE;

        // Handle gyro reset
        if (controls.driveResetYaw()) {
            var resetYawRotation = isRedAlliance ? 
                Constants.Odometry.DEFAULT_ROTATION_RED_ALLIANCE : 
                Constants.Odometry.DEFAULT_ROTATION_BLUE_ALLIANCE;
            drivetrain.setGyroscope(resetYawRotation);
        }

        // Handle odometry reset
        if (controls.driveResetGlobalPose()) {
            var resetPose = isRedAlliance ? 
                Constants.Odometry.DEFAULT_POSE_RED_ALLIANCE : 
                Constants.Odometry.DEFAULT_POSE_BLUE_ALLLIANCE;
            drivetrain.resetOdometry(resetPose);
        }

        // Move drivetrain
        var chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
            isRedAlliance ? -controls.driveX() : controls.driveX(),
            isRedAlliance ? -controls.driveY() : controls.driveY(),
            controls.driveRotationX(),
            drivetrain.getGyroscopeRotationNoApriltags()
        );

        drivetrain.drive(chassisSpeeds);
    }

    /* Stop drivetrain */
    @Override
    public void end(boolean interrupted) {
        drivetrain.stopModules();
    }
}

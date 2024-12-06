package com.team6560.lib.commands;

import com.team6560.lib.subsystems.drivetrain.GenericSwerve;
import com.team6560.lib.controls.GenericControlsIO;
import com.team6560.lib.util.AllianceUtil;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;

public class GenericDriveCommand extends Command {
    
    private final GenericSwerve drivetrain;
    private GenericControlsIO controls;

    /* Create a generic drive command using a drivetrain and a controls interface */
    public GenericDriveCommand(GenericSwerve drivetrain, GenericControlsIO controls) {
        this.drivetrain = drivetrain;
        this.controls = controls;
        addRequirements(drivetrain);
    }

    /* Move drivetrain based on controller input and alliance side. */
    @Override
    public void execute() {

        var isRedAlliance = AllianceUtil.IS_RED_ALLIANCE;

        // Handle gyro reset
        if (controls.driveResetYaw()) {
            drivetrain.setGyroscopeDefaultPose();
        }

        // Handle odometry reset
        if (controls.driveResetGlobalPose()) {
            drivetrain.resetOdometryDefaultPose();
        }

        // Move drivetrain

        var vX = controls.driveX();
        var vY = controls.driveY();
        var omega = controls.driveRotationX();

        var chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
            isRedAlliance ? -vX : vX,
            isRedAlliance ? -vY : vY,
            omega,
            drivetrain.getGyroscopeRotationNoApriltags()
        );

        drivetrain.drive(chassisSpeeds);
    }

    /* No initialization necessary */
    @Override
    public void initialize() {}

    /* Stop drivetrain */
    @Override
    public void end(boolean interrupted) {
        drivetrain.stopModules();
    }
}

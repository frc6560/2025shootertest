package com.team6560.frc2024.commands;

import com.pathplanner.lib.util.GeometryUtil;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
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
        var alliance = DriverStation.getAlliance().orElse(null);
        var isRedAlliance = (alliance == DriverStation.Alliance.Red);

        // Handle gyro reset
        if (controls.driveResetYaw()) {
            var resetYawRotation = isRedAlliance ? new Rotation2d(Math.PI) : new Rotation2d();
            drivetrain.zeroGyroscope(resetYawRotation);
        }

        // Handle odometry reset
        if (controls.driveResetGlobalPose()) {
            var resetPose = isRedAlliance ? GeometryUtil.flipFieldPose(new Pose2d()) : new Pose2d();
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

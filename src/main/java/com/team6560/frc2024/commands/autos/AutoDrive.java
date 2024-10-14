package com.team6560.frc2024.commands.autos;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.subsystems.Drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoDrive extends Command {
    
    final Drivetrain drivetrain;
    private double startTime;
    private boolean timerStarted;

    public AutoDrive(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        this.startTime = Timer.getFPGATimestamp();
        this.timerStarted = false;
        addRequirements(drivetrain);
    }

    public boolean getTimerStarted() {
        return this.timerStarted;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (!this.timerStarted) {
            this.startTime = Timer.getFPGATimestamp();
            this.timerStarted = true;
        } 
        var chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
            Constants.Global.IS_RED_ALLIANCE? -.5 : .5, // x
            0, // y
            0, // driveRotationX
            drivetrain.getGyroscopeRotationNoApriltags()
        );
        var nullSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
            0, 0, 0, drivetrain.getGyroscopeRotationNoApriltags()
        );
        if (Timer.getFPGATimestamp() < startTime + 8) {
            drivetrain.drive(chassisSpeeds);
        } else {
            drivetrain.drive(nullSpeeds);
        }
    }

    @Override
    public void end(boolean interrupted) {
        var nullSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
            0, 0, 0, drivetrain.getGyroscopeRotationNoApriltags()
        );
        drivetrain.drive(nullSpeeds);
    }

    @Override
    public boolean isFinished() {
      return Timer.getFPGATimestamp() > startTime + 8;
    }

}

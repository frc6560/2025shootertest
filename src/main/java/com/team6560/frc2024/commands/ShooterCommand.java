package com.team6560.frc2024.commands;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Hood;
import com.team6560.frc2024.subsystems.Shooter;
import com.team6560.frc2024.subsystems.Turret;

import edu.wpi.first.wpilibj2.command.Command;

public class ShooterCommand extends Command {
  final Shooter shooter;
  final ManualControls controls;
  final Hood hood;

  double HorizontalTargetPosition = 0.0;
  double VerticalTargetPosition = 40.0;

  public ShooterCommand(Shooter shooter, Hood hood, ManualControls controls) {
    this.shooter = shooter;
    this.controls = controls;
    this.hood = hood;
    addRequirements(shooter);
    addRequirements(hood);
  }

  @Override
  public void initialize() {
    shooter.stop();
    hood.ampStop();
  }

  @Override
  public void execute() {
    if (controls.getRunShooter()) {
      shooter.run();
    } 
    else if (controls.getAmp() && hood.getAmpReady()) {
      while (controls.getAmp()) {
        shooter.shooteramprun();
        hood.ampRun();
      }
    }
    else {
      shooter.stop();
      hood.ampStop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stop();
    hood.ampStop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}
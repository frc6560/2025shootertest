package com.team6560.frc2024.commands;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Hood;
import com.team6560.frc2024.subsystems.Shooter;
import com.team6560.frc2024.subsystems.Turret;

import edu.wpi.first.wpilibj2.command.Command;

public class ShooterCommand extends Command {
  final Shooter shooter;
  final Turret turret;
  final ManualControls controls;
  final Amp amp;
  final Hood hood;

  double HorizontalTargetPosition = 0.0;
  double VerticalTargetPosition = 40.0;

  public ShooterCommand(Shooter shooter, Amp amp, ManualControls controls) {
    this.shooter = shooter;
    this.controls = controls;
    this.amp = amp;
    this.hood = hood;
    addRequirements(shooter);
    addRequirements(amp);
    addRequirements(hood);
  }

  @Override
  public void initialize() {
    shooter.stop();
    amp.ampStop();
  }

  @Override
  public void execute() {
    if (controls.getRunShooter()) {
      shooter.run();
    } 
    else if (controls.getAmp() && getAmpReady()) {
      while (controls.getAmp()) {
        hood.setAmpAngle();
        turret.setTurretAngle(0.0);
        shooter.shooteramprun();
        amp.ampRun();
      }
    }
    else {
      shooter.stop();
      amp.ampStop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stop();
    amp.ampStop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}
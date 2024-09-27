package com.team6560.frc2024.commands;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.TurretHorizontal;
import edu.wpi.first.wpilibj2.command.Command;

public class TurretHorizontalCommand extends Command {
  final TurretHorizontal turretHorizontal;
  final ManualControls controls;

  public TurretHorizontalCommand(TurretHorizontal turretHorizontal, ManualControls controls) {
    this.turretHorizontal = turretHorizontal;
    this.controls = controls;
    addRequirements(turretHorizontal);
  }

  /* Make sure turretHorizontal is turned off at initialization */
  @Override
  public void initialize() {
    turretHorizontal.setFeedRate(0.0);
  }

  /* Checks turretHorizontal button press */
  @Override
  public void execute() {
    if (controls.getTurretClockwise()) {
      turretHorizontal.setFeedRate(Constants.TurretHorizontal.TURRET_HORIZONTAL_RATE);
    } else if (controls.getTurretCounterClockwise()) {
      turretHorizontal.setFeedRate(-Constants.TurretHorizontal.TURRET_HORIZONTAL_RATE);
    } else {
      turretHorizontal.setFeedRate(0.0);
    }
  }

  /* Stops feeder */
  @Override
  public void end(boolean interrupted) {}

  /* Command always executes */
  @Override
  public boolean isFinished() {
    return false;
  }
}

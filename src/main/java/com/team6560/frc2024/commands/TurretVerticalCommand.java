package com.team6560.frc2024.commands;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.TurretVertical;
import edu.wpi.first.wpilibj2.command.Command;

public class TurretVerticalCommand extends Command {
  final TurretVertical turretVertical;
  final ManualControls controls;

  public TurretVerticalCommand(TurretVertical turretVertical, ManualControls controls) {
    this.turretVertical = turretVertical;
    this.controls = controls;
    addRequirements(turretVertical);
  }

  /* Make sure feeder is turned off at initialization */
  @Override
  public void initialize() {
    turretVertical.setFeedRate(0.0);
  }

  /* Checks feeder button press */
  @Override
  public void execute() {
    if (controls.getTurretDown() && turretVertical.getTurretAngle() > Constants.TurretVertical.TURRET_LOWER_SOFT_LIMIT && !turretVertical.bottomLimitDown()) { 
      turretVertical.setFeedRate(Constants.TurretVertical.TURRET_VERTICAL_UP_RATE);
    } else if (controls.getTurretUp() && turretVertical.getTurretAngle() < Constants.TurretVertical.TURRET_UPPER_SOFT_LIMIT && !turretVertical.topLimitDown()) {
      turretVertical.setFeedRate(Constants.TurretVertical.TURRET_VERTICAL_DOWN_RATE);
    } else { // ensures shooter maintains position, doesn't fall
      turretVertical.setFeedRate(0.02 * Math.cos(Math.toRadians(turretVertical.getTurretAngle())));
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

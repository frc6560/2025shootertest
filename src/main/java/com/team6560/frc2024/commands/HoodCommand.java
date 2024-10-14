package com.team6560.frc2024.commands;

import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Hood;

import edu.wpi.first.wpilibj2.command.Command;

public class HoodCommand extends Command {

  final Hood hood;
  final ManualControls controls;

  public HoodCommand(Hood hood, ManualControls controls) {
    this.hood = hood;
    this.controls = controls;
    addRequirements(hood);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    
    boolean rumble = false;
    if (controls.getShootPosition()) {
      if (hood.setShootAngle()) {
        rumble = true;
      }
    } else if (controls.getPassPosition()) {
      if (hood.setPassAngle()) {
        rumble = true;
      }
    } else {
      hood.drop();
    }

    if (rumble) {
      controls.setShooterControllerRumble(.5);
    } else {
      controls.setShooterControllerRumble(0);
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}

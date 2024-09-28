package com.team6560.frc2024.commands;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Feeder;
import edu.wpi.first.wpilibj2.command.Command;

public class FeederCommand extends Command {
  final Feeder feeder;
  final ManualControls controls;

  public FeederCommand(Feeder feeder, ManualControls controls) {
    this.feeder = feeder;
    this.controls = controls;
    addRequirements(feeder);
  }

  /* Make sure feeder is turned off at initialization */
  @Override
  public void initialize() {
    feeder.setFeedRate(0.0);
  }

  /* Checks feeder button press */
  @Override
  public void execute() {
    if (controls.getRunFeeder() && !feeder.gamePieceDetected()) {
      feeder.setFeedRate(Constants.Feeder.FEEDER_FEED_RATE);
    } else {
      feeder.setFeedRate(0.0);
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

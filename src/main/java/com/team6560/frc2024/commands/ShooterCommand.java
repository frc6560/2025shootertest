package com.team6560.frc2024.commands;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;

public class ShooterCommand extends Command {
  final Shooter shooter;
  final ManualControls controls;

  public ShooterCommand(Shooter shooter, ManualControls controls) {
    this.shooter = shooter;
    this.controls = controls;
    addRequirements(shooter);
  }

  /* Make sure feeder is turned off at initialization */
  @Override
  public void initialize() {
    shooter.setFeedRate(0.0);
  }

  /* Checks feeder button press */
  @Override
  public void execute() {
    if (controls.getReverseShooter()) {
      shooter.setFeedRate(Constants.Feeder.FEEDER_FEED_RATE);
    } else if (controls.getRunShooter()) {
      shooter.setFeedRate(Constants.Feeder.FEEDER_REVERSE_RATE);
    } else {
      shooter.setFeedRate(0.0);
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

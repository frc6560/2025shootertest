package com.team6560.frc2024.commands;

import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Hood;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;

public class HoodCommand extends Command {

  final Hood hood;
  final ManualControls controls;

  private final NetworkTable limelightNetworkTable = NetworkTableInstance.getDefault().getTable("limelight");
  private final NetworkTableEntry limelightDistance = limelightNetworkTable.getEntry("distance");

  public HoodCommand(Hood hood, ManualControls controls) {
    this.hood = hood;
    this.controls = controls;
    addRequirements(hood);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (controls.getFancyHood()) {
      hood.setTrigAngle();
    } else if (controls.getUseLimelight() && limelightDistance.getDouble(-1) != -1) {
      hood.setDefaultAngle(); // limelightDistance.getDouble(0.0)
    } else {
      hood.setDefaultAngle();
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}

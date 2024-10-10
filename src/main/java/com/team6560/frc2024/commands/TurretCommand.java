package com.team6560.frc2024.commands;

import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Turret;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;

public class TurretCommand extends Command {

  final Turret turret;
  final ManualControls controls;

  private final NetworkTable limelightNetworkTable = NetworkTableInstance.getDefault().getTable("limelight");
  private final NetworkTableEntry limelightHorizontalAngle = limelightNetworkTable.getEntry("horizontal angle");

  public TurretCommand(Turret turret, ManualControls controls) {
    this.turret = turret;
    this.controls = controls;
    addRequirements(turret);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() { 
    if (controls.getUseLimelight() && limelightHorizontalAngle.getDouble(-1) != -1) {
      turret.setDefaultAngle(); // limelightHorizontalAngle.getDouble(0.0)
    } else {
      turret.setDefaultAngle();
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}

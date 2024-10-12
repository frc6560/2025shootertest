package com.team6560.frc2024.commands;

import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Turret;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;

import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

public class TurretCommand extends Command {

  final Turret turret;
  final ManualControls controls;
  private double turretAngle;

  private final NetworkTable limelightNetworkTable = NetworkTableInstance.getDefault().getTable("limelight");
  private final NetworkTableEntry limelightHorizontalAngle = limelightNetworkTable.getEntry("horizontal angle");

  public TurretCommand(Turret turret, ManualControls controls) {
    this.turret = turret;
    this.controls = controls;
    this.turretAngle = -16.5;
    ntDispTab("Turret Horizontal").add("Target Angle", this::getTurretAngle);
    addRequirements(turret);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() { 

    // testing configuration
    // if (controls.getTurretLeft()) {
    //   this.turretAngle -= 1;
    // } else if (controls.getTurretRight()) {
    //   this.turretAngle += 1;
    // }
    // turret.setTurretAngle(this.turretAngle);

    turret.setZeroAngle();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }

  private double getTurretAngle() {
    return this.turretAngle;
  }
}

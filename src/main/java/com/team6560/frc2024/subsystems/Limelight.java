package com.team6560.frc2024.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import com.team6560.frc2024.Constants;

import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// limelight used to control turret position
public class Limelight extends SubsystemBase {

  private final NetworkTable networkTable = NetworkTableInstance.getDefault().getTable("limelight");
    
  private final NetworkTableEntry ntX = networkTable.getEntry("tx");
  private final NetworkTableEntry ntY = networkTable.getEntry("ty");
  private final NetworkTableEntry ntV = networkTable.getEntry("tv");
  private final NetworkTableEntry ntA = networkTable.getEntry("ta");
  private final NetworkTableEntry ntL = networkTable.getEntry("tl");
  private final NetworkTableEntry ntcL = networkTable.getEntry("cl");
  private final NetworkTableEntry ntID = networkTable.getEntry("tid");

  // gets programmatically displayed limelight values and converts to better format
  public Limelight() {
    ntDispTab("Limelight")
    .add("Horizontal Angle", this::getHorizontalAngle)
    .add("Vertical Angle", this::getVerticalAngle)
    .add("Has Target", this::hasTarget);
  }

  public double getHorizontalAngle() {
    return ntX.getDouble(0.0);
  }

  public double getTargetArea() {
    return ntA.getDouble(0.0);
  }

  public double getVerticalAngle() {
    return ntY.getDouble(0.0);
  }

  public boolean hasTarget(){
    return ntV.getDouble(0.0) == 1.0;
  }

  public double getLatency() {
    // 11 additional ms is recommended for image capture latency
    // divided by 1000.0 to convert ms to s
    return (ntL.getDouble(0.0) + ntcL.getDouble(11.0))/1000.0;
  }

  public double getID() {
    return ntID.getDouble(0.0);
  }

  public double getSpeakerDistance() {
    if (!hasTarget())
      return 0.0;
    
    int id = (int) getID();
    double heightDifference = 0;

    switch (id) {
      case 3: heightDifference = (Constants.Limelight.ID_3_HEIGHT - Constants.Limelight.LIMELIGHT_HEIGHT);
      case 4: heightDifference = (Constants.Limelight.ID_4_HEIGHT - Constants.Limelight.LIMELIGHT_HEIGHT);
      case 7: heightDifference = (Constants.Limelight.ID_7_HEIGHT - Constants.Limelight.LIMELIGHT_HEIGHT);
      case 8: heightDifference = (Constants.Limelight.ID_8_HEIGHT - Constants.Limelight.LIMELIGHT_HEIGHT);
    }


    return heightDifference/(Math.tan((Constants.Limelight.LIMELIGHT_ANGLE_DEGREES + getVerticalAngle()) * Math.PI/180.0));
  }

  @Override
  public void periodic() {}
}

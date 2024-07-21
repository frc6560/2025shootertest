// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6560.frc2024.controls;

import com.team6560.frc2024.Constants;
// import com.team6560.frc2024.Constants.*;
import com.team6560.frc2024.utility.NumberStepper;
import com.team6560.frc2024.utility.PovNumberStepper;
import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

// import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class ManualControls {
  private XboxController xbox;

  private NetworkTable intakeTable;

  private XboxController controlStation;


  /**
   * Creates a new `ManualControls` instance.
   *
   * @param xbox the Xbox controller to use for manual control
   */
  public ManualControls(XboxController xbox) {
    this(xbox, null);
  }

  public ManualControls(XboxController xbox, XboxController controlStation) {
    this.xbox = xbox;
    if (controlStation == null)
      this.controlStation = xbox;
    else
      this.controlStation = controlStation;

    
    intakeTable = NetworkTableInstance.getDefault().getTable("Intake");
    intakeTable.getEntry("speed").setDouble(0.0);



  }

  public boolean getAim(){
    return controlStation.getRightBumper();
    // return false;
  }
  
  public boolean getSafeAim(){
    return controlStation.getRightTriggerAxis() > 0.2;
  }


  public boolean getReverseTransfer(){
    return controlStation.getXButton();
  }
  

  // ------------------------------ INTAKE ------------------------------ \\

  public boolean getRunIntake(){
    return getSafeAim() || getAim();
    // return controlStation.getLeftBumper();
  }

  public void setXboxRumble(double output){
    xbox.setRumble(RumbleType.kBothRumble, output);
  }

  public void setControlStationRumble(double output){
    controlStation.setRumble(RumbleType.kBothRumble, output);
  }
}
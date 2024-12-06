package com.team6560.frc2024.controls;

import com.team6560.lib.controls.DriveControlsConfig;
import com.team6560.lib.controls.GenericDoubleXboxControls;

import edu.wpi.first.wpilibj.XboxController;

public class ManualControls extends GenericDoubleXboxControls {
    
    public ManualControls(XboxController driverController, XboxController shooterController) {
        super(driverController, shooterController, new DriveControlsConfig.Builder().build());
    }

    public ManualControls(XboxController driverController, XboxController shooterController, DriveControlsConfig config) {
        super(driverController, shooterController, config);
    }

    public boolean getRunIntake() {
        return driverController.getRightBumper();
    }

    public boolean getReverseIntake() {
        return driverController.getLeftBumper();
    }

    public boolean getRunShooter() {
        return driverController.getAButton();
    }

    public boolean getRunTransfer() {
        return driverController.getXButton();
    }

    public boolean getRunAmp() {
        return driverController.getLeftTriggerAxis() > .2;
    }

}

package com.team6560.frc2024.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class ManualControls {    
    private XboxController driverController;
    private XboxController shooterController;

    private NetworkTableEntry ntIntakeSpeed;

    public ManualControls(XboxController driverController) {
        this(driverController, null);
    }

    /* ManualControls is initialized with two controllers as parameters - one for driving, another for shooting (turret control) */
    public ManualControls(XboxController driverController, XboxController shooterController) {
        this.driverController = driverController;
        this.shooterController = shooterController == null ? driverController : shooterController; 

        // Creating a NetworkTableEntry object instead of a NetworkTable allows for retrieving data using .get()
        ntIntakeSpeed = NetworkTableInstance.getDefault().getTable("Intake").getEntry("speed");
        ntIntakeSpeed.setDouble(0.0);
    }

    /* Run intake - A for now */
    public boolean getRunIntake() { 
        return driverController.getAButton();
    }

    /* Reverse intake - B for now */
    public boolean getReverseIntake() { 
        return driverController.getBButton();
    }

    /* Set rumble level of driver controller. Accepts values from 0 to 1 */
    public void setDriverControllerRumble(double output){
        driverController.setRumble(RumbleType.kBothRumble, output);
    }
    
    /* Set rumble level of shooter controller. Accepts values from 0 to 1 */
    public void setShooterControllerRumble(double output){
        shooterController.setRumble(RumbleType.kBothRumble, output);
    }
}

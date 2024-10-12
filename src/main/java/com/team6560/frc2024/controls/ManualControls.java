package com.team6560.frc2024.controls;

import edu.wpi.first.wpilibj.XboxController;

import com.team6560.frc2024.Constants;

import com.team6560.frc2024.utility.NumberStepper;
import com.team6560.frc2024.utility.PovNumberStepper;
import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

public class ManualControls {    

    private XboxController driverController;
    private XboxController shooterController;

    private final PovNumberStepper speed;
    private final PovNumberStepper turnSpeed;

    /* ManualControls is initialized with two controllers - one for driving, another for shooting (turret control) */
    public ManualControls(XboxController driverController, XboxController shooterController) {

        this.driverController = driverController;
        this.shooterController = shooterController;

        // speed and turnSpeed incremented by POV axes on driver controller

        this.speed = new PovNumberStepper(
            new NumberStepper(
                Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND * Constants.Controller.SPEED_INITIAL_PERCENT, 
                Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND * Constants.Controller.SPEED_MIN_PERCENT,
                Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND * Constants.Controller.SPEED_MAX_PERCENT, 
                Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND * Constants.Controller.SPEED_STEP_PERCENT
            ),
            driverController,
            PovNumberStepper.PovDirection.VERTICAL
        );

        this.turnSpeed = new PovNumberStepper(
            new NumberStepper(
                Constants.Drivetrain.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND * Constants.Controller.TURN_SPEED_INITIAL_PERCENT, 
                Constants.Drivetrain.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND * Constants.Controller.TURN_SPEED_MIN_PERCENT, 
                Constants.Drivetrain.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND * Constants.Controller.TURN_SPEED_MAX_PERCENT,
                Constants.Drivetrain.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND * Constants.Controller.TURN_SPEED_STEP_PERCENT
            ),
            driverController,
            PovNumberStepper.PovDirection.HORIZONTAL
        );

        ntDispTab("Controls")
        .add("Y Joystick", this::driveY)
        .add("X Joystick", this::driveX)
        .add("Rotation Joystick", this::driveRotationX);
    }

    // UTIL

    /* Deadband - filters out values with absolute value less than deadband, normalizes otherwise. */
    private static double deadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } 
            return (value + deadband) / (1.0 - deadband);
        }
        return 0.0;
    }
    
    /* Filter function applied to raw controller input. Applies deadband and squares value to allow both sensitivity and speed. */
    private static double modifyAxis(double value) {
        value = deadband(value, Constants.Controller.CONTROLLER_DEADBAND);
        value = Math.copySign(value * value, value);
        return value;
    }


    // FIRST DRIVER


    // DRIVING
    // Note: driveX/driveY use reversed controller axis due to 90 degree orientation of robot in relation to field coordinates.

    /* Get processed X-Axis driverController left stick input. */
    public double driveX() {
        return modifyAxis(driverController.getLeftY() * speed.get());
    }

    /* Get processed Y-Axis driverController left stick input. */
    public double driveY() {
        return modifyAxis(driverController.getLeftX() * speed.get());
    }

    /* Get processed X-Axis driverController right stick input. */
    public double driveRotationX() {
        return modifyAxis(driverController.getRightX() * turnSpeed.get());
    }

    /* Get processed Y-Axis driverController right stick input. */
    public double driveRotationY() {
        return modifyAxis(driverController.getRightY() * turnSpeed.get());
    }

    /* Resets robot yaw (left/right turn) */
    public boolean driveResetYaw() {
        return driverController.getStartButton();
    }
  
    /* Resets robot position */
    public boolean driveResetGlobalPose() {
        return driverController.getBackButton();
    }

    // INTAKE

    public boolean getRunIntake() { 
        return driverController.getRightBumper();
    }

    public boolean getReverseIntake() { 
        return driverController.getLeftBumper();
    }



    // SECOND DRIVER



    // SHOOTER

    /* Run transfer (fire) - Right shoulder button */
    
    public boolean getRunTransfer() {
        return shooterController.getRightBumper();
    }

    /* Accelerate shooter - left shoulder button */
    public boolean getRunShooter() { 
        return shooterController.getLeftBumper();
    }

    public boolean getFancyHood() {
        return shooterController.getStartButton();
    }

    // LIMELIGHT

    /* Activate limelight tracking */
    public boolean getUseLimelight() {
        return shooterController.getAButton();
    }

    

    // testing horizontal turret

    // public boolean getTurretLeft() {
    //     return shooterController.getYButton();
    // }
    // 
    // public boolean getTurretRight() {
    //     return shooterController.getXButton();
    // }

}

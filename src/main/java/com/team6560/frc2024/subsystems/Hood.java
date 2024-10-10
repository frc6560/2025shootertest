package com.team6560.frc2024.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.team6560.frc2024.Constants;
import com.team6560.frc2024.utility.PID.PIDConfigFuncs;
import com.team6560.frc2024.utility.PID.TalonFXPIDConfigProfile;

import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import java.time.Instant;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase {

    final TalonFX motor;
    final DigitalInput topLimitSwitch;
    final DigitalInput bottomLimitSwitch;
    
    private double encoderInitialValue;

    private static final double UP_RATE = 0.1;
    private static final double DOWN_RATE = -0.1;

    private static final double GEAR_RATIO = 25 * 360 / 40;

    private static final double LOWER_SOFT_LIMIT = 13.0;
    private static final double UPPER_SOFT_LIMIT = 53.0;

    private static final double LOWER_HARD_LIMIT = 7.0;
    private static final double UPPER_HARD_LIMIT = 71.0;

    private static final int UPPER_LIMIT_SWITCH_ID = 8;
    private static final int LOWER_LIMIT_SWITCH_ID = 9;

    public static final TalonFXPIDConfigProfile PID_PROFILE = new TalonFXPIDConfigProfile(
      .5, 0, 0
    );

    private static final double DEFAULT_ANGLE = 51.7;
    private static final double ANGLE_THRESHOLD = 1.5;

    public Hood() { 
        this.motor = new TalonFX(Constants.CanIDs.HOOD_MOTOR_ID); 
        this.motor.setNeutralMode(NeutralModeValue.Brake);
        PIDConfigFuncs.configurePID(motor, PID_PROFILE);
        this.topLimitSwitch = new DigitalInput(UPPER_LIMIT_SWITCH_ID);
        this.bottomLimitSwitch = new DigitalInput(LOWER_LIMIT_SWITCH_ID);
        this.encoderInitialValue = this.motor.getPosition().getValueAsDouble(); // initial encoder pos in terms of rotations

        ntDispTab("Hood ")
            .add("Hood Feed Rate", this::getFeedRate)
            .add("Hood Angle", this::getAngle)
            .add("Upper limit switch", this::topLimitDown)  
            .add("Lower limit switch", this::bottomLimitDown)   
            .add("Upper soft limit", this::getUpperBound)
            .add("Lower soft limit", this::getLowerBound);
    }   

    private void setFeedRate(double speed) {
        motor.set(speed);
    }

    private double getFeedRate() {
        return motor.getVelocity().getValueAsDouble();
    }

    public double getAngle() {
        return ((motor.getPosition().getValueAsDouble() * 360) - (this.encoderInitialValue * 360)) / GEAR_RATIO + LOWER_HARD_LIMIT;
    }

    public void setAngle(double targetAngle) {
        double currentAngle = this.getAngle();
        
        if (currentAngle < LOWER_SOFT_LIMIT) {
            this.setFeedRate(UP_RATE);
        } else if (currentAngle > UPPER_SOFT_LIMIT) {
            this.setFeedRate(DOWN_RATE);
        } else {
            double angleDifference = targetAngle - currentAngle;
    
            if (Math.abs(angleDifference) < ANGLE_THRESHOLD) {
                double slowDownFactor = Math.max(0.1, Math.abs(angleDifference) / ANGLE_THRESHOLD);
                this.setFeedRate(UP_RATE * slowDownFactor); 
            } else if (currentAngle < targetAngle - ANGLE_THRESHOLD) {
                this.setFeedRate(UP_RATE);
            } else if (currentAngle > targetAngle + ANGLE_THRESHOLD) {
                this.setFeedRate(DOWN_RATE);
            }
        }
    }

    public void setTrigAngle() {
        double timeInSeconds = Instant.now().getEpochSecond() % 60; 
        double targetAngle = LOWER_HARD_LIMIT + (UPPER_HARD_LIMIT - LOWER_HARD_LIMIT) / 2 
                             + (UPPER_HARD_LIMIT - LOWER_HARD_LIMIT) / 2 * Math.sin(Math.toRadians(timeInSeconds * 6));
        this.setAngle(targetAngle);
    }

    public void setDefaultAngle() {
        this.setAngle(DEFAULT_ANGLE);
    }

    private boolean topLimitDown() {
        return !this.topLimitSwitch.get();
    }

    private boolean bottomLimitDown() {
        if (!this.bottomLimitSwitch.get()) {
            this.encoderInitialValue = this.motor.getPosition().getValueAsDouble();
            return true;
        }
        return false;
    }

    public double getLowerBound() {
        return LOWER_SOFT_LIMIT;
    }

    public double getUpperBound() {
        return UPPER_SOFT_LIMIT;
    }
}

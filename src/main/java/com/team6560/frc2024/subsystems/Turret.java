package com.team6560.frc2024.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController.AccelStrategy;
import com.revrobotics.CANSparkMax;
import com.team6560.frc2024.Constants;
import com.team6560.frc2024.utility.PID.SparkPIDConfigProfile;
import com.team6560.frc2024.utility.PID.TalonFXPIDConfigProfile;

import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {
    
    final CANSparkMax motor;
    final DigitalInput limitSwitch;

    private double encoderInitialValue;

    private static final double CW_RATE = 0.08;
    private static final double CCW_RATE = -0.08;

    private static final double GEAR_RATIO = 5.0 * 3 / 2 * 400 / 46;
    private static final double SOFTWARE_CORRECTION_RATIO = 90.0 / 93; // compensates some GR innacuracy

    private static final double CCW_SOFT_LIMIT = -90.0;
    private static final double CW_SOFT_LIMIT = 90.0;
    private static final double DEFAULT_ANGLE = -16.5;
    private static final double ANGLE_THRESHOLD = 3;
    
    private static final int LIMIT_SWITCH_PORT = 6;

    public static final SparkPIDConfigProfile PID_PROFILE = new SparkPIDConfigProfile(
      .5, 0, 0, 0, AccelStrategy.kTrapezoidal, 100, 1000
    );

    public Turret() { 
        this.motor = new CANSparkMax(Constants.CanIDs.TURRET_MOTOR_ID, MotorType.kBrushless);
        this.motor.restoreFactoryDefaults();
        this.motor.setIdleMode(IdleMode.kBrake);
        this.motor.setSmartCurrentLimit(25);
        this.motor.setOpenLoopRampRate(0.1);
        this.limitSwitch = new DigitalInput(LIMIT_SWITCH_PORT);
        this.encoderInitialValue = this.motor.getEncoder().getPosition();

        ntDispTab("Turret Horizontal")
            .add("Turret Rotation Feed Rate", this::getFeedRate)
            .add("Turret Angle", this::getTurretAngle)
            .add("Limit down", this::limitDown)
            .add("CW soft limit", this::getCWBound)
            .add("CCW soft limit", this::getCCWBound);
    }   
    
    private void setFeedRate(double speed) {
        motor.set(speed);
    }

    private double getFeedRate() {
        return motor.get();
    }

    public double getTurretAngle() {
        return ((motor.getEncoder().getPosition() * 360) - (this.encoderInitialValue * 360)) / 
        GEAR_RATIO * SOFTWARE_CORRECTION_RATIO + DEFAULT_ANGLE;
    }

    /* 0 degrees is front of robot */
    public void setTurretAngle(double targetAngle) {
        double currentAngle = this.getTurretAngle();

        if (targetAngle > CW_SOFT_LIMIT) {
            targetAngle = CW_SOFT_LIMIT - ANGLE_THRESHOLD;
        } else if (targetAngle < CCW_SOFT_LIMIT) {  
            targetAngle = CCW_SOFT_LIMIT + ANGLE_THRESHOLD;
        }

        double angleDifference = targetAngle - currentAngle;

        if (currentAngle < targetAngle - angleDifference) {
            this.setFeedRate(CW_RATE);
        } else if (currentAngle > targetAngle + angleDifference) {
            this.setFeedRate(CCW_RATE);
        } else if (currentAngle < targetAngle) {
            double slowDownFactor = Math.max(0.05, Math.abs(angleDifference) / (ANGLE_THRESHOLD * 2));
            this.setFeedRate(CW_RATE * slowDownFactor);
        } else {
            double slowDownFactor = Math.max(0.05, Math.abs(angleDifference) / (ANGLE_THRESHOLD * 2));
            this.setFeedRate(CCW_RATE * slowDownFactor);
        }

    }

    public void setZeroAngle() {
        this.setTurretAngle(0.0);
    }

    public boolean limitDown() {
        if (!this.limitSwitch.get()) {
            this.encoderInitialValue = this.motor.getEncoder().getPosition();
            return true;
        }
        return false;
    }

    public double getCWBound() {
        return CW_SOFT_LIMIT;
    }

    public double getCCWBound() {
        return CCW_SOFT_LIMIT;
    }
}


package com.team6560.frc2024.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.team6560.frc2024.Constants;
import com.team6560.frc2024.utility.PID.PIDConfigFuncs;
import com.team6560.frc2024.utility.PID.TalonFXPIDConfigProfile;

import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    final TalonFX motor1;
    final TalonFX motor2;
    final TalonFX motor3;

    private static final double SHOOTER_FEED_RATE = 0.9;
    public static final TalonFXPIDConfigProfile SHOOTER_PID_PROFILE = new TalonFXPIDConfigProfile(
      1.2, 0.1, 0.01
    );

    // IMPORTANT: all motors are coupled and must have same speed
    public Shooter() { 
        this.motor1 = new TalonFX(Constants.CanIDs.SHOOTER_MOTOR_ONE_ID); 
        this.motor2 = new TalonFX(Constants.CanIDs.SHOOTER_MOTOR_TWO_ID);
        this.motor3 = new TalonFX(Constants.CanIDs.SHOOTER_MOTOR_THREE_ID);

        PIDConfigFuncs.configurePID(motor1, SHOOTER_PID_PROFILE);
        PIDConfigFuncs.configurePID(motor2, SHOOTER_PID_PROFILE);
        PIDConfigFuncs.configurePID(motor3, SHOOTER_PID_PROFILE);

        ntDispTab("Shooter")
            .add("Shooter Feed Rate", this::getFeedRate)
            .add("Shooter Velocity RPM", this::getVelocity)
            .add("Shooter Max Velocity RPM", this::getMaxVelocity);
    }   
    
    public void setFeedRate(double speed) {
        motor1.set(-speed);
        motor2.set(speed);
        motor3.set(speed);
    }

    public void run() {
        setFeedRate(SHOOTER_FEED_RATE);
    }

    public void stop() {
        setFeedRate(0.0);
    }

    private double getFeedRate() {
        return motor3.get();
    }

    // Velocity is that of flywheels (1.5 ascending GR)

    private double getVelocity() {
        return motor3.getVelocity().getValueAsDouble() * 60.0 * 1.5;
    }

    private double getMaxVelocity() {
        return 4250.0 * 1.5;
    }
}

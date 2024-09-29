package com.team6560.frc2024.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.team6560.frc2024.Constants;

import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Horizontal turret subsystem controls
public class TurretHorizontal extends SubsystemBase {
    
    final CANSparkMax motor;

    public TurretHorizontal() { 
        this.motor = new CANSparkMax(Constants.CanIDs.TURRET_HORIZONTAL_MOTOR_ID, MotorType.kBrushless);
        this.motor.restoreFactoryDefaults();
        this.motor.setIdleMode(IdleMode.kBrake);
        this.motor.setSmartCurrentLimit(25);
        this.motor.setOpenLoopRampRate(0.1);
        ntDispTab("Turret Horizontal").add("Turret Rotation Feed Rate", this::getFeedRate);
        ntDispTab("Turret Horizontal").add("Turret Position", this::getTurretPosition);
    }   
    
    /* Set feed speed of motor */
    public void setFeedRate(double speed) {
        motor.set(speed);
    }

    /* Get feed speed of motor */
    public double getFeedRate() {
        return motor.get();
    }

    /* Get position reading from encoder */
    public double getTurretPosition() {
        return motor.getEncoder().getPosition() * 360 / Constants.TurretHorizontal.TURRET_HORIZONTAL_GEAR_RATIO;
    }
}


package com.team6560.frc2024.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.team6560.frc2024.Constants;

import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Transfer extends SubsystemBase {
    final CANSparkMax feederMotor;

    private static final double FEED_RATE = 1.0;
    private static final double REVERSE_RATE = -1.0;

    public Transfer() { 
        this.feederMotor = new CANSparkMax(Constants.CanIDs.TRANSFER_MOTOR_ID, MotorType.kBrushless);
        this.feederMotor.restoreFactoryDefaults();
        this.feederMotor.setIdleMode(IdleMode.kBrake);
        this.feederMotor.setSmartCurrentLimit(25);
        this.feederMotor.setOpenLoopRampRate(0.1);
        ntDispTab("Transfer").add("Transfer Feed Rate", this::getFeedRate);
    }   
    
    public void setFeedRate(double speed) {
        feederMotor.set(speed);
    }

    public void run() {
        this.setFeedRate(FEED_RATE);
    }

    public void reverse() {
        this.setFeedRate(REVERSE_RATE);
    }

    public void stop() {
        this.setFeedRate(0.0);
    }

    private double getFeedRate() {
        return feederMotor.get();
    }
}

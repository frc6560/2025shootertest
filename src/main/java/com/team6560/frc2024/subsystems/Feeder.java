package com.team6560.frc2024.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.team6560.frc2024.Constants;

import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Amp subsystem controls
public class Feeder extends SubsystemBase {
    final CANSparkMax feederMotor;

    public Feeder() { 
        this.feederMotor = new CANSparkMax(Constants.CanIDs.FEEDER_MOTOR_ID, MotorType.kBrushless);
        this.feederMotor.restoreFactoryDefaults();
        this.feederMotor.setIdleMode(IdleMode.kBrake);
        this.feederMotor.setSmartCurrentLimit(25);
        this.feederMotor.setOpenLoopRampRate(0.1);
        ntDispTab("Amp").add("Amp Feed Rate", this::getFeedRate);
    }   
    
    /* Set feed speed of motor */
    public void setFeedRate(double speed) {
        feederMotor.set(speed);
    }

    /* Get feed speed of motor */
    public double getFeedRate() {
        return feederMotor.get();
    }
}

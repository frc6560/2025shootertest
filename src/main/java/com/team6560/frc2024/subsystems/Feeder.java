package com.team6560.frc2024.subsystems;

import au.grapplerobotics.LaserCan;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.team6560.frc2024.Constants;

import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import javax.sound.sampled.BooleanControl;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Amp subsystem controls
public class Feeder extends SubsystemBase {
    public LaserCan lc;
    final CANSparkMax feederMotor;

    public DistanceSensor(){
        lc = new LaserCan(0);
        lc.setRangingMode(LaserCan.RangingMode.SHORT);
        lc.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
    }

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

    /*checks if there is a game piece within 20 mm of the sensor */
    public boolean gamePieceDetected() {
        double distance = lc.GetMeasurement().distance_mm;
        return (distance <= Constants.MaxDistToGamePiece);
    }
}

package com.team6560.frc2024.subsystems;

import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;

import com.team6560.frc2024.Constants;
import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    
    final TalonFX intakeMotor;
    final CANSparkMax transferMotor;
    final DigitalInput distanceSensor;

    private static final int DISTANCE_SENSOR_PORT = 7;

    private static final double FEED_RATE = 0.6;
    private static final double REVERSE_RATE = -0.3;

    public Intake() { 
        this.distanceSensor = new DigitalInput(DISTANCE_SENSOR_PORT);
        this.intakeMotor = new TalonFX(Constants.CanIDs.INTAKE_FEED_MOTOR_ID);
        this.intakeMotor.getConfigurator().apply((new TalonFXConfiguration()).withOpenLoopRamps(new OpenLoopRampsConfigs().withVoltageOpenLoopRampPeriod(0.5)));
        this.transferMotor = new CANSparkMax(Constants.CanIDs.INTAKE_TRANSFER_MOTOR_ID, MotorType.kBrushless);
        this.transferMotor.restoreFactoryDefaults();
        this.transferMotor.setIdleMode(IdleMode.kBrake);
        this.transferMotor.setSmartCurrentLimit(25);
        this.transferMotor.setOpenLoopRampRate(0.5);
        ntDispTab("Intake")
            .add("Transfer Feed Rate", this::getTransferFeedRate)
            .add("Intake Feed Rate", this::getIntakeFeedRate)
            .add("Distance sensor", this::gamePieceIn);
    }   
    
    private void setFeedRate(double speed) {
        intakeMotor.set(-speed);
        transferMotor.set(-speed);
    }

    public void intake() {
        this.setFeedRate(FEED_RATE);
    }

    public void outtake() {
        this.setFeedRate(REVERSE_RATE);
    }

    public void stop() {
        this.setFeedRate(0.0);
    }

    private double getIntakeFeedRate() {
        return intakeMotor.get();
    }

    private double getTransferFeedRate() {
        return transferMotor.get();
    }

    public boolean gamePieceIn() {
        return !this.distanceSensor.get();
    }
}

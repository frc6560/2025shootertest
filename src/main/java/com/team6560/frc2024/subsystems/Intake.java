package com.team6560.frc2024.subsystems;

import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import com.team6560.frc2024.Constants;
import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Note: Intake class covers both intake and transfer mechanisms.
public class Intake extends SubsystemBase {
    
    final TalonFX intakeMotor;
    final CANSparkMax transferMotor;

    public Intake() { 
        this.intakeMotor = new TalonFX(Constants.CanIDs.INTAKE_FEED_MOTOR_ID);
        this.intakeMotor.getConfigurator().apply((new TalonFXConfiguration()).withOpenLoopRamps(new OpenLoopRampsConfigs().withVoltageOpenLoopRampPeriod(0.5)));
        this.transferMotor = new CANSparkMax(Constants.CanIDs.TRANSFER_MOTOR_ID, MotorType.kBrushless);
        this.transferMotor.restoreFactoryDefaults();
        this.transferMotor.setIdleMode(IdleMode.kBrake);
        this.transferMotor.setSmartCurrentLimit(25);
        this.transferMotor.setOpenLoopRampRate(0.5);
        ntDispTab("Intake")
            .add("Transfer Feed Rate", this::getTransferFeedRate)
            .add("Intake Feed Rate", this::getIntakeFeedRate);
    }   
    
    /* Set feed speed of intake motor. Intake motor is reversed. */
    public void setFeedRate(double speed) {
        intakeMotor.set(-speed);
        transferMotor.set(-speed);
    }

    /* Get feed speed of intake motor */
    public double getIntakeFeedRate() {
        return intakeMotor.get();
    }

    /* Get feed speed of transfer motor */
    public double getTransferFeedRate() {
        return transferMotor.get();
    }

    public boolean GamePieceIn() {
        final AnalogInput AnalogInput = new AnalogInput(Constants.CanIDs.DISTANCE_SENSOR_ID);
        double AnalogVoltage = AnalogInput.getVoltage();
        return (AnalogVoltage >= 1.6);
    }
}

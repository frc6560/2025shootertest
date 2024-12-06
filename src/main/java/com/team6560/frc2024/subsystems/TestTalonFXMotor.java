package com.team6560.frc2024.subsystems;

import com.team6560.lib.hardware.motors.TalonFXMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.team6560.lib.util.NetworkTable.NtValueDisplay;

public class TestTalonFXMotor extends SubsystemBase {
    
    private TalonFXMotor motor;

    public TestTalonFXMotor() {

        // testing with intake talonfx motor
        motor = new TalonFXMotor(20)
            .withOpenLoopRampConfig(5)
            .withReversedMotor();
        motor.setCurrentPositionAsZero();
        motor.setOpenLoopDutyCycle(-0.6);
        motor.setReverseSoftLimit(-200);
        motor.setForwardSoftLimit(300);
        NtValueDisplay.ntDispTab("TestTalonFXMotor")
        .add("Applied volts", () -> this.motor.getAppliedVolts())
        .add("Current supply amps", () -> this.motor.getCurrentSupplyAmps())
        .add("Current stator amps", () -> this.motor.getCurrentStatorAmps())
        .add("Duty cycle percentage", () -> this.motor.getDutyCyclePercent())
        .add("Velocity", () -> this.motor.getVelocityRPM())
        .add("Position (rotations)", () -> this.motor.getPositionRotations());
    }

}


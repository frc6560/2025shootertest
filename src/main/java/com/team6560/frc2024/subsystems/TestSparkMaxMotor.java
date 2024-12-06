package com.team6560.frc2024.subsystems;

import com.team6560.lib.hardware.motors.SparkMaxMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.team6560.lib.util.NetworkTable.NtValueDisplay;

public class TestSparkMaxMotor extends SubsystemBase {
    
    private SparkMaxMotor motor;

    public TestSparkMaxMotor() {

        // testing with transfer sparkmax motor
        motor = new SparkMaxMotor(19)
            .withPIDProfile(5, 0, 2, 0);
        motor.setOpenLoopDutyCycle(1);
        NtValueDisplay.ntDispTab("TestSparkMaxMotor")
        .add("Applied volts", () -> this.motor.getAppliedVolts())
        .add("Current supply amps", () -> this.motor.getCurrentSupplyAmps())
        .add("Duty cycle percentage", () -> this.motor.getDutyCyclePercent())
        .add("Velocity", () -> this.motor.getVelocityRPM())
        .add("Position (rotations)", () -> this.motor.getPositionRotations());
        motor.setOpenLoopDutyCycle(1);
    }

}

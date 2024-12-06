package com.team6560.frc2024.subsystems;

import com.team6560.lib.hardware.motors.RollerSubsystemMotor;
import com.team6560.lib.hardware.motors.RollerSubsystemMotor.MotorMode;
import com.team6560.lib.subsystems.roller.BasicRollerSubsystem;
import com.team6560.lib.hardware.motors.TalonFXMotor;

import com.team6560.frc2024.Constants;

public class Shooter extends BasicRollerSubsystem {

    private final double MOTOR_CURRENT_LIMIT = 50.0;

    private final double kP = .05;
    private final double kI = .01;
    private final double kD = 0.0;

    private final double MOTOR_TARGET_VELOCITY = 4000.0;
    private final double MOTOR_REVERSE_TARGET_VELOCITY = -1000.0;

    public Shooter() {
        super("Shooter");
        withMotor(
            new RollerSubsystemMotor(
                new TalonFXMotor(Constants.CanIDs.SHOOTER_MOTOR_ONE_ID)
                    .withPIDProfile(kP, kI, kD),
                1,
                -1,
                MotorMode.DUTY_CYCLE
            )
        )
        .withMotor(
            new RollerSubsystemMotor(
                new TalonFXMotor(Constants.CanIDs.SHOOTER_MOTOR_ONE_ID)
                    .withReversedMotor()
                    .withPIDProfile(kP, kI, kD),
                1,
                -1,
                MotorMode.DUTY_CYCLE
            )
        )
        .withMotor(
            new RollerSubsystemMotor(
                new TalonFXMotor(Constants.CanIDs.SHOOTER_MOTOR_ONE_ID)
                    .withCurrentLimit(MOTOR_CURRENT_LIMIT)
                    .withPIDProfile(kP, kI, kD),
                1,
                -1,
                MotorMode.DUTY_CYCLE
            )
        )
        .build();
    }
}

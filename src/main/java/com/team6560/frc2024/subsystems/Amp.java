package com.team6560.frc2024.subsystems;

import com.team6560.lib.hardware.motors.RollerSubsystemMotor;
import com.team6560.lib.hardware.motors.SparkMaxMotor;
import com.team6560.lib.hardware.motors.RollerSubsystemMotor.MotorMode;
import com.team6560.lib.subsystems.roller.BasicRollerSubsystem;

import com.team6560.frc2024.Constants;

public class Amp extends BasicRollerSubsystem {
    
    private static final double FEED_RATE = 0.6;
    private static final double REVERSE_RATE = -0.6;
    private static final double OPEN_LOOP_RAMP_TIME = 0.1;

    public Amp() {
        super("Amp");
        withMotor(
            new RollerSubsystemMotor(
                new SparkMaxMotor(Constants.CanIDs.AMP_MOTOR_ID)
                .withOpenLoopRampConfig(OPEN_LOOP_RAMP_TIME)
                .withBrakeMode(),
                FEED_RATE,
                REVERSE_RATE,
                MotorMode.DUTY_CYCLE
            )
        )
        .build();
    }
}

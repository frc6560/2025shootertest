package com.team6560.frc2024.utility.PID;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

/* Functions for configuring motor PID values */
public class PIDConfigFuncs {

    /* Configure PID constants for CANSparkMax-type motors */
    public static void configurePID(CANSparkMax motor, SparkPIDConfigProfile profile) {
        
        SparkPIDController motorPID = motor.getPIDController();

        motorPID.setP(profile.p, 0);
        motorPID.setI(profile.i, 0);
        motorPID.setD(profile.d, 0);
        motorPID.setFF(profile.ff, 0);

        motorPID.setSmartMotionAccelStrategy(profile.accelStrategy, 0);
        motorPID.setSmartMotionMaxAccel(profile.maxAccel, 0);
        motorPID.setSmartMotionMaxVelocity(profile.maxVelocity, 0);
    }

    /* Configure PID constants for TalonFX-type motors */
    public static void configurePID(TalonFX motor, TalonFXPIDConfigProfile profile) {

        TalonFXConfiguration motorConfiguration = new TalonFXConfiguration();
        motorConfiguration.Slot0.kP = profile.p;
        motorConfiguration.Slot0.kI = profile.i;
        motorConfiguration.Slot0.kD = profile.d;        

        motor.getConfigurator().apply(motorConfiguration);
    }

}

package com.team6560.frc2024.utility.PID;

import com.revrobotics.SparkPIDController.AccelStrategy;

// CANSparkMax PID config profile
public class SparkPIDConfigProfile {
    
    public double p, i, d, ff, maxAccel, maxVelocity;
    public AccelStrategy accelStrategy;

    public SparkPIDConfigProfile(double p, double i, double d, double ff, AccelStrategy accelStrategy, double maxAccel, double maxVelocity
    ) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.ff = ff;
        this.accelStrategy = accelStrategy;
        this.maxAccel = maxAccel;
        this.maxVelocity = maxVelocity;
    }
}

package com.team6560.frc2024.utility.PID;

// TalonFX PID config profile
public class TalonFXPIDConfigProfile {
    
    public double p, i, d;

    public TalonFXPIDConfigProfile(double p, double i, double d) {
        this.p = p;
        this.i = i;
        this.d = d;
    }
}

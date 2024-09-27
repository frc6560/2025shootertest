package com.team6560.frc2024.subsystems;

import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.team6560.frc2024.Constants;
import com.team6560.frc2024.utility.PID.PIDConfigFuncs;
import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Vertical turret subsystem controls
public class TurretVertical extends SubsystemBase {

    final TalonFX motor;

    public TurretVertical() { 
        this.motor = new TalonFX(Constants.CanIDs.TURRET_VERTICAL_MOTOR_ID); 
        this.motor.getConfigurator().apply(new TalonFXConfiguration().withOpenLoopRamps(new OpenLoopRampsConfigs().withVoltageOpenLoopRampPeriod(0.5)));
        ntDispTab("TurretVertical").add("Turret Vertical Feed Rate", this::getFeedRate);
    }   
    
    /* Set feed speed of motor */
    public void setFeedRate(double speed) {
        motor.set(speed);
    }

    /* Get feed speed of motor */
    public double getFeedRate() {
        return motor.get();
    }
}


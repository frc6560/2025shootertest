package com.team6560.frc2024.subsystems;

import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.team6560.frc2024.Constants;
import com.team6560.frc2024.utility.PID.PIDConfigFuncs;
import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Subsystem for shooter (top of turret)
public class Shooter extends SubsystemBase {

    final TalonFX motor1;
    final TalonFX motor2;
    final TalonFX motor3;

    // IMPORTANT: all motors are coupled and must have same speed
    public Shooter() { 
        this.motor1 = new TalonFX(Constants.CanIDs.SHOOTER_MOTOR_ONE_ID); 
        this.motor2 = new TalonFX(Constants.CanIDs.SHOOTER_MOTOR_TWO_ID);
        this.motor3 = new TalonFX(Constants.CanIDs.SHOOTER_MOTOR_THREE_ID);

        this.motor1.getConfigurator().apply(new TalonFXConfiguration().withOpenLoopRamps(new OpenLoopRampsConfigs().withVoltageOpenLoopRampPeriod(0.5)));
        this.motor2.getConfigurator().apply(new TalonFXConfiguration().withOpenLoopRamps(new OpenLoopRampsConfigs().withVoltageOpenLoopRampPeriod(0.5)));
        this.motor3.getConfigurator().apply(new TalonFXConfiguration().withOpenLoopRamps(new OpenLoopRampsConfigs().withVoltageOpenLoopRampPeriod(0.5)));

        // PIDConfigFuncs.configurePID(motor1, Constants.Shooter.SHOOTER_PID_PROFILE);
        // PIDConfigFuncs.configurePID(motor2, Constants.Shooter.SHOOTER_PID_PROFILE);
        // PIDConfigFuncs.configurePID(motor3, Constants.Shooter.SHOOTER_PID_PROFILE);

        ntDispTab("Shooter").add("Shooter Feed Rate", this::getFeedRate);
    }   
    
    /* Set feed speed of motors */
    public void setFeedRate(double speed) {
        motor1.set(-speed);
        motor2.set(speed);
        motor3.set(speed);
    }

    /* Get feed speed of motors */
    public double getFeedRate() {
        return motor3.get();
    }

}

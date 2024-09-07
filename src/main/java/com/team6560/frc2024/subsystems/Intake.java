package com.team6560.frc2024.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.team6560.frc2024.Constants;
import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    
    final TalonFX feedMotor;

    /* Basic intake - no PID (yet) 
     * Initialize motor and apply default configuration to keep behavior consistent.
    */
    public Intake() { 
        this.feedMotor = new TalonFX(Constants.CanIDs.INTAKE_FEED_MOTOR_ID);
        feedMotor.getConfigurator().apply(new TalonFXConfiguration()); 
        ntDispTab("Intake").add("Intake Feed Rate", this::getFeedRate);
    }
    
    /* Set feed speed of intake motor */
    public void setFeedRate(double speed) {
        feedMotor.set(speed);
    }

    /* Get feed speed of intake motor */
    public double getFeedRate() {
        return feedMotor.get();
    }
}

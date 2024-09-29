package com.team6560.frc2024.subsystems;

import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.team6560.frc2024.Constants;
import com.team6560.frc2024.utility.PID.PIDConfigFuncs;
import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Vertical turret subsystem controls
public class TurretVertical extends SubsystemBase {

    final TalonFX motor;
    final DigitalInput topLimitSwitch;
    final DigitalInput bottomLimitSwitch;

    public TurretVertical() { 
        this.motor = new TalonFX(Constants.CanIDs.TURRET_VERTICAL_MOTOR_ID); 
        this.motor.getConfigurator().apply(new TalonFXConfiguration().withOpenLoopRamps(new OpenLoopRampsConfigs().withVoltageOpenLoopRampPeriod(0.5)));
        this.topLimitSwitch = new DigitalInput(Constants.TurretVertical.UPPER_LIMIT_SWITCH_ID);
        this.bottomLimitSwitch = new DigitalInput(Constants.TurretVertical.LOWER_LIMIT_SWITCH_ID);
        ntDispTab("Turret Vertical")
            .add("Turret Vertical Feed Rate", this::getFeedRate)
            .add("Turret Vertical Angle", this::getTurretAngle)
            .add("Upper limit switch", this::topLimitDown)  
            .add("Lower limit switch", this::bottomLimitDown);        
    }   
    
    /* Set feed speed of motor */
    public void setFeedRate(double speed) {
        motor.set(speed);
    }

    /* Get feed speed of motor */
    public double getFeedRate() {
        return motor.get();
    }

    /* Get position reading from encoder */ 
    public double getTurretAngle() {
        return motor.getRotorPosition().getValueAsDouble() * 360 / Constants.TurretVertical.TURRET_VERTICAL_GEAR_RATIO + 9.02;
    }

    /* Get top limit switch reading */
    public boolean topLimitDown() {
        return !this.topLimitSwitch.get(); 
    }

    /* Get bottom limit switch reading */
    public boolean bottomLimitDown() {
        return !this.bottomLimitSwitch.get();
    }
}

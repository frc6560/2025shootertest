package com.team6560.frc2024.subsystems;

import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
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
    
    private double encoderInitialValue;

    public TurretVertical() { 
        this.motor = new TalonFX(Constants.CanIDs.TURRET_VERTICAL_MOTOR_ID); 
        this.motor.setNeutralMode(NeutralModeValue.Brake);
        this.motor.getConfigurator().apply(new TalonFXConfiguration().withOpenLoopRamps(new OpenLoopRampsConfigs().withVoltageOpenLoopRampPeriod(0.5)));
        PIDConfigFuncs.configurePID(motor, Constants.TurretVertical.TURRET_VERTICAL_PID_PROFILE);
        this.topLimitSwitch = new DigitalInput(Constants.TurretVertical.UPPER_LIMIT_SWITCH_ID);
        this.bottomLimitSwitch = new DigitalInput(Constants.TurretVertical.LOWER_LIMIT_SWITCH_ID);
        this.encoderInitialValue = this.motor.getPosition().getValueAsDouble(); // initial encoder pos in terms of rotations

        ntDispTab("Turret Vertical")
            .add("Turret Vertical Feed Rate", this::getFeedRate)
            .add("Turret Vertical Angle", this::getTurretAngle)
            .add("Upper limit switch", this::topLimitDown)  
            .add("Lower limit switch", this::bottomLimitDown)   
            .add("Upper soft limit", this::getUpperBound)
            .add("Lower soft limit", this::getLowerBound);
    }   

    /* Set feed speed of motor */
    public void setFeedRate(double speed) {
        motor.set(speed);
    }

    /* Get feed speed of motor */
    public double getFeedRate() {
        return motor.getVelocity().getValueAsDouble();
    }

    /* Get position reading from encoder */ 
    public double getTurretAngle() {
        return ((motor.getPosition().getValueAsDouble() * 360) - (this.encoderInitialValue * 360)) / Constants.TurretVertical.TURRET_VERTICAL_GEAR_RATIO + Constants.TurretVertical.TURRET_LOWER_HARD_LIMIT;
    }

    /* Set turret to certain position */
    public void setTurretAngle(double angle) {
        if (angle < Constants.TurretVertical.TURRET_LOWER_HARD_LIMIT || angle > Constants.TurretVertical.TURRET_UPPER_HARD_LIMIT) {
            motor.setPosition(((angle / 360) * Constants.TurretVertical.TURRET_VERTICAL_GEAR_RATIO) + this.encoderInitialValue);
        }
    }

    /* Get top limit switch reading */
    public boolean topLimitDown() {
        return !this.topLimitSwitch.get();
    }

    /* Get bottom limit switch reading */
    public boolean bottomLimitDown() {
        if (!this.bottomLimitSwitch.get()) {
            this.encoderInitialValue = this.motor.getPosition().getValueAsDouble();
            return true;
        }
        return false;
    }

    /* show lower bound on ntTable */
    public double getLowerBound() {
        return Constants.TurretVertical.TURRET_LOWER_SOFT_LIMIT;
    }

    /* show upper bound on ntTable */
    public double getUpperBound() {
        return Constants.TurretVertical.TURRET_UPPER_SOFT_LIMIT;
    }
}

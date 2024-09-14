package com.team6560.frc2024.subsystems;

import com.team6560.frc2024.Constants;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase{
    DigitalInput LimitSwitch = new DigitalInput(Constants.LIMIT_SWITCH_PORT_ID);
    private CANSparkFlex TurretMotor;
    public Turret() {
        TurretMotor = new CANSparkFlex(Constants.CanIDs.TURRET_MOTOR_ID, MotorType.kBrushless);
        //device id is placeholder
    }

    public void setTurretSpeed(double turretSpeed){
        TurretMotor.set(turretSpeed);
    }

    public void stopTurretLimSwitch(){
        if (LimitSwitch.get()) {
            setTurretSpeed(0.0);
        }
    }

    public double getTurretVelocity(){
        return TurretMotor.getEncoder().getVelocity();
    }

    public double getTurretPosition(){
        return TurretMotor.getEncoder().getPosition();  
    }

    public void setTurretHome(){
        TurretMotor.getEncoder().setPosition(0.0);
    }

    public void setTurretFeedPos(){
        TurretMotor.getEncoder().setPosition(4.0);
    }
    
}

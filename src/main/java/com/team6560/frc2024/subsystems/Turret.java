package com.team6560.frc2024.subsystems;

import com.team6560.frc2024.Constants;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class Turret extends SubsystemBase{
    DigitalInput LimitSwitch = new DigitalInput(Constants.LIMIT_SWITCH_PORT_ID);
    public CANSparkFlex TurretMotor;
    private Turret() {
        TurretMotor = new CANSparkFlex(100, MotorType.kBrushless);
        //device id is placeholder

    }

    public void setTurretSpeed(double turretSpeed){
        TurretMotor.set(turretSpeed);
    }
    

    public void getLimitSwitch(){
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
}

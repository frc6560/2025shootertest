package com.team6560.frc2024.subsystems;

import com.team6560.frc2024.Constants;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase{
    public CANSparkFlex TurretMotor;
    private Turret() {
        TurretMotor = new CANSparkFlex(100, MotorType.kBrushless);
        //device id is placeholder

    }
    public LimitSwitchPlaceholder LimitSwitch;
    

    public double getTurretVelocity(){
        return TurretMotor.getEncoder().getVelocity();
    }
    public double getTurretPosition(){
        return TurretMotor.getEncoder().getPosition();  
    }
}

package com.team6560.frc2024.subsystems;

import com.team6560.frc2024.controls.ManualControls;

import com.team6560.frc2024.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase{
    DigitalInput LimitSwitch = new DigitalInput(Constants.LIMIT_SWITCH_PORT_ID);
    public CANSparkMax TurretMotor;

    public Turret() {
        TurretMotor = new CANSparkMax(Constants.CanIDs.TURRET_MOTOR_ID, MotorType.kBrushless);
        //device id is placeholder
    }

    public void setTurretSpeed(double turretSpeed){
        TurretMotor.set(turretSpeed);
    }

    public void stopTurret(){
        setTurretSpeed(0.0);
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

    public void setTurretPosition(double TargetPosition){
        double TurretPosition = getTurretPosition();
        if (Constants.Turret.minang <= TurretPosition && TurretPosition <= Constants.Turret.maxang){
            while (TurretPosition < TargetPosition){
                setTurretSpeed(Constants.Turret.turretSpeed);
            }
            while (TurretPosition > TargetPosition){
                setTurretSpeed(-(Constants.Turret.turretSpeed));
            }
            stopTurret();
        }
        else {
            System.out.println("Inaccessible Target");
        }
    }
    
}

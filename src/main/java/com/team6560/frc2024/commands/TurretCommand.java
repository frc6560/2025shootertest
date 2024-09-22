package com.team6560.frc2024.commands;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.Command;

public class TurretCommand extends Command {
    private Turret Turret;
    private ManualControls controls;

    public void initialize() {
        Turret.stopTurret();
    }

    public void periodic(){
        Turret.stopTurretLimSwitch();

        if (controls.initTurretHome()) {
            Turret.setTurretPosition(Constants.Turret.Home);
        } 
        else if (controls.initTurretFeedPos()) {
            Turret.setTurretPosition(Constants.Turret.FeedPos);
        }
    }

    public boolean isFinished() {
        return false;
    }
}
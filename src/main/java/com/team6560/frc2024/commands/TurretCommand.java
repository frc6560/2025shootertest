package com.team6560.frc2024.commands;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.Command;

public class TurretCommand extends Command {
    private Turret Turret;
    private ManualControls controls;

    public void initialize() {
        Turret.setTurretSpeed(0.0);
    }
    public void execute() {
        if (controls.initTurretHome()) {
            Turret.setTurretHome();
        } 
        else if (controls.initTurretFeedPos()) {
            Turret.setTurretFeedPos();
        }
    }

    public boolean isFinished() {
        return false;
    }
}
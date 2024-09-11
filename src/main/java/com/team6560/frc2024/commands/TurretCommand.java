package com.team6560.frc2024.commands;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.Command;

public class TurretCommand extends Command {
    private Turret Turret;
    private ManualControls controls;


public void initialize() {

}

public void execute() {
    if (controls.setTurretHome()) {
        Turret.setTurretSpeed(Constants.Turret.TURRET_HOME);
    } 
    else if (controls.setTurretFeedPos()) {
        Turret.setTurretSpeed(Constants.Turret.TURRET_FEED_POS);
    }
    
}

public boolean isFinished() {
    return false;
}




}
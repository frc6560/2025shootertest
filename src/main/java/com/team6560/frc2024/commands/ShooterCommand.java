package com.team6560.frc2024.commands;

import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Shooter;
import com.team6560.lib.commands.GenericRollerCommand;

public class ShooterCommand extends GenericRollerCommand{

    public ShooterCommand(Shooter shooter, ManualControls controls) {
        super(shooter, (Void) -> controls.getRunShooter());
    }

}

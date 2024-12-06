package com.team6560.frc2024.commands;

import com.team6560.lib.commands.GenericDriveCommand;
import com.team6560.frc2024.subsystems.Drivetrain;
import com.team6560.frc2024.controls.ManualControls;

public class DriveCommand extends GenericDriveCommand {
    
    public DriveCommand(Drivetrain drivetrain, ManualControls controls) {
        super(drivetrain, controls);
    }
}

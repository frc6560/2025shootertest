package com.team6560.frc2024.commands;

import com.team6560.lib.commands.GenericRollerCommand;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Intake;

public class IntakeCommand extends GenericRollerCommand {
    
    public IntakeCommand(Intake intake, ManualControls controls) {
        super(intake, (Void) -> controls.getRunIntake(), (Void) -> controls.getReverseIntake());
    }

}

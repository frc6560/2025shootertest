package com.team6560.frc2024.commands;

import com.team6560.lib.commands.GenericRollerCommand;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Amp;

public class AmpCommand extends GenericRollerCommand {
    
    public AmpCommand(Amp amp, ManualControls controls) {
        super(amp, (Void) -> controls.getRunAmp());
    }

}

package com.team6560.frc2024.commands;

import com.team6560.frc2024.Constants;
import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;

public class IntakeCommand extends Command {
    final Intake intake;
    final ManualControls controls;

    /* Simple command to run or stop intake depending on controls activated. No PID. */
    public IntakeCommand(Intake intake, ManualControls controls) {
        this.intake = intake;
        this.controls = controls;
        addRequirements(intake);
    }

    /* Makes sure intake is turned off at initialization. */
    @Override
    public void initialize() {
        intake.setFeedRate(0.0);
    }

    /* Set intake feed rate depending on button pressed. Reverse is checked first in case of jammed notes.  */
    @Override
    public void execute() {
        if (controls.getReverseIntake()) {
            intake.setFeedRate(Constants.Intake.INTAKE_REVERSE_RATE);
        } else if (controls.getRunIntake()) {
            intake.setFeedRate(Constants.Intake.INTAKE_FEED_RATE);
        } else {
            intake.setFeedRate(0.0);
        }
    }

    /* Stops intake when command ends. */
    @Override
    public void end(boolean interrupted) {
      intake.setFeedRate(0.0);
    }

    /* Command always executes (speed is set to zero if no input) */
    @Override
    public boolean isFinished() {
      return false;
    }
}

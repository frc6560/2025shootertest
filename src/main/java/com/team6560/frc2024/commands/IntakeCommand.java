package com.team6560.frc2024.commands;

import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;

public class IntakeCommand extends Command {
    final Intake intake;
    final ManualControls controls;

    public IntakeCommand(Intake intake, ManualControls controls) {
        this.intake = intake;
        this.controls = controls;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.stop();
    }

    @Override
    public void execute() {
        if (controls.getReverseIntake() && !intake.gamePieceIn()) {
            intake.outtake();
        } else if (controls.getRunIntake() && !intake.gamePieceIn()) {
            intake.intake();
        } else {
            intake.stop();
        }
    }

    @Override
    public void end(boolean interrupted) {
      intake.stop();
    }

    @Override
    public boolean isFinished() {
      return false;
    }
}

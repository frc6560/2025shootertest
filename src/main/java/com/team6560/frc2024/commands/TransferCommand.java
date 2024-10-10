package com.team6560.frc2024.commands;

import com.team6560.frc2024.controls.ManualControls;
import com.team6560.frc2024.subsystems.Transfer;
import edu.wpi.first.wpilibj2.command.Command;

public class TransferCommand extends Command {
  final Transfer transfer;
  final ManualControls controls;

  public TransferCommand(Transfer transfer, ManualControls controls) {
    this.transfer = transfer;
    this.controls = controls;
    addRequirements(transfer);
  }

  @Override
  public void initialize() {
    transfer.stop();
  }

  @Override
  public void execute() {
    if (controls.getRunTransfer()) {
      transfer.run();
    } else {
      transfer.stop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    transfer.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

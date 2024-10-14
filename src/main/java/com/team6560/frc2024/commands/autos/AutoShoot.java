package com.team6560.frc2024.commands.autos;

import com.team6560.frc2024.subsystems.Hood;
import com.team6560.frc2024.subsystems.Shooter;
import com.team6560.frc2024.subsystems.Transfer;
import com.team6560.frc2024.subsystems.Turret;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoShoot extends Command {

  final Hood hood;
  final Shooter shooter;
  final Transfer transfer;
  final Turret turret;
  private double startTime;
  private boolean timerStarted;

  public AutoShoot(Hood hood, Shooter shooter, Transfer transfer, Turret turret) {
    this.hood = hood;
    this.shooter = shooter;
    this.transfer = transfer;
    this.turret = turret;
    this.startTime = Timer.getFPGATimestamp();
    addRequirements(hood, shooter, transfer);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (!this.timerStarted) {
        this.startTime = Timer.getFPGATimestamp();
        this.timerStarted = true;
    } 
    while (Timer.getFPGATimestamp() < startTime + 7) {
      double currTime = Timer.getFPGATimestamp() - startTime;
      turret.setZeroAngle();
      hood.setShootAngle();
      shooter.run();
      if (currTime > 4) {
        transfer.run();
      }
    }

    shooter.run();
    transfer.run();
  }

  @Override
  public void end(boolean interrupted) {
    turret.setZeroAngle();
    shooter.stop();
    transfer.stop();
    hood.drop();
  }

  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() > startTime + 7;
  }
}

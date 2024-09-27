// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6560.frc2024;

// constants
// import com.team6560.frc2024.Constants;

// commands
import com.team6560.frc2024.commands.DriveCommand;
import com.team6560.frc2024.commands.IntakeCommand;
import com.team6560.frc2024.commands.FeederCommand;
import com.team6560.frc2024.commands.ShooterCommand;
import com.team6560.frc2024.commands.TurretVerticalCommand;
import com.team6560.frc2024.commands.TurretHorizontalCommand;
// subsystems
import com.team6560.frc2024.subsystems.Drivetrain;
import com.team6560.frc2024.subsystems.Intake;
import com.team6560.frc2024.subsystems.Feeder;
import com.team6560.frc2024.subsystems.Shooter;
import com.team6560.frc2024.subsystems.TurretVertical;
import com.team6560.frc2024.subsystems.TurretHorizontal;

// controls 
import com.team6560.frc2024.controls.ManualControls;

// wpilib imports
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
  // Subsystems
  // private final Drivetrain drivetrain;
  private final Intake intake;
  private final Feeder feeder;
  private final Shooter shooter;
  private final TurretHorizontal turretHorizontal;
  private final TurretVertical turretVertical;

  // Commands
  // private final DriveCommand driveCommand;
  private final IntakeCommand intakeCommand;
  private final FeederCommand feederCommand;
  private final ShooterCommand shooterCommand;
  private final TurretHorizontalCommand turretHorizontalCommand;
  private final TurretVerticalCommand turretVerticalCommand;

  private final ManualControls manualControls = new ManualControls(new XboxController(0), new XboxController(1));

  private final SendableChooser<Command> autoChooser;

  public RobotContainer() {

      /* 
      drivetrain = new Drivetrain();
      driveCommand = new DriveCommand(drivetrain, manualControls);
      drivetrain.setDefaultCommand(driveCommand);
      */

      intake = new Intake();
      intakeCommand = new IntakeCommand(intake, manualControls);
      intake.setDefaultCommand(intakeCommand);

      feeder = new Feeder();
      feederCommand = new FeederCommand(feeder, manualControls);
      feeder.setDefaultCommand(feederCommand);

      shooter = new Shooter();
      shooterCommand = new ShooterCommand(shooter, manualControls);
      shooter.setDefaultCommand(shooterCommand);

      turretHorizontal = new TurretHorizontal();
      turretHorizontalCommand = new TurretHorizontalCommand(turretHorizontal, manualControls);
      turretHorizontal.setDefaultCommand(turretHorizontalCommand);

      turretVertical = new TurretVertical();
      turretVerticalCommand = new TurretVerticalCommand(turretVertical, manualControls);
      turretVertical.setDefaultCommand(turretVerticalCommand);

      autoChooser = new SendableChooser<Command>();
      SmartDashboard.putData("Auto Mode", autoChooser);
      SmartDashboard.putNumber("Auto Delay", 0);
  }

  // null to not kill anyone
  public Command getAutonomousCommand() {
    return null;
  }
}

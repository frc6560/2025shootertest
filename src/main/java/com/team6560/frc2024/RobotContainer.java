// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6560.frc2024;

// constants
// import com.team6560.frc2024.Constants;

// commands
import com.team6560.frc2024.commands.DriveCommand;
import com.team6560.frc2024.commands.IntakeCommand;
import com.team6560.frc2024.commands.TransferCommand;
import com.team6560.frc2024.commands.ShooterCommand;
import com.team6560.frc2024.commands.HoodCommand;
import com.team6560.frc2024.commands.TurretCommand;

// subsystems
import com.team6560.frc2024.subsystems.Drivetrain;
import com.team6560.frc2024.subsystems.Intake;
import com.team6560.frc2024.subsystems.Transfer;
import com.team6560.frc2024.subsystems.Shooter;
import com.team6560.frc2024.subsystems.Hood;
import com.team6560.frc2024.subsystems.Turret;

// controls 
import com.team6560.frc2024.controls.ManualControls;

// wpilib imports
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
  
  // Subsystems
  private final Drivetrain drivetrain;
  private final Intake intake;
  private final Transfer feeder;
  private final Shooter shooter;
  private final Turret turret;
  private final Hood hood;

  // Commands
  private final DriveCommand driveCommand;
  private final IntakeCommand intakeCommand;
  private final TransferCommand feederCommand;
  private final ShooterCommand shooterCommand;
  private final TurretCommand turretHorizontal;
  private final HoodCommand hoodCommand;

  private final ManualControls manualControls = new ManualControls(new XboxController(0), new XboxController(1));

  private final SendableChooser<Command> autoChooser;

  public RobotContainer() {
      
      drivetrain = new Drivetrain();
      driveCommand = new DriveCommand(drivetrain, manualControls);
      drivetrain.setDefaultCommand(driveCommand);

      intake = new Intake();
      intakeCommand = new IntakeCommand(intake, manualControls);
      intake.setDefaultCommand(intakeCommand);

      feeder = new Transfer();
      feederCommand = new TransferCommand(feeder, manualControls);
      feeder.setDefaultCommand(feederCommand);

      shooter = new Shooter();
      shooterCommand = new ShooterCommand(shooter, manualControls);
      shooter.setDefaultCommand(shooterCommand);

      turret = new Turret();
      turretHorizontal = new TurretCommand(turret, manualControls);
      turret.setDefaultCommand(turretHorizontal);

      hood = new Hood();
      hoodCommand = new HoodCommand(hood, manualControls);
      hood.setDefaultCommand(hoodCommand);

      autoChooser = new SendableChooser<Command>();
      SmartDashboard.putData("Auto Mode", autoChooser);
      SmartDashboard.putNumber("Auto Delay", 0);
  }

  // null to not kill anyone
  public Command getAutonomousCommand() {
    return null;
  }
}

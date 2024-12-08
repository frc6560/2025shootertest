// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6560.frc2024;

import com.team6560.lib.controls.DriveControlsConfig;

import com.team6560.frc2024.controls.ManualControls;

import com.team6560.frc2024.subsystems.Drivetrain;
import com.team6560.frc2024.commands.DriveCommand;

import com.team6560.frc2024.subsystems.Intake;
import com.team6560.frc2024.commands.IntakeCommand;

import com.team6560.frc2024.subsystems.Shooter;
import com.team6560.frc2024.commands.ShooterCommand;

import com.team6560.frc2024.subsystems.Transfer;
import com.team6560.frc2024.commands.TransferCommand;

import com.team6560.frc2024.subsystems.Amp;
import com.team6560.frc2024.commands.AmpCommand;

import com.team6560.frc2024.subsystems.TestDigitalInputSensor;
import com.team6560.frc2024.subsystems.TestSparkMaxMotor;
import com.team6560.frc2024.subsystems.TestTalonFXMotor;

import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.wpilibj2.command.WaitCommand;

// wpilib imports
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  private Drivetrain drivetrain;
  private DriveCommand driveCommand;

  /* 
  private Intake intake;
  private IntakeCommand intakeCommand;

  private Shooter shooter;
  private ShooterCommand shooterCommand;

  private Transfer transfer;
  private TransferCommand transferCommand;

  private Amp amp;
  private AmpCommand ampCommand;
  */

  // private TestDigitalInputSensor testDigitalInputSensor;
  // private TestSparkMaxMotor testSparkMaxMotor;
  // private TestTalonFXMotor testTalonFXMotor;

  private final SendableChooser<Command> autoChooser;

  private final ManualControls manualControls = new ManualControls(
    new XboxController(0), 
    new XboxController(1), 
    new DriveControlsConfig.Builder()
    .setMaxVelocity(Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND)
    .setMaxANgularVelocity(Constants.Drivetrain.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND)
    .setSpeedInitialPercent(0.5) 
    .setSpeedMinPercent(0.1)
    .setSpeedMaxPercent(0.9)
    .setSpeedStepPercent(0.05) 
    .setTurnSpeedInitialPercent(0.2)
    .setTurnSpeedMinPercent(0.1) 
    .setTurnSpeedMaxPercent(0.8) 
    .setTurnSpeedStepPercent(0.025) 
    .setDeadband(0.1) 
    .setReverseX(false) 
    .setReverseY(false) 
    .build()
  );

  public RobotContainer() {

      drivetrain = new Drivetrain();
      driveCommand = new DriveCommand(drivetrain, manualControls);
      drivetrain.setDefaultCommand(driveCommand);

      /* 

      intake = new Intake();
      intakeCommand = new IntakeCommand(intake, manualControls);
      intake.setDefaultCommand(intakeCommand);

      shooter = new Shooter();
      shooterCommand = new ShooterCommand(shooter, manualControls);
      shooter.setDefaultCommand(shooterCommand);

      transfer = new Transfer();
      transferCommand = new TransferCommand(transfer, manualControls);
      transfer.setDefaultCommand(transferCommand);

      amp = new Amp();
      ampCommand = new AmpCommand(amp, manualControls);
      amp.setDefaultCommand(ampCommand);

      */

      // testDigitalInputSensor = new TestDigitalInputSensor();
      // testTalonFXMotor = new TestTalonFXMotor();
      // testSparkMaxMotor = new TestSparkMaxMotor();

      autoChooser = new SendableChooser<Command>();
      autoChooser.addOption("No Auto", null);
      autoChooser.addOption("Calibration", calibration());
      SmartDashboard.putData("Auto Mode", autoChooser);
      SmartDashboard.putNumber("Auto Delay", 0);
  }

  public Command getAutonomousCommand() {
    return null;
  }

  public Command calibration(){
    return (new PathPlannerAuto("cali1"))
    .andThen(new WaitCommand(3))
    .andThen(new PathPlannerAuto("cali2"));
  }

}

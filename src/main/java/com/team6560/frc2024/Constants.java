// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6560.frc2024;

import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import com.swervedrivespecialties.swervelib.MotorType;
import com.pathplanner.lib.util.GeometryUtil;
import com.revrobotics.SparkPIDController.AccelStrategy;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

import com.team6560.frc2024.utility.PID.TalonFXPIDConfigProfile;
import com.team6560.frc2024.utility.PID.SparkPIDConfigProfile;

/* Contains all constants, defined in subclasses. */
public final class Constants {

  /* Universal constants */
  public static class Global {
    
    public static final double MAX_VOLTAGE = 12.0;

    // Current alliance

    public static final Alliance ALLIANCE = DriverStation.getAlliance().orElse(DriverStation.Alliance.Red);
    public static final boolean IS_RED_ALLIANCE = (ALLIANCE == DriverStation.Alliance.Red);

  }

  /* Contains CAN IDs for all electrical components. Lower IDs are prioritized. */
  public static class CanIDs {

    // Drivetrain
    // 1-4: Motor encoders
    // 5-8: Drive motors
    // 9-12: Steer motors
    // 13: Gyro

    public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR_ID = 5;
    public static final int FRONT_LEFT_MODULE_STEER_MOTOR_ID = 9;
    public static final int FRONT_LEFT_MODULE_STEER_ENCODER_ID = 1;

    public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR_ID = 8;
    public static final int FRONT_RIGHT_MODULE_STEER_MOTOR_ID = 12;
    public static final int FRONT_RIGHT_MODULE_STEER_ENCODER_ID = 4;

    public static final int BACK_LEFT_MODULE_DRIVE_MOTOR_ID = 6;
    public static final int BACK_LEFT_MODULE_STEER_MOTOR_ID = 10;
    public static final int BACK_LEFT_MODULE_STEER_ENCODER_ID = 2;

    public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR_ID = 7;
    public static final int BACK_RIGHT_MODULE_STEER_MOTOR_ID = 11;
    public static final int BACK_RIGHT_MODULE_STEER_ENCODER_ID = 3;

    public static final int GYRO_ID = 14;

    // Intake
    // 14: Transfer motor
    // 20: Feed motor

    public static final int TRANSFER_MOTOR_ID = 19;
    public static final int INTAKE_FEED_MOTOR_ID = 20;

    public static final int TURRET_HORIZONTAL_MOTOR_ID = 13; // same as gyro - fix!

    // NOT IMPLEMENTED

    public static final int FEEDER_MOTOR_ID = 23;

    public static final int SHOOTER_MOTOR_ONE_ID = 15;
    public static final int SHOOTER_MOTOR_TWO_ID = 16;
    public static final int SHOOTER_MOTOR_THREE_ID = 17;

    public static final int TURRET_VERTICAL_MOTOR_ID = 18;
  }

  /* Constants for drivetrain dimensions, offsets, speeds etc. */
  public static class Drivetrain {

    // Modules enum

    public enum SwerveModuleIndex {
      FRONT_LEFT,
      FRONT_RIGHT,
      BACK_LEFT,
      BACK_RIGHT;
    }

    // Motors

    public static final MotorType DRIVE_MOTOR_TYPE = MotorType.FALCON;
    public static final MotorType STEER_MOTOR_TYPE = MotorType.NEO;

    // Drivetrain dimensions

    public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.57785;
    public static final double DRIVETRAIN_WHEELBASE_METERS = 0.57785;    

    // Swerve kinematics

    public static final SwerveDriveKinematics M_KINEMATICS = new SwerveDriveKinematics(
      new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0), // Front left
      new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0), // Front right
      new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0), // Back left
      new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0)  // Back right
    );

    // Max velocities

    public static final double MAX_VELOCITY_METERS_PER_SECOND = 6380.0 / 60.0 *
      SdsModuleConfigurations.MK4I_L2.getDriveReduction() *
      SdsModuleConfigurations.MK4I_L2.getWheelDiameter() * Math.PI;

    public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND /
      Math.hypot(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0);

    // Steer offsets

    public static final double FRONT_LEFT_MODULE_STEER_OFFSET = -6.5039;
    public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -72.4218;
    public static final double BACK_LEFT_MODULE_STEER_OFFSET = -76.2011; 
    public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -177.1875;
  }

  /* Gyro and odometry constants */
  public static class Odometry {

    public static final Rotation2d DEFAULT_ROTATION_RED_ALLIANCE = new Rotation2d(Math.PI);
    public static final Rotation2d DEFAULT_ROTATION_BLUE_ALLIANCE = new Rotation2d();

    public static final Pose2d DEFAULT_POSE_RED_ALLIANCE = GeometryUtil.flipFieldPose(new Pose2d());
    public static final Pose2d DEFAULT_POSE_BLUE_ALLLIANCE = new Pose2d();

  }

  /* Constants for controlling intake functionality. */
  public static class Intake {

    public static final double INTAKE_FEED_RATE = 0.6;
    public static final double INTAKE_REVERSE_RATE = -0.3;

  }  

  /* Shooter feeder constants */
  public static class Feeder {

    public static final double FEEDER_FEED_RATE = 1.0;
    public static final double FEEDER_REVERSE_RATE = -1.0;

    public static Boolean GamePieceIn = False;
    
  }

  /* Constants for controlling shooter functionality */
  public static class Shooter {

    public static final double SHOOTER_FEED_RATE = -1.0;
    public static final double SHOOTER_REVERSE_RATE = 0.8;

    public static final TalonFXPIDConfigProfile SHOOTER_PID_PROFILE = new TalonFXPIDConfigProfile(
      0, 0, 0
    );

  }

  public static class TurretVertical {

    public static final double TURRET_VERTICAL_FEED_RATE = 0.5;
    public static final double TURRET_VERTICAL_REVERSE_RATE = -0.5;

    public static final double TURRET_VERTICAL_GEAR_RATIO = 25 * 360 / 40;

    public static final int UPPER_LIMIT_SWITCH_ID = 9;
    public static final int LOWER_LIMIT_SWITCH_ID = 8;
  }

  public static class TurretHorizontal {

    public static final double TURRET_HORIZONTAL_RATE = 0.3;

    public static final double TURRET_HORIZONTAL_GEAR_RATIO = 5 * 400 / 46;

    // not sure which is left and right
    public static final int LEFT_LIMIT_SWITCH_ID = 6; 
    public static final int RIGHT_LIMIT_SWITCH_ID = 7;
  }

  /* Constants related to controller functionality, input etc. */
  public static class Controller {

    public static final double SPEED_MIN_PERCENT = 0.0;
    public static final double SPEED_INITIAL_PERCENT = 0.4;
    public static final double SPEED_MAX_PERCENT = 0.6;
    public static final double SPEED_STEP_PERCENT = .025;

    public static final double TURN_SPEED_MIN_PERCENT = 0.0;
    public static final double TURN_SPEED_INITIAL_PERCENT = 0.175;
    public static final double TURN_SPEED_MAX_PERCENT = 0.3;
    public static final double TURN_SPEED_STEP_PERCENT = .0025;

    public static final double CONTROLLER_DEADBAND = 0.1;

  }
}

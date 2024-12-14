package com.team6560.frc2024;

import com.swervedrivespecialties.swervelib.MotorType;
import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import com.pathplanner.lib.util.GeometryUtil;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

/* Contains all constants, defined in subclasses. */
public final class Constants {

  /* Universal constants */
  public static class Global {
    
    public static final double MAX_VOLTAGE = 12.0;

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

    // Other subsystems

    public static final int TURRET_MOTOR_ID = 13;

    public static final int SHOOTER_MOTOR_ONE_ID = 15;
    public static final int SHOOTER_MOTOR_TWO_ID = 16;
    public static final int SHOOTER_MOTOR_THREE_ID = 17;

    public static final int HOOD_MOTOR_ID = 18;

    public static final int INTAKE_TRANSFER_MOTOR_ID = 19;
    public static final int INTAKE_FEED_MOTOR_ID = 20;

    public static final int TRANSFER_MOTOR_ID = 23;

    public static final int AMP_MOTOR_ID = 24;

  }

  /* Constants for drivetrain dimensions, offsets, speeds etc. */
  public static class Drivetrain {

    // Motors

    public static final MotorType DRIVE_MOTOR = MotorType.FALCON;
    public static final MotorType STEER_MOTOR = MotorType.NEO;

    // Drivetrain dimensions

    public static final double TRACKWIDTH_M = 0.57785;
    public static final double WHEELBASE_M = 0.57785;    

    // Steer offsets

    public static final double FL_STEER_OFFSET = 184.83; 
    public static final double FR_STEER_OFFSET = 255.56; 
    public static final double BL_STEER_OFFSET = 256.35; 
    public static final double BR_STEER_OFFSET = 354.02; 

    // PathPlanner config

    // Max velocities (required for controller in addition to drivetrain)

    public static final double MAX_VELOCITY_METERS_PER_SECOND = 6380.0 / 60.0 *
      SdsModuleConfigurations.MK4I_L2.getDriveReduction() *
      SdsModuleConfigurations.MK4I_L2.getWheelDiameter() * Math.PI;

    public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND /
      Math.hypot(TRACKWIDTH_M / 2.0, WHEELBASE_M / 2.0);

  }

  /* Gyro and odometry constants */
  public static class Odometry {

    public static final Rotation2d DEFAULT_ROTATION_RED_ALLIANCE = new Rotation2d();
    public static final Rotation2d DEFAULT_ROTATION_BLUE_ALLIANCE = new Rotation2d();

    public static final Pose2d DEFAULT_POSE_RED_ALLIANCE = new Pose2d();
    public static final Pose2d DEFAULT_POSE_BLUE_ALLIANCE = new Pose2d();

  }

}

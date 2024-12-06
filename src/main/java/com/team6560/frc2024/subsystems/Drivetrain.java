package com.team6560.frc2024.subsystems;

import com.team6560.frc2024.Constants;
import com.team6560.lib.subsystems.drivetrain.GenericSwerve;
import com.team6560.lib.subsystems.drivetrain.SwerveConfig;
import com.team6560.lib.subsystems.drivetrain.SwerveOffsets;

public class Drivetrain extends GenericSwerve {

    public Drivetrain() {
        super(new SwerveConfig.Builder()
        .setOffsets(
            new SwerveOffsets(
                Constants.Drivetrain.FL_STEER_OFFSET, 
                Constants.Drivetrain.FR_STEER_OFFSET, 
                Constants.Drivetrain.BL_STEER_OFFSET, 
                Constants.Drivetrain.BR_STEER_OFFSET
            )
        )
        .setSteerMotorType(Constants.Drivetrain.STEER_MOTOR)
        .setDriveMotorType(Constants.Drivetrain.DRIVE_MOTOR)
        .setTrackwidth(Constants.Drivetrain.TRACKWIDTH_M)
        .setWheelbase(Constants.Drivetrain.WHEELBASE_M)
        .setFLSteerCanID(Constants.CanIDs.FRONT_LEFT_MODULE_STEER_MOTOR_ID)
        .setFLDriveCanID(Constants.CanIDs.FRONT_LEFT_MODULE_DRIVE_MOTOR_ID)
        .setFLEncoderCanID(Constants.CanIDs.FRONT_LEFT_MODULE_STEER_ENCODER_ID)
        .setFRSteerCanID(Constants.CanIDs.FRONT_RIGHT_MODULE_STEER_MOTOR_ID)
        .setFRDriveCanID(Constants.CanIDs.FRONT_RIGHT_MODULE_DRIVE_MOTOR_ID)
        .setFREncoderCanID(Constants.CanIDs.FRONT_RIGHT_MODULE_STEER_ENCODER_ID)
        .setBLSteerCanID(Constants.CanIDs.BACK_LEFT_MODULE_STEER_MOTOR_ID)
        .setBLDriveCanID(Constants.CanIDs.BACK_LEFT_MODULE_DRIVE_MOTOR_ID)
        .setBLEncoderCanID(Constants.CanIDs.BACK_LEFT_MODULE_STEER_ENCODER_ID)
        .setBRSteerCanID(Constants.CanIDs.BACK_RIGHT_MODULE_STEER_MOTOR_ID)
        .setBRDriveCanID(Constants.CanIDs.BACK_RIGHT_MODULE_DRIVE_MOTOR_ID)
        .setBREncoderCanID(Constants.CanIDs.BACK_RIGHT_MODULE_STEER_ENCODER_ID)
        .setDefaultPoseRedAlliance(Constants.Odometry.DEFAULT_POSE_RED_ALLIANCE)
        .setGyroID(Constants.CanIDs.GYRO_ID)
        .setDefaultPoseBlueAlliance(Constants.Odometry.DEFAULT_POSE_BLUE_ALLIANCE)
        .setDefaultRotationRedAlliance(Constants.Odometry.DEFAULT_ROTATION_RED_ALLIANCE)
        .setDefaultRotationBlueAlliance(Constants.Odometry.DEFAULT_ROTATION_BLUE_ALLIANCE)
        .setPathFollowerConfig(Constants.Drivetrain.PATH_FOLLOWER_CONFIG)
        .build(),
        Constants.Global.MAX_VOLTAGE
        );

    }

}

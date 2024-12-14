package com.team6560.lib.subsystems.drivetrain;

import com.swervedrivespecialties.swervelib.MotorType;
import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import com.pathplanner.lib.util.GeometryUtil;

/**
 * Config class for encapsulating all swerve drivetrain parameters.
 */
public class SwerveConfig {

    public final int FLSteerCanID;
    public final int FLDriveCanID;
    public final int FLEncoderCanID;
    public final int FRSteerCanID;
    public final int FRDriveCanID;
    public final int FREncoderCanID;
    public final int BLSteerCanID;
    public final int BLDriveCanID;
    public final int BLEncoderCanID;
    public final int BRSteerCanID;
    public final int BRDriveCanID;
    public final int BREncoderCanID;
    public final int gyroID;
    public final MotorType steerMotorType;
    public final MotorType driveMotorType;
    public final double trackwidth;
    public final double wheelbase;
    public final double maxVelocity; // m/s
    public final double maxAngularVelocity; // rad/s
    public final SwerveOffsets offsets;

    private SwerveDriveKinematics kinematics;

    // New variables for rotation and pose for both alliances
    public final Rotation2d defaultRotationRedAlliance;
    public final Rotation2d defaultRotationBlueAlliance;
    public final Pose2d defaultPoseRedAlliance;
    public final Pose2d defaultPoseBlueAlliance;

    /**
     * Private constructor to enforce usage of builder.
     * @param builder Builder to initialize class.
     */
    private SwerveConfig(Builder builder) {
        this.FLSteerCanID = builder.FLSteerCanID;
        this.FLDriveCanID = builder.FLDriveCanID;
        this.FLEncoderCanID = builder.FLEncoderCanID;
        this.FRSteerCanID = builder.FRSteerCanID;
        this.FRDriveCanID = builder.FRDriveCanID;
        this.FREncoderCanID = builder.FREncoderCanID;
        this.BLSteerCanID = builder.BLSteerCanID;
        this.BLDriveCanID = builder.BLDriveCanID;
        this.BLEncoderCanID = builder.BLEncoderCanID;
        this.BRSteerCanID = builder.BRSteerCanID;
        this.BRDriveCanID = builder.BRDriveCanID;
        this.BREncoderCanID = builder.BREncoderCanID;
        this.gyroID = builder.gyroID;
        this.steerMotorType = builder.steerMotorType;
        this.driveMotorType = builder.driveMotorType;
        this.trackwidth = builder.trackwidth;
        this.wheelbase = builder.wheelbase;
        this.maxVelocity = calculateMaxVelocity();
        this.maxAngularVelocity = calculateMaxAngularVelocity();
        this.offsets = builder.offsets;
        this.defaultRotationRedAlliance = builder.defaultRotationRedAlliance;
        this.defaultRotationBlueAlliance = builder.defaultRotationBlueAlliance;
        this.defaultPoseRedAlliance = builder.defaultPoseRedAlliance;
        this.defaultPoseBlueAlliance = builder.defaultPoseBlueAlliance;

        // Recalculate kinematics whenever the config is built
        this.kinematics = createKinematics();
    }

    private double calculateMaxVelocity() {
        return 6380.0 / 60.0 *
            SdsModuleConfigurations.MK4I_L2.getDriveReduction() *
            SdsModuleConfigurations.MK4I_L2.getWheelDiameter() * Math.PI;
    }

    private double calculateMaxAngularVelocity() {
        return maxVelocity / Math.hypot(trackwidth / 2.0, wheelbase / 2.0);
    }

    /**
     * Method to create kinematics based on trackwidth and wheelbase.
     */
    private SwerveDriveKinematics createKinematics() {
        return new SwerveDriveKinematics(
            new Translation2d(trackwidth / 2.0, wheelbase / 2.0), // Front left
            new Translation2d(trackwidth / 2.0, -wheelbase / 2.0), // Front right
            new Translation2d(-trackwidth / 2.0, wheelbase / 2.0), // Back left
            new Translation2d(-trackwidth / 2.0, -wheelbase / 2.0)  // Back right
        );
    }

    /**
     * Allows for setting specific parameters.
     */
    public static class Builder {

        // Defaults set to 2024 offseason values

        private int FLSteerCanID = 9;
        private int FLDriveCanID = 5;
        private int FLEncoderCanID = 1;
        private int FRSteerCanID = 12;
        private int FRDriveCanID = 8;
        private int FREncoderCanID = 4;
        private int BLSteerCanID = 10;
        private int BLDriveCanID = 6;
        private int BLEncoderCanID = 2;
        private int BRSteerCanID = 11;
        private int BRDriveCanID = 7;
        private int BREncoderCanID = 3;
        private int gyroID = 14;
        private MotorType steerMotorType = MotorType.NEO;
        private MotorType driveMotorType = MotorType.FALCON;
        private double trackwidth = 0.57785;
        private double wheelbase = 0.57785;
        private SwerveOffsets offsets = new SwerveOffsets(0, 0, 0, 0);

        // Default rotation and pose for both alliances

        // private Rotation2d defaultRotationRedAlliance = new Rotation2d(Math.PI);
        // private Rotation2d defaultRotationBlueAlliance = new Rotation2d();
        // private Pose2d defaultPoseRedAlliance = GeometryUtil.flipFieldPose(new Pose2d());
        // private Pose2d defaultPoseBlueAlliance = new Pose2d();


        private Rotation2d defaultRotationRedAlliance = new Rotation2d();
        private Rotation2d defaultRotationBlueAlliance = new Rotation2d();
        private Pose2d defaultPoseRedAlliance = new Pose2d();
        private Pose2d defaultPoseBlueAlliance = new Pose2d();

        // Change config if using auto

        public Builder setFLSteerCanID(int id) { this.FLSteerCanID = id; return this; }
        public Builder setFLDriveCanID(int id) { this.FLDriveCanID = id; return this; }
        public Builder setFLEncoderCanID(int id) { this.FLEncoderCanID = id; return this; }
        public Builder setFRSteerCanID(int id) { this.FRSteerCanID = id; return this; }
        public Builder setFRDriveCanID(int id) { this.FRDriveCanID = id; return this; }
        public Builder setFREncoderCanID(int id) { this.FREncoderCanID = id; return this; }
        public Builder setBLSteerCanID(int id) { this.BLSteerCanID = id; return this; }
        public Builder setBLDriveCanID(int id) { this.BLDriveCanID = id; return this; }
        public Builder setBLEncoderCanID(int id) { this.BLEncoderCanID = id; return this; }
        public Builder setBRSteerCanID(int id) { this.BRSteerCanID = id; return this; }
        public Builder setBRDriveCanID(int id) { this.BRDriveCanID = id; return this; }
        public Builder setBREncoderCanID(int id) { this.BREncoderCanID = id; return this; }
        public Builder setGyroID(int id) { this.gyroID = id; return this; }
        public Builder setSteerMotorType(MotorType type) { this.steerMotorType = type; return this; }
        public Builder setDriveMotorType(MotorType type) { this.driveMotorType = type; return this; }
        public Builder setTrackwidth(double trackwidth) { this.trackwidth = trackwidth; return this; }
        public Builder setWheelbase(double wheelbase) { this.wheelbase = wheelbase; return this; }
        public Builder setOffsets(SwerveOffsets offsets) {this.offsets = offsets; return this; }

        // Setters for the new rotation and pose variables
        public Builder setDefaultRotationRedAlliance(Rotation2d rotation) {
            this.defaultRotationRedAlliance = rotation;
            return this;
        }

        public Builder setDefaultRotationBlueAlliance(Rotation2d rotation) {
            this.defaultRotationBlueAlliance = rotation;
            return this;
        }

        public Builder setDefaultPoseRedAlliance(Pose2d pose) {
            this.defaultPoseRedAlliance = pose;
            return this;
        }

        public Builder setDefaultPoseBlueAlliance(Pose2d pose) {
            this.defaultPoseBlueAlliance = pose;
            return this;
        }

        public SwerveConfig build() {
            return new SwerveConfig(this);
        }
    }

    /* Get drivetrain kinematics object */
    public SwerveDriveKinematics getKinematics() {
        return this.kinematics;
    }

    /* Get rotation and pose for both alliances */
    public Rotation2d getDefaultRotationRedAlliance() {
        return defaultRotationRedAlliance;
    }

    public Rotation2d getDefaultRotationBlueAlliance() {
        return defaultRotationBlueAlliance;
    }

    public Pose2d getDefaultPoseRedAlliance() {
        return defaultPoseRedAlliance;
    }

    public Pose2d getDefaultPoseBlueAlliance() {
        return defaultPoseBlueAlliance;
    }
}

package com.team6560.frc2024.subsystems;

import com.team6560.frc2024.Constants;

// WPI & REV & SYSTEM:
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.swervedrivespecialties.swervelib.MotorType;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.pathplanner.lib.util.GeometryUtil;

// UTIL:
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.swervedrivespecialties.swervelib.MkModuleConfiguration;
import com.swervedrivespecialties.swervelib.MkSwerveModuleBuilder;
import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import com.swervedrivespecialties.swervelib.SwerveModule;

public class Drivetrain extends SubsystemBase {

    private final Pigeon2 pigeon = new Pigeon2(Constants.CanIDs.GYRO_ID);
    private Field2d fieldOnlyOdometry;

    private final SwerveModule m_frontLeftModule;
    private final SwerveModule m_frontRightModule;
    private final SwerveModule m_backLeftModule;
    private final SwerveModule m_backRightModule;

    // Default states for each module correspond to an X shape
    public static final SwerveModuleState[] DEFAULT_MODULE_STATES = new SwerveModuleState[] {
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(45.0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(-45.0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(-45.0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(45.0))
    };

    public SwerveModule[] modules;
    private final SwerveDriveOdometry odometry;

    public Drivetrain() {
        odometry = new SwerveDriveOdometry(Constants.Drivetrain.M_KINEMATICS, getRawGyroRotation(), getModulePositions());

        ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");

        m_frontLeftModule = createSwerveModule(
            tab, 
            "Front Left Module", 
            Constants.CanIDs.FRONT_LEFT_MODULE_DRIVE_MOTOR_ID, 
            Constants.CanIDs.FRONT_LEFT_MODULE_STEER_MOTOR_ID, 
            Constants.CanIDs.FRONT_LEFT_MODULE_STEER_ENCODER_ID, 
            Constants.Drivetrain.FRONT_LEFT_MODULE_STEER_OFFSET
        );

        m_frontRightModule = createSwerveModule(
            tab, 
            "Front Right Module", 
            Constants.CanIDs.FRONT_RIGHT_MODULE_DRIVE_MOTOR_ID, 
            Constants.CanIDs.FRONT_RIGHT_MODULE_STEER_MOTOR_ID, 
            Constants.CanIDs.FRONT_RIGHT_MODULE_STEER_ENCODER_ID, 
            Constants.Drivetrain.FRONT_RIGHT_MODULE_STEER_OFFSET
        );

        m_backLeftModule = createSwerveModule(
            tab, 
            "Back Left Module", 
            Constants.CanIDs.BACK_LEFT_MODULE_DRIVE_MOTOR_ID, 
            Constants.CanIDs.BACK_LEFT_MODULE_STEER_MOTOR_ID, 
            Constants.CanIDs.BACK_LEFT_MODULE_STEER_ENCODER_ID, 
            Constants.Drivetrain.BACK_LEFT_MODULE_STEER_OFFSET
        );

        m_backRightModule = createSwerveModule(
            tab, 
            "Back Right Module", 
            Constants.CanIDs.BACK_RIGHT_MODULE_DRIVE_MOTOR_ID, 
            Constants.CanIDs.BACK_RIGHT_MODULE_STEER_MOTOR_ID, 
            Constants.CanIDs.BACK_RIGHT_MODULE_STEER_ENCODER_ID, 
            Constants.Drivetrain.BACK_RIGHT_MODULE_STEER_OFFSET
        );

        modules = new SwerveModule[] { 
            m_frontLeftModule, 
            m_frontRightModule, 
            m_backLeftModule,
            m_backRightModule 
        };

        var alliance = DriverStation.getAlliance().orElse(DriverStation.Alliance.Red);

        Pose2d initialPose = (alliance == DriverStation.Alliance.Red)
            ? GeometryUtil.flipFieldPose(new Pose2d())
            : new Pose2d();

        resetOdometry(initialPose);
    }

    /* Logic for creating a swerve module */
    private SwerveModule createSwerveModule(ShuffleboardTab tab, String name, int driveMotorId, int steerMotorId, int steerEncoderId, double steerOffset) {
        return new MkSwerveModuleBuilder(MkModuleConfiguration.getDefaultSteerNEO())
            .withLayout(tab.getLayout(name, BuiltInLayouts.kList)
                .withSize(2, 4)
                .withPosition(6, 0)
            )
            .withGearRatio(SdsModuleConfigurations.MK4I_L2)
            .withDriveMotor(MotorType.FALCON, driveMotorId)
            .withSteerMotor(MotorType.NEO, steerMotorId)
            .withSteerEncoderPort(steerEncoderId)
            .withSteerOffset(steerOffset)
            .build();
    }

    public SwerveModule[] getModules() {
        return this.modules;
    }

    @Override
    public void periodic() {
        updateOdometry();
        fieldOnlyOdometry.setRobotPose(getPose());
    }

    // Updates the field-relative position.
    private void updateOdometry() {
        odometry.update(getRawGyroRotation(), getModulePositions());
    }

    // This method is used to control the movement of the chassis.
    public void drive(ChassisSpeeds chassisSpeeds) {
        SwerveModuleState[] speeds = DEFAULT_MODULE_STATES;
        if (chassisSpeeds.vxMetersPerSecond != 0.0 || chassisSpeeds.vyMetersPerSecond != 0.0 || chassisSpeeds.omegaRadiansPerSecond != 0.0) {
            speeds = Constants.Drivetrain.M_KINEMATICS.toSwerveModuleStates(chassisSpeeds);
            SwerveDriveKinematics.desaturateWheelSpeeds(speeds, Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND);
        }
        setChassisState(speeds);
    }

    // Sets the speeds and orientations of each swerve module.
    // array order: front left, front right, back left, back right
    public void setChassisState(SwerveModuleState[] states) {
        m_frontLeftModule.set(
            states[0].speedMetersPerSecond / Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND * Constants.Global.MAX_VOLTAGE,
            states[0].angle.getRadians()
        );
        m_frontRightModule.set(
            states[1].speedMetersPerSecond / Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND * Constants.Global.MAX_VOLTAGE,
            states[1].angle.getRadians()
        );
        m_backLeftModule.set(
            states[2].speedMetersPerSecond / Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND * Constants.Global.MAX_VOLTAGE,
            states[2].angle.getRadians()
        );
        m_backRightModule.set(
            states[3].speedMetersPerSecond / Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND * Constants.Global.MAX_VOLTAGE,
            states[3].angle.getRadians()
        );
    }

    public void setChassisState(double fLdeg, double fRdeg, double bLdeg, double bRdeg) {
        setChassisState(
            new SwerveModuleState[] {
                new SwerveModuleState(0.0, Rotation2d.fromDegrees(fLdeg)),
                new SwerveModuleState(0.0, Rotation2d.fromDegrees(fRdeg)),
                new SwerveModuleState(0.0, Rotation2d.fromDegrees(bLdeg)),
                new SwerveModuleState(0.0, Rotation2d.fromDegrees(bRdeg))
            }
        );
    }

    // Sets drive motor idle mode to be either brake mode or coast mode.
    public void setDriveMotorBrakeMode(boolean brake) {
        IdleMode sparkMaxMode = brake ? IdleMode.kBrake : IdleMode.kCoast;
        NeutralModeValue phoenixMode = brake ? NeutralModeValue.Brake : NeutralModeValue.Coast;

        for (SwerveModule i : modules) {
            if (i.getSteerMotor() instanceof CANSparkMax) {
                ((CANSparkMax) i.getSteerMotor()).setIdleMode(IdleMode.kCoast);
            } else {
                ((TalonFX) i.getSteerMotor()).setNeutralMode(NeutralModeValue.Coast);
            }

            if (i.getDriveMotor() instanceof CANSparkMax) {
                ((CANSparkMax) i.getDriveMotor()).setIdleMode(sparkMaxMode);
            } else {
                ((TalonFX) i.getDriveMotor()).setNeutralMode(phoenixMode);
            }
        }
    }

    // This method is used to stop all of the swerve drive modules.
    public void stopModules() {
        for (SwerveModule i : modules) {
            i.set(0.0, i.getSteerAngle());
        }
    }

    public Rotation2d getRawGyroRotation() {
        return Rotation2d.fromDegrees(pigeon.getYaw().getValueAsDouble());
    }

    public Rotation2d getGyroscopeRotationNoApriltags() {
        return getOdometryPose2dNoApriltags().getRotation();
    }

    public Pose2d getOdometryPose2dNoApriltags() {
        return odometry.getPoseMeters();
    }

    public SwerveModulePosition[] getModulePositions() {
        return new SwerveModulePosition[] {
            m_frontLeftModule.getPosition(),
            m_frontRightModule.getPosition(),
            m_backLeftModule.getPosition(),
            m_backRightModule.getPosition()
        };
    }

    public void driveRobotRelative(ChassisSpeeds robotRelativeSpeeds) {
        ChassisSpeeds targetSpeeds = ChassisSpeeds.discretize(robotRelativeSpeeds, 0.02);
        SwerveModuleState[] targetStates = Constants.Drivetrain.M_KINEMATICS.toSwerveModuleStates(targetSpeeds);
        setChassisState(targetStates);
    }

    // Sets the gyroscope angle to zero. This can be used to set the direction the
    // robot is currently facing to the 'forwards' direction.
    public void zeroGyroscope() {
        resetOdometry(new Pose2d(getPose().getTranslation(), new Rotation2d(0.0)));
    }

    public void zeroGyroscope(Rotation2d rotation) {
        resetOdometry(new Pose2d(getPose().getTranslation(), rotation));
    }

    public ChassisSpeeds getChassisSpeeds() {
        return Constants.Drivetrain.M_KINEMATICS.toChassisSpeeds(getStates());
    }

    public SwerveModuleState[] getStates() {
        return new SwerveModuleState[] {
            m_frontLeftModule.getState(),
            m_frontRightModule.getState(),
            m_backLeftModule.getState(),
            m_backRightModule.getState()
        };
    }

    public double getDistanceMeters() {
        Pose2d desiredTargetBlue = new Pose2d(new Translation2d(1.0, 2.0), Rotation2d.fromRotations(0.5));
        Pose2d predictedBotPose = getPose();
        return desiredTargetBlue.minus(predictedBotPose).getTranslation().getNorm();
    }

    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(getRawGyroRotation(), getModulePositions(), pose);
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters(); // TODO: with SwerveDrivePoseEstimator... I think this is already done.
    }
}
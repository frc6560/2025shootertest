package com.team6560.frc2024.subsystems;

import com.team6560.frc2024.Constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import com.swervedrivespecialties.swervelib.MkModuleConfiguration;
import com.swervedrivespecialties.swervelib.MkSwerveModuleBuilder;
import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import com.swervedrivespecialties.swervelib.SwerveModule;
import com.swervedrivespecialties.swervelib.MotorType;

import java.util.function.Function;

public class Drivetrain extends SubsystemBase {

    private final Pigeon2 pigeon = new Pigeon2(Constants.CanIDs.GYRO_ID);
    private Field2d fieldOnlyOdometry;

    /* 

    // Default states for each module correspond to an X shape (brake mode)
    public static final SwerveModuleState[] DEFAULT_MODULE_STATES = new SwerveModuleState[] {
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(45.0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(-45.0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(-45.0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(45.0))
    };

    */

    public static final SwerveModuleState[] DEFAULT_MODULE_STATES = new SwerveModuleState[] {
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(0))
    };

    public SwerveModule[] modules;
    private final SwerveDriveOdometry odometry;



    // INITIALIZATION



    public Drivetrain() {

        ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");

        modules = new SwerveModule[] {
            createSwerveModule(
                tab, 
                "Front Left Module", 
                Constants.CanIDs.FRONT_LEFT_MODULE_DRIVE_MOTOR_ID, 
                Constants.CanIDs.FRONT_LEFT_MODULE_STEER_MOTOR_ID, 
                Constants.CanIDs.FRONT_LEFT_MODULE_STEER_ENCODER_ID, 
                Constants.Drivetrain.FRONT_LEFT_MODULE_STEER_OFFSET
            ),
            createSwerveModule(
                tab, 
                "Front Right Module", 
                Constants.CanIDs.FRONT_RIGHT_MODULE_DRIVE_MOTOR_ID, 
                Constants.CanIDs.FRONT_RIGHT_MODULE_STEER_MOTOR_ID, 
                Constants.CanIDs.FRONT_RIGHT_MODULE_STEER_ENCODER_ID, 
                Constants.Drivetrain.FRONT_RIGHT_MODULE_STEER_OFFSET
            ),
            createSwerveModule(
                tab, 
                "Back Left Module", 
                Constants.CanIDs.BACK_LEFT_MODULE_DRIVE_MOTOR_ID, 
                Constants.CanIDs.BACK_LEFT_MODULE_STEER_MOTOR_ID, 
                Constants.CanIDs.BACK_LEFT_MODULE_STEER_ENCODER_ID, 
                Constants.Drivetrain.BACK_LEFT_MODULE_STEER_OFFSET
            ),
            createSwerveModule(
                tab, 
                "Back Right Module", 
                Constants.CanIDs.BACK_RIGHT_MODULE_DRIVE_MOTOR_ID, 
                Constants.CanIDs.BACK_RIGHT_MODULE_STEER_MOTOR_ID, 
                Constants.CanIDs.BACK_RIGHT_MODULE_STEER_ENCODER_ID, 
                Constants.Drivetrain.BACK_RIGHT_MODULE_STEER_OFFSET
            )
        };

        odometry = new SwerveDriveOdometry(Constants.Drivetrain.M_KINEMATICS, getRawGyroRotation(), getModulePositions());
        fieldOnlyOdometry = new Field2d();
        SmartDashboard.putData("FieldOnlyOdometry", fieldOnlyOdometry);

        Pose2d initialPose = Constants.Global.IS_RED_ALLIANCE 
            ? Constants.Odometry.DEFAULT_POSE_RED_ALLIANCE
            : Constants.Odometry.DEFAULT_POSE_BLUE_ALLLIANCE;

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
            .withDriveMotor(Constants.Drivetrain.DRIVE_MOTOR_TYPE, driveMotorId)
            .withSteerMotor(Constants.Drivetrain.STEER_MOTOR_TYPE, steerMotorId)
            .withSteerEncoderPort(steerEncoderId)
            .withSteerOffset(steerOffset)
            .build();
    }



    // PERIODIC EXECUTION



    @Override
    public void periodic() {
        updateOdometry();
        fieldOnlyOdometry.setRobotPose(getPose2d());
    }



    // SWERVE MODULES


    // Accessing swerve module properties

    /* Returns drivetrain swerve modules. */
    public SwerveModule[] getModules() {
        return this.modules;
    }

    /* Get chassis speeds */
    public ChassisSpeeds getChassisSpeeds() {
        return Constants.Drivetrain.M_KINEMATICS.toChassisSpeeds(getModuleStates());
    }

    /* Get position of each swerve modules. */
    public SwerveModulePosition[] getModulePositions() {
        return getModuleData(SwerveModule::getPosition, SwerveModulePosition.class);
    }
    
    /* Get state of each swerve modules. */
    public SwerveModuleState[] getModuleStates() {
        return getModuleData(SwerveModule::getState, SwerveModuleState.class);
    }

    /* Get some property of swerve modules (generic method). */
    private <T> T[] getModuleData(Function<SwerveModule, T> extractor, Class<T> type) {
        @SuppressWarnings("unchecked")
        T[] data = (T[]) java.lang.reflect.Array.newInstance(type, modules.length);
        for (int i = 0; i < modules.length; i++) {
            data[i] = extractor.apply(modules[i]);
        }
        return data;
    }



    // Modifying chassis states

    /* Update chassis speeds */
    public void drive(ChassisSpeeds chassisSpeeds) {
        SwerveModuleState[] speeds = DEFAULT_MODULE_STATES;
        if (chassisSpeeds.vxMetersPerSecond != 0.0 || 
            chassisSpeeds.vyMetersPerSecond != 0.0 || 
            chassisSpeeds.omegaRadiansPerSecond != 0.0) {
            speeds = Constants.Drivetrain.M_KINEMATICS.toSwerveModuleStates(chassisSpeeds);
            SwerveDriveKinematics.desaturateWheelSpeeds(speeds, Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND);
        }
        setChassisState(speeds);
    }

    /* Set speed and rotation of each swerve module. */
    public void setChassisState(SwerveModuleState[] states) {
        double[] driveVoltages = new double[modules.length];
        double[] angles = new double[modules.length];
        
        for (int i = 0; i < states.length; i++) {
            driveVoltages[i] = states[i].speedMetersPerSecond / Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND * Constants.Global.MAX_VOLTAGE;
            angles[i] = states[i].angle.getRadians();
        }
        
        applyChassisStates(driveVoltages, angles);
    }

    /* Set speed and rotation of each swerve module. Takes list of rotations as input, speed is set to 0. */
    public void setChassisState(double[] rotations) {
        double[] driveVoltages = new double[modules.length];
        double[] angles = new double[modules.length];
        
        for (int i = 0; i < modules.length; i++) {
            driveVoltages[i] = 0.0;
            angles[i] = Rotation2d.fromDegrees(rotations[i]).getRadians();
        }
        
        applyChassisStates(driveVoltages, angles);
    }

    /* This method is used to stop all of the swerve drive modules. */
    public void stopModules() {
        double[] driveVoltages = new double[modules.length];
        double[] angles = new double[modules.length];
        
        for (int i = 0; i < modules.length; i++) {
            driveVoltages[i] = 0.0;
            angles[i] = modules[i].getSteerAngle();
        }

        applyChassisStates(driveVoltages, angles);
    }

    /* Set chassis states given a list of voltagles and angles. */
    private void applyChassisStates(double driveVoltages[], double angles[]) {
        for (int i = 0; i < modules.length; i++) {
            modules[i].set(driveVoltages[i], angles[i]);
        }
    }



    // Setting motor break/coast modes

    /* Sets drive motor idle mode to be either brake mode or coast mode. */
    public void setMotorBrakeMode(boolean brake) {

        IdleMode SPARK_MAX_BREAK_MODE = IdleMode.kBrake;
        IdleMode SPARK_MAX_COAST_MODE = IdleMode.kCoast;

        NeutralModeValue TALONFX_BREAK_MODE = NeutralModeValue.Brake;
        NeutralModeValue TALONFX_COAST_MODE = NeutralModeValue.Coast;

        if (Constants.Drivetrain.STEER_MOTOR_TYPE == MotorType.NEO) {
            for (SwerveModule module : modules) {
                ((CANSparkMax) module.getSteerMotor()).setIdleMode(SPARK_MAX_COAST_MODE);
            }
        } else {
            for (SwerveModule module : modules) {
                ((TalonFX) module.getSteerMotor()).setNeutralMode(TALONFX_COAST_MODE);
            }
        }

        if (Constants.Drivetrain.DRIVE_MOTOR_TYPE == MotorType.NEO) {
            for (SwerveModule module : modules) {
                ((CANSparkMax) module.getDriveMotor()).setIdleMode(brake ? SPARK_MAX_BREAK_MODE : SPARK_MAX_COAST_MODE);
            }
        } else {
            for (SwerveModule module : modules) {
                ((TalonFX) module.getDriveMotor()).setNeutralMode(brake ? TALONFX_BREAK_MODE : TALONFX_COAST_MODE);
            }
        }
    }



    // ODOMETRY



    /* Update field-relative position */
    private void updateOdometry() {
        odometry.update(getRawGyroRotation(), getModulePositions());
    }

    /* Reset field-relative position */
    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(getRawGyroRotation(), getModulePositions(), pose);
    }

    public Rotation2d getRawGyroRotation() {
        return Rotation2d.fromDegrees(pigeon.getYaw().getValueAsDouble());
    }

    public Rotation2d getGyroscopeRotationNoApriltags() {
        return odometry.getPoseMeters().getRotation();
    }

    public Pose2d getPose2d() {
        return odometry.getPoseMeters();
    }

    public void driveRobotRelative(ChassisSpeeds robotRelativeSpeeds) {
        ChassisSpeeds targetSpeeds = ChassisSpeeds.discretize(robotRelativeSpeeds, 0.02);
        SwerveModuleState[] targetStates = Constants.Drivetrain.M_KINEMATICS.toSwerveModuleStates(targetSpeeds);
        setChassisState(targetStates);
    }

    /* Set gyroscope angle to 0 (forward) */
    public void zeroGyroscope() {
        setGyroscope(new Rotation2d(0.0));
    }

    /* Set gyroscope angle */
    public void setGyroscope(Rotation2d rotation) {
        resetOdometry(new Pose2d(getPose2d().getTranslation(), rotation));
    }

    public double getDistanceMeters() {
        Pose2d desiredTargetBlue = new Pose2d(new Translation2d(1.0, 2.0), Rotation2d.fromRotations(0.5));
        Pose2d predictedBotPose = getPose2d();
        return desiredTargetBlue.minus(predictedBotPose).getTranslation().getNorm();
    }
}

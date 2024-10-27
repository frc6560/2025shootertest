package com.team6560.frc2024.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.team6560.frc2024.Constants;
import com.team6560.frc2024.utility.PID.PIDConfigFuncs;
import com.team6560.frc2024.utility.PID.TalonFXPIDConfigProfile;

import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;

import java.time.Instant;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase {

    final TalonFX motor;
    final DigitalInput topLimitSwitch;
    final DigitalInput bottomLimitSwitch;
    final CANSparkMax AmpMotor;
    final DigitalInput distanceSensor;

    private static final int DISTANCE_SENSOR_PORT = 7;

    private double encoderInitialValue;

    private static final double UP_RATE = 0.2;
    private static final double DOWN_RATE = -0.2;

    private static final double GEAR_RATIO = 25 * 360 / 40;

    private static final double LOWER_SOFT_LIMIT = 13.0;
    private static final double UPPER_SOFT_LIMIT = 53.0;

    private static final double LOWER_HARD_LIMIT = 7.0;
    private static final double UPPER_HARD_LIMIT = 71.0;

    private static final int UPPER_LIMIT_SWITCH_ID = 8;
    private static final int LOWER_LIMIT_SWITCH_ID = 9;

    public static final TalonFXPIDConfigProfile PID_PROFILE = new TalonFXPIDConfigProfile(
      .5, 0, 0
    );

    public static final double SHOOT_ANGLE = 51.7;
    public static final double PASS_ANGLE = 25.0;
    public static final double AMP_ANGLE = 40.0;
    //not actual amp angle
    public static final double MOTORTALONFX_ANGLE = 40.0;
    private static final double ANGLE_THRESHOLD = 1.5;
    
    double currentAngle = this.getAngle();

    public Hood() { 
        this.motor = new TalonFX(Constants.CanIDs.HOOD_MOTOR_ID); 
        this.motor.setNeutralMode(NeutralModeValue.Brake);
        PIDConfigFuncs.configurePID(motor, PID_PROFILE);
        this.topLimitSwitch = new DigitalInput(UPPER_LIMIT_SWITCH_ID);
        this.bottomLimitSwitch = new DigitalInput(LOWER_LIMIT_SWITCH_ID);
        this.encoderInitialValue = this.motor.getPosition().getValueAsDouble(); // initial encoder pos in terms of rotations
        this.distanceSensor = new DigitalInput(DISTANCE_SENSOR_PORT);
        this.AmpMotor = new CANSparkMax(Constants.CanIDs.AMP_MOTOR_ID, MotorType.kBrushless);
        this.AmpMotor.restoreFactoryDefaults();
        this.AmpMotor.setIdleMode(IdleMode.kBrake);
        this.AmpMotor.setSmartCurrentLimit(25);
        this.AmpMotor.setOpenLoopRampRate(0.1);

        ntDispTab("Amp Ready").add("Amp Ready: ", this::getAmpReady);

        ntDispTab("Hood ")
            .add("Hood Feed Rate", this::getFeedRate)
            .add("Hood Angle", this::getAngle)
            .add("Upper limit switch", this::topLimitDown)  
            .add("Lower limit switch", this::bottomLimitDown)   
            .add("Upper soft limit", this::getUpperBound)
            .add("Lower soft limit", this::getLowerBound);
    }   
    
    public void ampStop() {
        setAngle(currentAngle);
    }

    public void ampRun() {
        setAmpRate(Constants.ShooterAmpHood.AMP_FEED_RATE);
    }

    public boolean gamePieceIn() {
        return !this.distanceSensor.get();
    }

    
    public boolean getAmpReady() {
        return (gamePieceIn() && (getFeedRate() == 0));
    }

    public void setAmpRate(double speed) {
        AmpMotor.set(speed);
    }

    private void setFeedRate(double speed) {
        motor.set(speed);
    }

    private double getFeedRate() {
        return motor.getVelocity().getValueAsDouble();
    }

    public double getAngle() {
        return ((motor.getPosition().getValueAsDouble() * 360) - (this.encoderInitialValue * 360)) / GEAR_RATIO + LOWER_HARD_LIMIT;
    }

    // returns True if angle is close enough to initiate shooting
    public boolean setAngle(double targetAngle) {
        double currentAngle = this.getAngle();
        
        if (currentAngle < LOWER_SOFT_LIMIT) {
            this.setFeedRate(UP_RATE);
            return false;
        } else if (currentAngle > UPPER_SOFT_LIMIT) {
            this.setFeedRate(DOWN_RATE);
            return false;
        } else {

            double angleDifference = targetAngle - currentAngle;
    
            if (Math.abs(angleDifference) < ANGLE_THRESHOLD) {
                double slowDownFactor = Math.max(0.1, Math.abs(angleDifference) / ANGLE_THRESHOLD);
                if (currentAngle < targetAngle) {
                    this.setFeedRate(UP_RATE * slowDownFactor); 
                } else {
                    this.setFeedRate(DOWN_RATE * slowDownFactor);
                }
                return true;
            } else if (currentAngle < targetAngle - ANGLE_THRESHOLD) {
                this.setFeedRate(UP_RATE);
                return false;
            } else if (currentAngle > targetAngle + ANGLE_THRESHOLD) {
                this.setFeedRate(DOWN_RATE);
                return false;
            }
            return false;
        }
    }

    public boolean setShootAngle() {
        return this.setAngle(SHOOT_ANGLE);
    }

    public boolean setPassAngle() {
        return this.setAngle(PASS_ANGLE);
    }

    public boolean setAmpAngle() {
        return this.setAngle(AMP_ANGLE);
    }

    public void drop() {
        if (this.getAngle() > LOWER_SOFT_LIMIT) {
            this.setFeedRate(DOWN_RATE);
        } else {
            this.setFeedRate(0.0);
        }
    }

    private boolean topLimitDown() {
        return !this.topLimitSwitch.get();
    }

    private boolean bottomLimitDown() {
        if (!this.bottomLimitSwitch.get()) {
            this.encoderInitialValue = this.motor.getPosition().getValueAsDouble();
            return true;
        }
        return false;
    }

    public double getLowerBound() {
        return LOWER_SOFT_LIMIT;
    }

    public double getUpperBound() {
        return UPPER_SOFT_LIMIT;
    }
}

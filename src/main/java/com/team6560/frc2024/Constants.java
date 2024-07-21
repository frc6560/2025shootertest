// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6560.frc2024;

import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

// import edu.wpi.first.apriltag.AprilTagFieldLayout;
// import edu.wpi.first.math.geometry.Rotation3d;
// import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;

// import edu.wpi.first.wpilibj.Filesystem;

public final class Constants {


    public static final int INTAKE_FEED_MOTOR = 15;



    public static final double INTAKE_FEED_RATE = 0.6;
    public static final double INTAKE_REVERSE_RATE = -0.3;


    

    public static final int INTAKE_PROXIMITY_SENSOR_PORT = 6;


}
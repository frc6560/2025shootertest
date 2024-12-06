package com.team6560.lib.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/* Class for determining current alliance. */
public class AllianceUtil {

    public static final Alliance ALLIANCE = DriverStation.getAlliance().orElse(DriverStation.Alliance.Red);
    public static final boolean IS_RED_ALLIANCE = (ALLIANCE == DriverStation.Alliance.Red);

}

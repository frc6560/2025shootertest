package com.team6560.frc2024.utility;

import com.team6560.frc2024.Constants;
import static com.team6560.frc2024.utility.NetworkTable.NtValueDisplay.ntDispTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase; 

public class LimelightLogic extends SubsystemBase{
    public void LimlightThingys() {
        double untruedistance = (Constants.Limelight.APRILTAG_HEIGHT-0.29845)/(tan(getVerticalAngle()));
        double horizontaldistance = untruedistance*tan(getHorizontalAngle());
        double truedistance = Math.sqrt(Math.pow(untruedistance,2) + Math.pow(horizontaldistance, 2));
    }

    public double getVerticalAngle() {
        return ntY.getDouble(0.0);
      }

    public double getHorizontalAngle() {
        if(hasTarget())
        return ntX.getDouble(0.0);
        else return 0.0;
    }
}

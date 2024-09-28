package com.team6560.frc2024.subsystems;

import com.team6560.frc2024.Constants;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

 //If analog voltage is above 1.6V, it means that an object is 3 cm or closer to the sensor. It returns true if so

public boolean GamePieceIn {
    public AnalogInput AnalogInput = new AnalogInput(Constants.CanIDs.DISTANCE_SENSOR_ID);
    double AnalogVoltage = AnalogInput.getVoltage()
    if (AnalogVoltage >= 1.6) {
        return true;
    }
    else {
        return false;
    }
}

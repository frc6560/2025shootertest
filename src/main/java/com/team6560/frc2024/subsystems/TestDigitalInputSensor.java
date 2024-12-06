package com.team6560.frc2024.subsystems;

import com.team6560.lib.hardware.sensors.DigitalInputSensor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.team6560.lib.util.NetworkTable.NtValueDisplay;

public class TestDigitalInputSensor extends SubsystemBase {
    
    private DigitalInputSensor sensor1;
    private DigitalInputSensor sensor2;

    // Testing with upper limit and lower switch on hood
    // Change DIO port ID otherwise
    public TestDigitalInputSensor() {
        sensor1 = new DigitalInputSensor(8);
        sensor2 = new DigitalInputSensor(9).withReversedOutput();
        NtValueDisplay.ntDispTab("TestDigitalInputSensor")
            .add("Sensor 1", () -> this.sensor1.get())
            .add("Sensor 2", () -> this.sensor2.get());
    }

}

/* (C) 2024 */
package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/*
 * This OpMode illustrates how to use the Modern Robotics Range Sensor.
 *
 * The OpMode assumes that the range sensor is configured with a name of "sensor_range".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 *
 * @see <a href="http://modernroboticsinc.com/range-sensor">MR Range Sensor</a>
 */
@TeleOp(name = "Sensor: MR range sensor", group = "Sensor")
@Disabled // comment out or remove this line to enable this OpMode
public class SensorMRRangeSensor extends LinearOpMode {

  ModernRoboticsI2cRangeSensor rangeSensor;

  @Override
  public void runOpMode() {

    // get a reference to our compass
    rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");

    // wait for the start button to be pressed
    waitForStart();

    while (opModeIsActive()) {
      telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic());
      telemetry.addData("raw optical", rangeSensor.rawOptical());
      telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
      telemetry.addData("cm", "%.2f cm", rangeSensor.getDistance(DistanceUnit.CM));
      telemetry.update();
    }
  }
}

/* (C) 2024 */
package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

/*
 * This OpMode illustrates the concept of driving up to a line and then stopping.
 * The code is structured as a LinearOpMode
 *
 * The Sensor used here can be a REV Color Sensor V2 or V3.  Make sure the white LED is turned on.
 * The sensor can be plugged into any I2C port, and must be named "sensor_color" in the active configuration.
 *
 *   Depending on the height of your color sensor, you may want to set the sensor "gain".
 *   The higher the gain, the greater the reflected light reading will be.
 *   Use the SensorColor sample in this folder to determine the minimum gain value that provides an
 *   "Alpha" reading of 1.0 when you are on top of the white line.  In this sample, we use a gain of 15
 *   which works well with a Rev V2 color sensor
 *
 *   Setting the correct WHITE_THRESHOLD value is key to stopping correctly.
 *   This should be set halfway between the bare-tile, and white-line "Alpha" values.
 *   The reflected light value can be read on the screen once the OpMode has been INIT, but before it is STARTED.
 *   Move the sensor on and off the white line and note the min and max readings.
 *   Edit this code to make WHITE_THRESHOLD halfway between the min and max.
 *
 *   Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 *   Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@Autonomous(name = "Robot: Auto Drive To Line", group = "Robot")
@Disabled
public class RobotAutoDriveToLine_Linear extends LinearOpMode {

	/* Declare OpMode members. */
	private DcMotor leftDrive = null;
	private DcMotor rightDrive = null;

	/** The variable to store a reference to our color sensor hardware object */
	NormalizedColorSensor colorSensor;

	static final double WHITE_THRESHOLD = 0.5; // spans between 0.0 - 1.0 from dark to light
	static final double APPROACH_SPEED = 0.25;

	@Override
	public void runOpMode() {

		// Initialize the drive system variables.
		leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
		rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

		// To drive forward, most robots need the motor on one side to be reversed,
		// because the axles
		// point in opposite directions.
		// When run, this OpMode should start both motors driving forward. So adjust
		// these two lines
		// based on your first test drive.
		// Note: The settings here assume direct drive on left and right wheels. Gear
		// Reduction or 90
		// Deg drives may require direction flips
		leftDrive.setDirection(DcMotor.Direction.REVERSE);
		rightDrive.setDirection(DcMotor.Direction.FORWARD);

		// If there are encoders connected, switch to RUN_USING_ENCODER mode for greater
		// accuracy
		// leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		// rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		// Get a reference to our sensor object. It's recommended to use
		// NormalizedColorSensor over
		// ColorSensor, because NormalizedColorSensor consistently gives values between
		// 0 and 1, while
		// the values you get from ColorSensor are dependent on the specific sensor
		// you're using.
		colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

		// If necessary, turn ON the white LED (if there is no LED switch on the sensor)
		if (colorSensor instanceof SwitchableLight) {
			((SwitchableLight) colorSensor).enableLight(true);
		}

		// Some sensors allow you to set your light sensor gain for optimal
		// sensitivity...
		// See the SensorColor sample in this folder for how to determine the optimal
		// gain.
		// A gain of 15 causes a Rev Color Sensor V2 to produce an Alpha value of 1.0 at
		// about 1.5"
		// above the floor.
		colorSensor.setGain(15);

		// Wait for driver to press START)
		// Abort this loop is started or stopped.
		while (opModeInInit()) {

			// Send telemetry message to signify robot waiting;
			telemetry.addData("Status", "Ready to drive to white line."); //

			// Display the light level while we are waiting to start
			getBrightness();
		}

		// Start the robot moving forward, and then begin looking for a white line.
		leftDrive.setPower(APPROACH_SPEED);
		rightDrive.setPower(APPROACH_SPEED);

		// run until the white line is seen OR the driver presses STOP;
		while (opModeIsActive() && (getBrightness() < WHITE_THRESHOLD)) {
			sleep(5);
		}

		// Stop all motors
		leftDrive.setPower(0);
		rightDrive.setPower(0);
	}

	// to obtain reflected light, read the normalized values from the color sensor.
	// Return the Alpha
	// channel.
	double getBrightness() {
		NormalizedRGBA colors = colorSensor.getNormalizedColors();
		telemetry.addData("Light Level (0 to 1)", "%4.2f", colors.alpha);
		telemetry.update();

		return colors.alpha;
	}
}

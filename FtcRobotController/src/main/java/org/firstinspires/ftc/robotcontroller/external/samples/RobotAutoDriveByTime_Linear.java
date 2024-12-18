/* (C) 2024 */
package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This OpMode illustrates the concept of driving a path based on time.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: RobotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backward for 1 Second
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@Autonomous(name = "Robot: Auto Drive By Time", group = "Robot")
@Disabled
public class RobotAutoDriveByTime_Linear extends LinearOpMode {

	/* Declare OpMode members. */
	private DcMotor leftDrive = null;
	private DcMotor rightDrive = null;

	private ElapsedTime runtime = new ElapsedTime();

	static final double FORWARD_SPEED = 0.6;
	static final double TURN_SPEED = 0.5;

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

		// Send telemetry message to signify robot waiting;
		telemetry.addData("Status", "Ready to run"); //
		telemetry.update();

		// Wait for the game to start (driver presses START)
		waitForStart();

		// Step through each leg of the path, ensuring that the OpMode has not been
		// stopped along the
		// way.

		// Step 1: Drive forward for 3 seconds
		leftDrive.setPower(FORWARD_SPEED);
		rightDrive.setPower(FORWARD_SPEED);
		runtime.reset();
		while (opModeIsActive() && (runtime.seconds() < 3.0)) {
			telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
			telemetry.update();
		}

		// Step 2: Spin right for 1.3 seconds
		leftDrive.setPower(TURN_SPEED);
		rightDrive.setPower(-TURN_SPEED);
		runtime.reset();
		while (opModeIsActive() && (runtime.seconds() < 1.3)) {
			telemetry.addData("Path", "Leg 2: %4.1f S Elapsed", runtime.seconds());
			telemetry.update();
		}

		// Step 3: Drive Backward for 1 Second
		leftDrive.setPower(-FORWARD_SPEED);
		rightDrive.setPower(-FORWARD_SPEED);
		runtime.reset();
		while (opModeIsActive() && (runtime.seconds() < 1.0)) {
			telemetry.addData("Path", "Leg 3: %4.1f S Elapsed", runtime.seconds());
			telemetry.update();
		}

		// Step 4: Stop
		leftDrive.setPower(0);
		rightDrive.setPower(0);

		telemetry.addData("Path", "Complete");
		telemetry.update();
		sleep(1000);
	}
}

/* (C) 2024 */
package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/*
 * This OpMode demonstrates a POV Drive system, with commented-out code for a Tank Drive system,
 * for a two wheeled robot using two REV SPARKminis.
 * To use this example, connect two REV SPARKminis into servo ports on the Control Hub. On the
 * robot configuration, use the drop down list under 'Servos' to select 'REV SPARKmini Controller'
 * and name them 'left_drive' and 'right_drive'.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name = "REV SPARKmini Simple Drive Example", group = "Concept")
@Disabled
public class ConceptRevSPARKMini extends LinearOpMode {

	// Declare OpMode members.
	private ElapsedTime runtime = new ElapsedTime();
	private DcMotorSimple leftDrive = null;
	private DcMotorSimple rightDrive = null;

	@Override
	public void runOpMode() {
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		// Initialize the hardware variables. Note that the strings used here as
		// parameters
		// to 'get' must correspond to the names assigned during robot configuration.
		leftDrive = hardwareMap.get(DcMotorSimple.class, "left_drive");
		rightDrive = hardwareMap.get(DcMotorSimple.class, "right_drive");

		// Most robots need the motor on one side to be reversed to drive forward
		// Reverse the motor that runs backward when connected directly to the battery
		leftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
		rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

		// Wait for the game to start (driver presses START)
		waitForStart();
		runtime.reset();

		// run until the end of the match (driver presses STOP)
		while (opModeIsActive()) {

			// Setup a variable for each drive wheel to save power level for telemetry
			double leftPower;
			double rightPower;

			// Choose to drive using either Tank Mode, or POV Mode
			// Comment out the method that's not used. The default below is POV.

			// POV Mode uses left stick to go forward, and right stick to turn.
			// - This uses basic math to combine motions and is easier to drive straight.
			double drive = -gamepad1.left_stick_y;
			double turn = gamepad1.right_stick_x;
			leftPower = Range.clip(drive + turn, -1.0, 1.0);
			rightPower = Range.clip(drive - turn, -1.0, 1.0);

			// Tank Mode uses one stick to control each wheel.
			// - This requires no math, but it is hard to drive forward slowly and keep
			// straight.
			// leftPower = -gamepad1.left_stick_y ;
			// rightPower = -gamepad1.right_stick_y ;

			// Send calculated power to wheels
			leftDrive.setPower(leftPower);
			rightDrive.setPower(rightPower);

			// Show the elapsed game time and wheel power.
			telemetry.addData("Status", "Run Time: " + runtime.toString());
			telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
			telemetry.update();
		}
	}
}

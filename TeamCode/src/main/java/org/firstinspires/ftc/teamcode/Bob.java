/* (C) 2024 */
package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Elevator;

@TeleOp
public class Bob extends OpMode {

	private MecanumDrive drive;
	private GamepadEx driverOp;

	private IMU imu;

	private Arm arm;
	private Elevator elevator;

	private Servo pivot;
	private CRServo intake;

	private Button aButton;
	private Button bButton;

	private Button xButton;

	private Button yButton;

	private Button rightBumper;
	private Button leftBumper;

	@Override
	public void init() {
		/* instantiate motors */
		Motor frontLeft = new Motor(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_312);
		Motor frontRight = new Motor(hardwareMap, "frontRight", Motor.GoBILDA.RPM_312);
		 frontLeft.setInverted(true);
		Motor backLeft = new Motor(hardwareMap, "backLeft", Motor.GoBILDA.RPM_312);
		Motor backRight = new Motor(hardwareMap, "backRight", Motor.GoBILDA.RPM_312);
		this.drive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);

		this.imu = hardwareMap.get(IMU.class, "imu");

		this.intake = hardwareMap.crservo.get("claw");
		this.pivot = hardwareMap.servo.get("pivot");
		// Adjust the orientation parameters to match your robot
		IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
				RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
		// Without this, the REV Hub's orientation is assumed to be logo up / USB
		// forward
		imu.initialize(parameters);
		driverOp = new GamepadEx(gamepad1);

		aButton = new GamepadButton(driverOp, GamepadKeys.Button.A);
		bButton = new GamepadButton(driverOp, GamepadKeys.Button.B);
		xButton = new GamepadButton(driverOp, GamepadKeys.Button.X);
		yButton = new GamepadButton(driverOp, GamepadKeys.Button.Y);
		rightBumper = new GamepadButton(driverOp, GamepadKeys.Button.RIGHT_BUMPER);
		leftBumper = new GamepadButton(driverOp, GamepadKeys.Button.LEFT_BUMPER);

		this.elevator = new Elevator(hardwareMap, "elevator", telemetry);
		this.arm = new Arm(hardwareMap, "arm1", "arm2", telemetry);

		CommandScheduler.getInstance().enable();

		aButton.whenPressed(elevator.setPositionCmd(-2100));
		rightBumper.whenPressed(arm.increasePosition(50));
		leftBumper.whenPressed(arm.increasePosition(-50));
		bButton.whenPressed(arm.setPositionCmd(215, -750));
		xButton.whenPressed(arm.setPositionCmd(13, -57));
		yButton.whenPressed(elevator.setPositionCmd(0));
		System.out.println("HELLOOOOO");
		
	}

	@Override
	public void loop() {

		// drive.driveFieldCentric(driverOp.getLeftX(), driverOp.getLeftY(),
		// driverOp.getRightX(),
		// imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES), // gyro value
		// passed in here must be in
		// // degrees
		// false);
		drive.driveRobotCentric(driverOp.getLeftX(), driverOp.getLeftY(), driverOp.getRightX());



		if (driverOp.isDown(GamepadKeys.Button.RIGHT_BUMPER)) {
			arm.increasePosition(5).schedule();
		}
		if (driverOp.isDown(GamepadKeys.Button.LEFT_BUMPER)) {
			arm.increasePosition(-5).schedule();
		}
		if(driverOp.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) >0) {
			elevator.increasePos(5).schedule();
		}
		if(driverOp.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) >0) {
			elevator.increasePos(-5).schedule();
		}
		if (driverOp.isDown(GamepadKeys.Button.DPAD_DOWN)) {
			intake.setPower(-1);
		}
		if (driverOp.isDown(GamepadKeys.Button.DPAD_UP)) {
			intake.setPower(1);
		}
		if (driverOp.getButton(GamepadKeys.Button.LEFT_STICK_BUTTON)) {
			intake.setPower(0);
		}
		if (driverOp.isDown(GamepadKeys.Button.DPAD_LEFT)) {
			pivot.setPosition(0.5);
		}
		if (driverOp.isDown(GamepadKeys.Button.DPAD_RIGHT)) {
			pivot.setPosition(1);
		}

		CommandScheduler.getInstance().run();
		telemetry.addData("Pivot", pivot.getPosition());
		telemetry.addData("Intake", intake.getPortNumber());
		telemetry.update();
	}

}
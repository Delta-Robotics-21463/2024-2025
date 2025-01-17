/* (C) 2024 */
package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
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

import org.firstinspires.ftc.teamcode.subsystems.HorizantalElevator;
import org.firstinspires.ftc.teamcode.subsystems.VerticalElevator;

@TeleOp
public class Bob extends OpMode {

	private MecanumDrive drive;
	private GamepadEx driverOp;

	private GamepadEx operatorOp;

	private IMU imu;

	private HorizantalElevator horizantalElevator;

	private VerticalElevator verticalElevator;

	private Servo horizantalPivot;
	private Servo intakeClaw;

	private Servo horizantalTwist;

	private Servo outtakeClaw;

	private Servo verticalPivot;

	private Button aButton;
	private Button bButton;

	private Button xButton;

	private Button yButton;

	private Button bButtonOp;

	private Button rightBumper;
	private Button leftBumper;

	@Override
	public void init() {
		/* instantiate motors */
		Motor backLeft = new Motor(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_312);
		Motor frontLeft = new Motor(hardwareMap, "frontRight", Motor.GoBILDA.RPM_312);
		Motor backRight = new Motor(hardwareMap, "backLeft", Motor.GoBILDA.RPM_312);
		Motor frontRight = new Motor(hardwareMap, "backRight", Motor.GoBILDA.RPM_312);
		backLeft.setInverted(false);
		frontLeft.setInverted(true);
		backRight.setInverted(false);
		frontRight.setInverted(true);
		this.drive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);

		this.imu = hardwareMap.get(IMU.class, "imu");

		this.intakeClaw = hardwareMap.servo.get("intakeClaw");
		this.horizantalPivot = hardwareMap.servo.get("horizantalPivot");
		this.horizantalTwist = hardwareMap.servo.get("horizantalTwist");
		this.outtakeClaw = hardwareMap.servo.get("outtakeClaw");
		this.verticalPivot = hardwareMap.servo.get("verticalPivot");
		// Adjust the orientation parameters to match your robot
		IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
				RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
		// Without this, the REV Hub's orientation is assumed to be logo up / USB
		// forward
		imu.initialize(parameters);
		driverOp = new GamepadEx(gamepad1);
		operatorOp = new GamepadEx(gamepad2);

		aButton = new GamepadButton(driverOp, GamepadKeys.Button.A);
		bButton = new GamepadButton(driverOp, GamepadKeys.Button.B);
		xButton = new GamepadButton(driverOp, GamepadKeys.Button.X);
		yButton = new GamepadButton(driverOp, GamepadKeys.Button.Y);
		bButtonOp = new GamepadButton(operatorOp, GamepadKeys.Button.B);
		rightBumper = new GamepadButton(driverOp, GamepadKeys.Button.RIGHT_BUMPER);
		leftBumper = new GamepadButton(driverOp, GamepadKeys.Button.LEFT_BUMPER);

		this.horizantalElevator = new HorizantalElevator(hardwareMap, "horizantalElevator", telemetry);
		this.verticalElevator = new VerticalElevator(hardwareMap, "elevatorLeft", "elevatorRight", telemetry);


		CommandScheduler.getInstance().enable();

		aButton.whenPressed(verticalElevator.setPositionCmd(4000, -4000).alongWith(new InstantCommand(()->verticalPivot.setPosition(0))));
		bButton.whenPressed(verticalElevator.setPositionCmd(0, 0).alongWith(new InstantCommand(()->verticalPivot.setPosition(0.31
		))));
		rightBumper.whileHeld(horizantalElevator.increasePos(20));
		leftBumper.whileHeld(horizantalElevator.increasePos(-20));

		bButtonOp.whenPressed(horizantalElevator.setPositionCmd(0).alongWith(new InstantCommand(()->horizantalPivot.setPosition(0.83)).alongWith(new InstantCommand(()->verticalPivot.setPosition(0.475)))));

		this.verticalElevator.resetPos();
		this.horizantalElevator.resetPos();
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
		drive.driveRobotCentric(driverOp.getLeftX()*0.75, driverOp.getLeftY()*0.75, driverOp.getRightX()*0.75);
		if (driverOp.isDown(GamepadKeys.Button.X)) {
			horizantalPivot.setPosition(0.83);
		}
		if (driverOp.isDown(GamepadKeys.Button.Y)) {
			horizantalPivot.setPosition(0);
		}
		if (driverOp.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)>0.5) {
			horizantalTwist.setPosition(1);
		}
		if (driverOp.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)>0.5) {
			horizantalTwist.setPosition(0.45);
		}

		if (driverOp.isDown(GamepadKeys.Button.DPAD_UP)) {
			intakeClaw.setPosition(0.75);
		}
		if (driverOp.isDown(GamepadKeys.Button.DPAD_DOWN)) {
			intakeClaw.setPosition(0.35);
		}
		if (driverOp.isDown(GamepadKeys.Button.DPAD_RIGHT)) {
			outtakeClaw.setPosition(0.5);
		}
		if (driverOp.isDown(GamepadKeys.Button.DPAD_LEFT)) {
			outtakeClaw.setPosition(0.8);
		}
		if (operatorOp.isDown(GamepadKeys.Button.A)) {
			outtakeClaw.setPosition(0.8);
			intakeClaw.setPosition(0.35);
		}



		CommandScheduler.getInstance().run();
		telemetry.addData("Pivot", horizantalPivot.getPosition());
		telemetry.addData("IntakeClaw", intakeClaw.getPosition());
		telemetry.addData("Twist", horizantalTwist.getPosition());
		telemetry.addData("OuttakeClaw", outtakeClaw.getPosition());
		telemetry.addData("VerticalPivot", verticalPivot.getPosition());
		telemetry.update();
	}

}
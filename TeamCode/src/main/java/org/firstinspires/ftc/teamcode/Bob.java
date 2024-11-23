/* (C) 2024 */
package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandScheduler;
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

	@Override
	public void init() {
		/* instantiate motors */

		this.drive = new MecanumDrive(new Motor(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_435),
				new Motor(hardwareMap, "frontRight", Motor.GoBILDA.RPM_435),
				new Motor(hardwareMap, "backLeft", Motor.GoBILDA.RPM_435),
				new Motor(hardwareMap, "backRight", Motor.GoBILDA.RPM_435));

		this.imu = hardwareMap.get(IMU.class, "imu");

		this.intake = hardwareMap.crservo.get("intake");
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

		this.elevator = new Elevator(hardwareMap, "elevator", telemetry);
		this.arm = new Arm(hardwareMap, "arm", telemetry);

		CommandScheduler.getInstance().enable();

		aButton.whenPressed(elevator.setPositionCmd(-2000));
		bButton.whenPressed(arm.setPositionCmd(-975));
		xButton.whenPressed(arm.setPositionCmd(0));
		yButton.whenPressed(arm.setPositionCmd(-2000));
		System.out.println("HELLOOOOO");
	}

	@Override
	public void loop() {

		drive.driveFieldCentric(driverOp.getLeftX(), driverOp.getLeftY(), driverOp.getRightX(),
				imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES), // gyro value passed in here must be in
																			// degrees
				false);

		if (driverOp.isDown(GamepadKeys.Button.X)) {
			pivot.setPosition(10);
		}
		if (driverOp.isDown(GamepadKeys.Button.Y)) {
			intake.setPower(1);
		}
		telemetry.update();

		CommandScheduler.getInstance().run();
	}

}
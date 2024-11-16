/* (C) 2024 */
package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Elevator;

@TeleOp
public class Main extends OpMode {

	private MecanumDrive drive;
	private GamepadEx driverOp;

	private IMU imu;

	private Arm arm;
	private Elevator elevator;

	private Button aButton;
	private Button bButton;

	@Override
	public void init() {
		/* instantiate motors */

		this.drive = new MecanumDrive(new Motor(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_435),
				new Motor(hardwareMap, "frontRight", Motor.GoBILDA.RPM_435),
				new Motor(hardwareMap, "backLeft", Motor.GoBILDA.RPM_435),
				new Motor(hardwareMap, "backRight", Motor.GoBILDA.RPM_435));

		this.imu = hardwareMap.get(IMU.class, "imu");
		// Adjust the orientation parameters to match your robot
		IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
				RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
		// Without this, the REV Hub's orientation is assumed to be logo up / USB
		// forward
		imu.initialize(parameters);
		driverOp = new GamepadEx(gamepad1);

		aButton = new GamepadButton(driverOp, GamepadKeys.Button.A);
		bButton = new GamepadButton(driverOp, GamepadKeys.Button.B);

		this.elevator = new Elevator(hardwareMap, "elevator");
		this.arm = new Arm(hardwareMap, "arm");

		CommandScheduler.getInstance().enable();
	}

	@Override
	public void loop() {

		drive.driveFieldCentric(driverOp.getLeftX(), driverOp.getLeftY(), driverOp.getRightX(),
				imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES), // gyro value passed in here must be in
																			// degrees
				false);

		aButton.whenPressed(elevator.setPositionCmd(5));
		bButton.whenPressed(arm.setPositionCmd(5));

		CommandScheduler.getInstance().run();
	}

}
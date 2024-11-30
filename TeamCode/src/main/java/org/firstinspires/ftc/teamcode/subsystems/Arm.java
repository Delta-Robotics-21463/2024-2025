/* (C) 2024 */
package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm extends SubsystemBase {

	private final Motor arm1;
	private final Motor arm2;

	private double reqPosition = 0;

	private Telemetry telemetry;
	public Arm(HardwareMap hwmap, String name1, String name2, Telemetry telemetry) {
		this.arm1 = new Motor(hwmap, name1, Motor.GoBILDA.RPM_312);
		this.arm1.stopAndResetEncoder();
		this.arm1.setFeedforwardCoefficients(0.01, 0.025);
		this.arm1.setPositionCoefficient(.01);
		this.arm1.setPositionTolerance(30);
		// this.arm1.setRunMode(Motor.RunMode.PositionControl);
		this.arm2 = new Motor(hwmap, name2, Motor.GoBILDA.RPM_312);
		// this.arm2.setFeedforwardCoefficients(0.01, 0.025);
		this.arm2.setPositionCoefficient(.0025);
		this.arm2.setPositionTolerance(30);
		this.arm2.stopAndResetEncoder();
		this.arm2.setRunMode(Motor.RunMode.PositionControl);
		this.telemetry = telemetry;
		arm1.setInverted(true);
		arm2.setInverted(true);
	}

	private void setPosition(int position1, int position2) {
		arm1.setTargetPosition(position1);
		arm2.setTargetPosition(position2);
		telemetry.addData("this is running", 1);
	}

	public Command setPositionCmd(int position1, int position2) {
		return new InstantCommand(() -> this.setPosition(position1, position2), this);
	}

	@Override
	public void periodic() {
		telemetry.addData("Arm 1 position", arm1.getCurrentPosition());
		telemetry.addData("Arm 2 position", arm2.getCurrentPosition());
		// arm1.set(1);
		arm2.set(1);
	}

}

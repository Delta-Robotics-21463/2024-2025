/* (C) 2024 */
package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm extends SubsystemBase {

	private final Motor arm;

	private double reqPosition = 0;

	private Telemetry telemetry;
	public Arm(HardwareMap hwmap, String name, Telemetry telemetry) {
		this.arm = new Motor(hwmap, name, Motor.GoBILDA.RPM_312);
		this.arm.setRunMode(Motor.RunMode.PositionControl);
		this.telemetry = telemetry;
		arm.setPositionCoefficient(1);
	}

	private void setPosition(int position) {
		arm.setTargetPosition(position);
		telemetry.addData("this is running", 1);
	}

	public Command setPositionCmd(int position) {
		return new InstantCommand(() -> this.setPosition(position), this);
	}

	@Override
	public void periodic() {
		telemetry.addData("Arm position", arm.getCurrentPosition());
		arm.set(1);
	}

}

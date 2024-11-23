/* (C) 2024 */
package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator extends SubsystemBase {

	private final Motor elevator;

	private double reqPosition = 0;

	private Telemetry telemetry;
	public Elevator(HardwareMap hwmap, String name, Telemetry telemetry) {
		this.elevator = new Motor(hwmap, name, Motor.GoBILDA.RPM_312);
		this.elevator.setFeedforwardCoefficients(0.01, 0.025);
		this.elevator.setPositionCoefficient(.025);
		this.elevator.setPositionTolerance(30);
		this.elevator.setRunMode(Motor.RunMode.PositionControl);
		this.telemetry = telemetry;
		elevator.setInverted(true);
	}

	private void setPosition(int position) {
		elevator.setTargetPosition(position);
		telemetry.addData("this is running", 1);
	}

	public Command setPositionCmd(int position) {
		return new InstantCommand(() -> this.setPosition(position), this);
	}

	@Override
	public void periodic() {
		telemetry.addData("Elevator position", elevator.getCurrentPosition());
		elevator.set(1);
	}

}

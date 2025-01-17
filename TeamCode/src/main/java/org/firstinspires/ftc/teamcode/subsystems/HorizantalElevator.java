/* (C) 2024 */
package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RepeatCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HorizantalElevator extends SubsystemBase {

	private final Motor elevator;

	private int reqPosition = 0;

	private Telemetry telemetry;
	public HorizantalElevator(HardwareMap hwmap, String name, Telemetry telemetry) {
		this.elevator = new Motor(hwmap, name, Motor.GoBILDA.RPM_312);
//		this.elevator.setFeedforwardCoefficients(0.01, 0.025);
		this.elevator.setPositionCoefficient(.015);
		this.elevator.setPositionTolerance(50);
		this.elevator.stopAndResetEncoder();
		this.elevator.setRunMode(Motor.RunMode.PositionControl);
		this.telemetry = telemetry;
		elevator.setInverted(true);
	}


	public void resetPos() {
		this.elevator.resetEncoder();
	}

	private void setPosition(int position) {
		elevator.setTargetPosition(position);
		reqPosition = position;
		telemetry.addData("this is running", 1);
	}

	public int getPosition() {
		return elevator.getCurrentPosition();
	}
	public Command setPositionCmd(int position) {
		return new InstantCommand(() -> this.setPosition(position), this);
	}

	public Command increasePos(int amt) {
		return new RepeatCommand(new InstantCommand(()->this.setPosition(reqPosition-amt), this));
	}

	@Override
	public void periodic() {
		telemetry.addData("Elevator position", elevator.getCurrentPosition());
		telemetry.addData("Req pos", reqPosition);
		elevator.set(1);
	}

}

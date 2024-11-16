/* (C) 2024 */
package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Elevator extends SubsystemBase {

	private final Motor elevator;

	private double reqPosition = 0;
	public Elevator(HardwareMap hwmap, String name) {
		this.elevator = new Motor(hwmap, name, Motor.GoBILDA.RPM_312);
		elevator.setPositionCoefficient(1);
	}

	private void setPosition(int position) {
		elevator.setTargetPosition(position);
	}

	public Command setPositionCmd(int position) {
		return new InstantCommand(() -> this.setPosition(position), this);
	}

}

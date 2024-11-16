/* (C) 2024 */
package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm extends SubsystemBase {

	private final Motor arm;

	private double reqPosition = 0;
	public Arm(HardwareMap hwmap, String name) {
		this.arm = new Motor(hwmap, name, Motor.GoBILDA.RPM_312);
		arm.setPositionCoefficient(1);
	}

	private void setPosition(int position) {
		arm.setTargetPosition(position);
	}

	public Command setPositionCmd(int position) {
		return new InstantCommand(() -> this.setPosition(position), this);
	}

}

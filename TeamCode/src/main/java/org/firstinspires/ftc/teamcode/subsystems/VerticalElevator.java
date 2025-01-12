/* (C) 2024 */
package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class VerticalElevator extends SubsystemBase {

    private final Motor leftElevator;
    private final Motor rightElevator;

    private int leftReqPosition = 0;
    private int rightReqPosition = 0;

    private Telemetry telemetry;
    public VerticalElevator(HardwareMap hwmap, String name, String name2, Telemetry telemetry) {
        this.leftElevator = new Motor(hwmap, name, Motor.GoBILDA.RPM_312);
//		this.elevator.setFeedforwardCoefficients(0.01, 0.025);
        this.leftElevator.setPositionCoefficient(.025);
        this.leftElevator.setPositionTolerance(30);
        this.leftElevator.stopAndResetEncoder();
        this.leftElevator.setRunMode(Motor.RunMode.PositionControl);
        this.telemetry = telemetry;
        leftElevator.setInverted(true);


        this.rightElevator = new Motor(hwmap, name2, Motor.GoBILDA.RPM_312);
//		this.elevator.setFeedforwardCoefficients(0.01, 0.025);
        this.rightElevator.setPositionCoefficient(.025);
        this.rightElevator.setPositionTolerance(30);
        this.rightElevator.stopAndResetEncoder();
        this.rightElevator.setRunMode(Motor.RunMode.PositionControl);
        rightElevator.setInverted(true);
    }

    private void setPosition(int leftPosition, int rightPosition ) {
        leftElevator.setTargetPosition(leftPosition);
        leftReqPosition = leftPosition;
        rightReqPosition = rightPosition;
    }

    public int getLeftPosition() {
        return leftElevator.getCurrentPosition();
    }
    public int getRightPosition() {
        return rightElevator.getCurrentPosition();
    }
    public Command setPositionCmd(int leftPosition, int rightPosition) {
        return new InstantCommand(() -> this.setPosition(leftPosition, rightPosition), this);
    }

    public Command increasePos(int leftAmt, int rightAmt) {
        return new InstantCommand(()->this.setPosition(leftReqPosition -leftAmt, rightReqPosition-rightAmt), this);
    }

    @Override
    public void periodic() {
        telemetry.addData("Left Elevator position", leftElevator.getCurrentPosition());
        telemetry.addData("Left Req pos", leftReqPosition);
//        leftElevator.set(1);
//        rightElevator.set(1);
        telemetry.addData("Right Elevator position", rightElevator.getCurrentPosition());
        telemetry.addData("Right Req pos", rightReqPosition);
    }

}

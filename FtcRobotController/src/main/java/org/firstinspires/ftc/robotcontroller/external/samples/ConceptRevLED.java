/* (C) 2024 */
package org.firstinspires.ftc.robotcontroller.external.samples;

/*
 * This OpMode illustrates how to use the REV Digital Indicator
 *
 * This is a simple way to add the REV Digital Indicator which has a red and green LED.
 * (and as you may remember, red + green = yellow so when they are both on you can get yellow)
 *
 * Why?
 * You can use this to show information to the driver.   For example, green might be that you can
 * pick up more game elements and red would be that you already have the possession limit.
 *
 * This OpMode assumes that the REV Digital Indicator is setup as 2 Digital Channels named
 * front_led_green and front_led_red. (the green should be the lower of the 2 channels it is plugged
 * into and the red should be the higher)
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 *
 * You can buy this product here:  https://www.revrobotics.com/rev-31-2010/
 */
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;

@TeleOp(name = "Concept: RevLED", group = "Concept")
@Disabled
public class ConceptRevLED extends OpMode {
	LED frontLED_red;
	LED frontLED_green;

	@Override
	public void init() {
		frontLED_green = hardwareMap.get(LED.class, "front_led_green");
		frontLED_red = hardwareMap.get(LED.class, "front_led_red");
	}

	@Override
	public void loop() {
		if (gamepad1.a) {
			frontLED_red.on();
		} else {
			frontLED_red.off();
		}
		if (gamepad1.b) {
			frontLED_green.on();
		} else {
			frontLED_green.off();
		}
	}
}

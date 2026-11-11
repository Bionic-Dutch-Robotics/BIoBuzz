package org.firstinspires.ftc.teamcode.pedro;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Test extends OpMode {
    private DcMotorEx Motor;
    private Servo Servo;
    private double position = 0.95;

    @Override
    public void init () {
        Motor = hardwareMap.get(DcMotorEx.class, "motor");
        Servo = hardwareMap.get(Servo.class, "servo");
        Servo.setPosition(position);
    }
    @Override
    public void loop () {
        // Motor controls
        if (gamepad1.b) {
            Motor.setPower(1.0);
        } else if (gamepad1.right_bumper) {
            Motor.setPower(-1.0);
        } else if (gamepad1.a) {
            Motor.setPower(0.0);
        }

        // Servo adjustments using X and Y
        if (gamepad1.x) {
            position += 0.005;
            if (position > 1.0) {
                position = 1.0;
            }
        } else if (gamepad1.y) {
            position -= 0.005;
            if (position < 0.0) {
                position = 0.0;
            }
        }

        Servo.setPosition(position);

        telemetry.addData("position", position);
        telemetry.update();
    }
}

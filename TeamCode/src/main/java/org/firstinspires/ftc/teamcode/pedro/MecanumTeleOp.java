package org.firstinspires.ftc.teamcode.pedro;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class MecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize hardware from Constants
        Constants.create(hardwareMap);

        // Reverse the right side motors. This may be wrong for your setup.
        Constants.frmotor.setDirection(DcMotorEx.Direction.REVERSE);
        Constants.brmotor.setDirection(DcMotorEx.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            Constants.flmotor.setPower(frontLeftPower);
            Constants.blmotor.setPower(backLeftPower);
            Constants.frmotor.setPower(frontRightPower);
            Constants.brmotor.setPower(backRightPower);
        }
    }
}

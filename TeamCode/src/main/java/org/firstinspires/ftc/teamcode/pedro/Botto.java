package org.firstinspires.ftc.teamcode.pedro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//@Disabled
@TeleOp(name="Botto")
public class Botto extends OpMode {
    private DcMotorEx Transfer = null;
    private DcMotorEx Intake;
    private DcMotorEx Outtake;
    private DcMotor FL;
    private DcMotor FR;
    private DcMotor BL;
    private DcMotor BR;
    private double Shooter = .75;

    private boolean shooterdebounce = false;

    @Override
    public void init () {
        //Configuration
        {
            Transfer = hardwareMap.get(DcMotorEx.class, "Transfer");
            Intake = hardwareMap.get(DcMotorEx.class, "Intake");
            Outtake = hardwareMap.get(DcMotorEx.class, "Outtake");
            FL = hardwareMap.get(DcMotor.class, "front_left");
            FR = hardwareMap.get(DcMotor.class, "front_right");
            BL = hardwareMap.get(DcMotor.class, "back_left");
            BR = hardwareMap.get(DcMotor.class, "back_right");
        }
        //Direction
        {
            BL.setDirection(DcMotorSimple.Direction.REVERSE);
            FL.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }
    @Override
    public void loop () {
        //Drive
        {
            double drive = gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double twist = gamepad1.right_stick_x;
            double[] speeds = {
                    (drive + strafe + twist),
                    (drive - strafe - twist),
                    (drive - strafe + twist),
                    (drive + strafe - twist)
            };
            double max = Math.abs(speeds[0]);
            for (int i = 0; i < speeds.length; i++) {
                if (max < Math.abs(speeds[i])) max = Math.abs(speeds[i]);
            }
            if (max > 1) {
                for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
            }
            FL.setPower(speeds[0]);
            FR.setPower(speeds[1]);
            BL.setPower(speeds[2]);
            BR.setPower(speeds[3]);
        }
        //Intake
        {
            if (gamepad1.a) {
                Intake.setPower(.7);
            } else if (gamepad1.b) {
                Intake.setPower(-.7);
            } else {
                Intake.setPower(0);
            }
        }
        //Outtake
        {
            if (gamepad1.right_bumper) {
                Outtake.setPower(Shooter);
            } else {
                Outtake.setPower(0);
            }
            {
                if (gamepad1.dpad_up && !shooterdebounce) {
                    Shooter = Shooter + .02;
                    shooterdebounce = true;
                } else if (gamepad1.dpad_down && !shooterdebounce) {
                    Shooter = Shooter - .02;
                    shooterdebounce = true;
                } else if (!gamepad1.dpad_down && !gamepad1.dpad_up) {
                    shooterdebounce = false;
                }
                telemetry.addData("Shooter", Shooter);

            }
            telemetry.update();
        }
        //Transfer
        {
            if (gamepad1.y) {
                Transfer.setPower(1);
            } else {
                Transfer.setPower(0);
            }
        }
    }
}
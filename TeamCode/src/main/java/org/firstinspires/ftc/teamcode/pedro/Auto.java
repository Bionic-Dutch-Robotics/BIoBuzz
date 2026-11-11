package org.firstinspires.ftc.teamcode.pedro;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Auto")
public class Auto extends LinearOpMode {

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotorEx launcher = null;
    private DcMotor intake = null;
    private CRServo leftIntakeServo = null;
    private CRServo rightIntakeServo = null;
    private CRServo windmillServo = null;

    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        // Initialize hardware directly from hardwareMap
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        windmillServo = hardwareMap.get(CRServo.class, "windmill");
        leftIntakeServo = hardwareMap.get(CRServo.class, "left_intake_servo");
        rightIntakeServo = hardwareMap.get(CRServo.class, "right_intake_servo");

        // Set motor directions
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // Drive forward for 2.0 seconds
        while (opModeIsActive() && runtime.seconds() < 15.0) {
            leftDrive.setPower(0.5);
            rightDrive.setPower(0.5);

            telemetry.addData("Status", "Driving Forward: %.1f sec", runtime.seconds());
            telemetry.update();
        }

        // Stop drive motors
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        telemetry.addData("Status", "Complete");
        telemetry.update();
    }
}

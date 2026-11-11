package org.firstinspires.ftc.teamcode.pedro;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp(name = "BioBuzz StarterBot Teleop")
//@Disabled
public class StarterBot extends OpMode {

    // Declare OpMode members.
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotorEx launcher = null;
    private DcMotor intake = null;
    private CRServo leftIntakeServo = null;
    private CRServo rightIntakeServo = null;
    private CRServo windmillServo = null;
    double shooter = .125;
    private boolean shooterspeed = false;

    public final int LAUNCHER_TARGET_VELOCITY = 1250;
    public final int LAUNCHER_MIN_VELOCITY = 1200;

    double leftPower;
    double rightPower;

    // Create a variable to set to the intake.
    double intakePower;

    @Override
    public void init() {
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        windmillServo = hardwareMap.get(CRServo.class, "windmill");
        leftIntakeServo = hardwareMap.get(CRServo.class, "left_intake_servo");
        rightIntakeServo = hardwareMap.get(CRServo.class, "right_intake_servo");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        leftDrive.setZeroPowerBehavior(BRAKE);
        rightDrive.setZeroPowerBehavior(BRAKE);
        intake.setZeroPowerBehavior(BRAKE);

        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));

        leftIntakeServo.setPower(0);
        rightIntakeServo.setPower(0);
        windmillServo.setPower(0);

        rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
        windmillServo.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        arcadeDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);

        intakePower = gamepad1.right_trigger - gamepad1.left_trigger;

        launch();

        intake.setPower(intakePower);
        leftIntakeServo.setPower(intakePower);
        rightIntakeServo.setPower(intakePower);

        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Shooter Speed", "%.3f", shooter);
        telemetry.addData("Launcher Velocity", launcher.getVelocity());
        telemetry.update();
    }

    @Override
    public void stop() {
    }

    void arcadeDrive(double forward, double rotate) {
        leftPower = forward + rotate;
        rightPower = forward - rotate;

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }

    void launch() {
        // gamepad2.a starts the shooter motor forward, gamepad2.y reverses it
        if (gamepad2.a) {
            launcher.setPower(shooter);
        } else if (gamepad2.y) {
            launcher.setPower(-shooter);
        } else {
            launcher.setPower(0);
        }

        // Windmill servo control using left and right bumpers
        if (gamepad2.left_bumper) {
            windmillServo.setPower(1);
        } else if (gamepad2.right_bumper) {
            windmillServo.setPower(-1);
        } else {
            windmillServo.setPower(0);
        }

        // Increase (gamepad2.b) and decrease (gamepad2.x) shooter speed with single-press edge detection
        if (gamepad2.b && !shooterspeed) {
            shooter = Math.min(1.0, shooter + 0.005);
            shooterspeed = true;
        } else if (gamepad2.x && !shooterspeed) {
            shooter = Math.max(0.0, shooter - 0.005);
            shooterspeed = true;
        } else if (!gamepad2.b && !gamepad2.x) {
            shooterspeed = false;
        }
    }
}

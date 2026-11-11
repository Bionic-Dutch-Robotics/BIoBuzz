package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants  {
    public static DcMotorEx flmotor;
    public static DcMotorEx frmotor;
    public static DcMotorEx blmotor;
    public static DcMotorEx brmotor;
    public static GoBildaPinpointDriver pinpoint;

    public static Follower create(HardwareMap h) {
        flmotor = h.get(DcMotorEx.class, "front_left");
        frmotor = h.get(DcMotorEx.class, "front_right");
        blmotor = h.get(DcMotorEx.class, "back_left");
        brmotor = h.get(DcMotorEx.class, "back_right");
        try {
            pinpoint = h.get(GoBildaPinpointDriver.class, "pinpoint");
        } catch (Exception e) {
            // Optional if not configured yet
        }
        // return new Follower(Drivetrain, Localizer, Foresight)
        return null;
    }
}

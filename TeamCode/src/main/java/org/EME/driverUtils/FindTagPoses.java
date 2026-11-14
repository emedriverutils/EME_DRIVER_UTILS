package org.EME.driverUtils;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp(name = "Find tag poses", group = "EME_DRIVER_UTILS")
public class FindTagPoses extends LinearOpMode {
    public int[] tagIDs = {38,39,40,41,42,43,44,45};
    public void runOpMode(){
        LimelightRunner runner = new LimelightRunner(hardwareMap.get(Limelight3A.class, "limelight"));
        waitForStart();
        while(opModeIsActive()){
            for(int ID : tagIDs) {
                YawPitchRollAngles rot = runner.tagPoseFromCamera(ID).getOrientation();
                telemetry.addLine("Tag #" + Integer.toString(ID) + " rotation information:\n" +
                        "\tYaw is " + rot.getYaw(AngleUnit.DEGREES) + "degrees\n" +
                        "\tPitch is " + rot.getPitch(AngleUnit.DEGREES) + "degrees\n" +
                        "\tRoll is " + rot.getRoll(AngleUnit.DEGREES) + "degrees\n");
            }
            telemetry.update();
        }
    }
}

package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.io.IOException;

@Configurable
@TeleOp(group = "EME_DRIVER_UTILS", name = "VisionManagerTest")
public class VisionManagerTest extends LinearOpMode {
    public static int testTag = 38;
    public void runOpMode(){
        VisionManager camera;
        waitForStart();
        try {
            ConfigManager.startConfigManager("Config", hardwareMap);
            GamepadManager.startGamepadManager(gamepad1, gamepad2);
            HardwareManager.startHardwareManager(hardwareMap, true);
            camera = new VisionManager(new ManualDrive());
        }catch (Exception e){
            String exceptionMessage = TelemetryManager.exceptionPrintout(e);
            while(opModeIsActive()){
                telemetry.addLine("An exception occurred:");
                telemetry.addLine(exceptionMessage);
                telemetry.update();
            }
            return;
        }
        while(opModeIsActive() && !gamepad1.aWasPressed()){
            Pose2D position = camera.localize();
            telemetry.addLine("Please press gamepad1.a if you have not set up a proper fmap in your limelight, or if your robot's:");
            telemetry.addLine("x = " + position.getX(DistanceUnit.INCH) + "inches,");
            telemetry.addLine("y = " + position.getY(DistanceUnit.INCH) + "inches, and");
            telemetry.addLine("heading = " + position.getHeading(AngleUnit.DEGREES));
            telemetry.addLine("If any of these are null or NaN try moving the robot until they all have real values");
            telemetry.update();
        }
        while(opModeIsActive() && !gamepad1.aWasPressed()){
            try {
                camera.rotateTo(testTag);
                telemetry.addLine("If your robot is rotating to an aprilTag with ID " + Integer.toString(testTag) + " please press gamepad1.a");
                telemetry.addLine("If not, try rotating the robot (for this one you can use drive controls) toward an aprilTag with ID " + Integer.toString(testTag));
                telemetry.update();
            }catch(IllegalAccessException e){
                String exceptionMessage = TelemetryManager.exceptionPrintout(e);
                while(opModeIsActive()){
                    telemetry.addLine("An exception occurred:");
                    telemetry.addLine(exceptionMessage);
                    telemetry.update();
                }
            }
        }
        while(opModeIsActive() && !gamepad1.aWasPressed()){
            telemetry.addLine("BIOBUZZ only, otherwise press gamepad1.a");
            telemetry.addLine("If the statement 'The red goal is tipped toward the flower' is " + camera.redGoalIsTipped() + ", please press gamepad1.a");
            telemetry.addLine("If I'm asking you if the statement is null, try moving the robot so that it can see the red goal.");
            telemetry.update();
        }
        while(opModeIsActive() && !gamepad1.aWasPressed()){
            telemetry.addLine("BIOBUZZ only, otherwise press gamepad1.a");
            telemetry.addLine("If the statement 'The blue goal is tipped toward the flower' is " + camera.blueGoalIsTipped() + ", please press gamepad1.a");
            telemetry.addLine("If I'm asking you if the statement is null, try moving the robot so that it can see the blue goal.");
            telemetry.update();
        }
        while(opModeIsActive()){
            telemetry.addLine("Done! All tests were successful.");
            telemetry.addLine("Unless you pressed gamepad1.a when something wasn't working. Then something is wrong.");
            telemetry.update();
        }
    }
}

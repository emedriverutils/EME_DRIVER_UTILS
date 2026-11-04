package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TelemetryManagerTest", group = "EME_DRIVER_UTILS")
public class TelemetryManagerTest extends LinearOpMode {
    public void runOpMode(){
        waitForStart();
        TelemetryManager.startTelemetryManager(telemetry);
        while(opModeIsActive() && !gamepad1.aWasPressed()){
            telemetry.addLine("Instructions will appear on telemetry and in panels. When you see them, press the indicated button. If no instructions appear, something went wrong.");
            telemetry.addLine("Press gamepad1.a to continue.");
            telemetry.update();
        }
        telemetry.update();
        while(opModeIsActive() && !gamepad1.aWasPressed()){
            TelemetryManager.addOneTime("Test 1", "Please press gamepad1.a");
            TelemetryManager.update();
        }
        TelemetryManager.update();
        while(opModeIsActive() && !gamepad1.aWasPressed()){
            TelemetryManager.addOneTime("Test 2: Please press gamepad1.a");
            TelemetryManager.update();
        }
        TelemetryManager.update();
        String press = "Please press gamepad1.a";
        TelemetryManager.add("Test 3", press);
        while(opModeIsActive() && !gamepad1.aWasPressed()){
            TelemetryManager.update();
        }
        TelemetryManager.remove("Test 3", press);
        TelemetryManager.add("Test 4: Please press gamepad1.a");
        TelemetryManager.update();
        while(opModeIsActive() && !gamepad1.aWasPressed()){
            TelemetryManager.update();
        }
        TelemetryManager.remove("Test 4: Please press gamepad1.a");
        TelemetryManager.addKeyed("Test 5", "Please press gamepad1.a", this);
        TelemetryManager.update();
        while(opModeIsActive() && !gamepad1.aWasPressed()){
            TelemetryManager.update();
        }
        TelemetryManager.updateWithKey(this);
        TelemetryManager.update();
        while(opModeIsActive()){
            TelemetryManager.addOneTime("done with all 5 tests");
            TelemetryManager.update();
        }
    }
}

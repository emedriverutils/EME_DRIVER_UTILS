package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

@Autonomous(name = "ManualDrive test", group = "EME_DRIVER_UTILS")
public class ManualDriveTest extends LinearOpMode {
    ManualDrive drive;
    public void runOpMode(){
        try {
            ConfigManager.startConfigManager("Config.yml", hardwareMap);
            GamepadManager.startGamepadManager(gamepad1, gamepad2);
            HardwareManager.startHardwareManager(hardwareMap, ConfigManager.config.get("limelight") != null);
        }catch(Exception e){
            while(opModeIsActive() && !gamepad1.a){
                telemetry.addLine("Press a to continue the program");
                telemetry.addLine("An exception occurred: " + e.getMessage());
                StackTraceElement[] stack = e.getStackTrace();
                for(int i = 0; i < stack.length; i++){
                    telemetry.addLine(stack[i].toString());
                }
                telemetry.update();
            }
        }
        drive = new ManualDrive();
        Gamepad lastGamepad = new Gamepad();
        boolean aWasPressed = false;
        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a && !lastGamepad.a) {
                if(aWasPressed){
                    break;
                }else{
                    aWasPressed = true;
                }
            }
            try {
                if(aWasPressed){
                    telemetry.addLine("Please press your buttons for driving and strafing.");
                    telemetry.addLine("If the drivetrain drives and strafes how you want it to and slowly turns, press gamepad1.a");
                    telemetry.addLine("Otherwise, you have a problem.");
                    drive.runGivenTurn(0.25, ManualDrive.NaNTurnBehavior.SET_TO_GAMEPAD_INPUT);
                }else {
                    telemetry.addLine("Please press your buttons for driving, strafing, and turning.");
                    telemetry.addLine("If the drivetrain moves how you want it to, press gamepad1.a");
                    telemetry.addLine("Otherwise, you have a problem.");
                    drive.runWheels();
                }
            }catch(IllegalAccessException e){
                telemetry.addLine("An exception occurred: " + e.getMessage());
                StackTraceElement[] stack = e.getStackTrace();
                for(int i = 0; i < stack.length; i++){
                    telemetry.addLine(stack[i].toString());
                }
            }
            telemetry.update();
            lastGamepad.copy(gamepad1);
        }
    }
}

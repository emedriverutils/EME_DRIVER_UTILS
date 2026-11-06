package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.HashMap;

@Autonomous(name = "GamepadManager test", group = "EME_DRIVER_UTILS")
public class GamepadManagerTest extends LinearOpMode {
    public static final String[] gamepadBoolButtons = {"gamepad1.a", "gamepad1.b", "gamepad1.x", "gamepad1.y", "gamepad1.left_bumper", "gamepad1.right_bumper"};
    public static final String[] gamepadDoubleButtons = {"gamepad1.left_stick_x", "gamepad1.left_stick_y", "gamepad1.right_stick_x", "gamepad1.right_stick_y", "gamepad1.left_trigger", "gamepad1.right_trigger"};
    public void runOpMode(){
        String[] doubleActions = GamepadManager.doubleActions;
        String[] booleanActions = GamepadManager.booleanActions;
        HashMap<String, String> map = new HashMap<String, String>((int) Math.ceil((doubleActions.length + booleanActions.length)/0.75));
        String telemetryPut = "";
        if(doubleActions.length != 0) {
            for (int i = 0; i < doubleActions.length; i++) {
                map.put(doubleActions[i], gamepadDoubleButtons[i]);
            }
        }
        if(booleanActions.length != 0) {
            for (int i = 0; i < booleanActions.length; i++) {
                map.put(booleanActions[i], gamepadBoolButtons[i]);
            }
        }
        ConfigManager.startConfigManager(map);
        try {
            GamepadManager.startGamepadManager(gamepad1, gamepad2);
        }catch(Exception e){
            telemetryPut = "An exception was found:\n" + e.getMessage();
            StackTraceElement[] stack = e.getStackTrace();
            for(int i = 0; i < stack.length; i++){
                telemetryPut = telemetryPut + "\n" + stack[i].toString();
            }
        }
        waitForStart();
        for(int i = 0; i < doubleActions.length; i++) {
            while(opModeIsActive()){
                if(!telemetryPut.isEmpty()) {
                    telemetry.addLine(telemetryPut);
                }
                telemetry.addLine("Please press or push on: " + gamepadDoubleButtons[i]);
                try {
                    if (Math.abs(GamepadManager.getOutDouble(doubleActions[i])) > 0.25) {
                        telemetry.addLine("Success! That button mapped correctly!");
                        telemetry.update();
                        sleep(3000);
                        break;
                    }
                }catch(Exception e){
                    if(!telemetryPut.isEmpty()){
                        telemetryPut = telemetryPut + "\n";
                    }
                    telemetryPut = telemetryPut + "An error occurred:\n" + e.getMessage();
                }
                telemetry.update();
            }
        }
        for(int i = 0; i < booleanActions.length; i++) {
            while(opModeIsActive()){
                if(!telemetryPut.isEmpty()) {
                    telemetry.addLine(telemetryPut);
                }
                telemetry.addLine("Please press: " + gamepadBoolButtons[i]);
                try {
                    if (GamepadManager.getOutBool(booleanActions[i])) {
                        telemetry.addLine("Success! That button mapped correctly");
                        telemetry.update();
                        sleep(3000);
                        break;
                    }
                }catch(Exception e){
                    if(!telemetryPut.isEmpty()){
                        telemetryPut = telemetryPut + "\n";
                    }
                    telemetryPut = telemetryPut + "An error occurred:\n" + e.getMessage();
                }
                telemetry.update();
            }
        }
        while(opModeIsActive()){
            telemetry.addLine("All binds worked as they were supposed to. GamepadManager works.");
            telemetry.update();
        }
    }
}

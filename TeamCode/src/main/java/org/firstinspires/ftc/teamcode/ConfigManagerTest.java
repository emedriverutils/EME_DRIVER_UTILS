package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.HashMap;
import java.util.Map;

@Autonomous(name = "ConfigManager test", group = "EME_DRIVER_UTILS")
public class ConfigManagerTest extends LinearOpMode {
    public enum ConfigTest{
        YAML,
        ARRAYS,
        MAP
    }
    public void runOpMode(){
        ConfigTest test = ConfigTest.YAML;
        while(opModeInInit()) {
            if (gamepad1.x) {
                test = ConfigTest.YAML;
                telemetry.addLine("YAML");
            }else if (gamepad1.y){
                test = ConfigTest.ARRAYS;
                telemetry.addLine("Arrays");
            }else if (gamepad1.a){
                test = ConfigTest.MAP;
                telemetry.addLine("Map");
            }
            telemetry.update();
        }
        waitForStart();
        boolean successful = true;
        String telemetryPut = "";
        String[] keys = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        Map<String, String> map = new HashMap<String, String>();
        for(int i = 0; i < keys.length; i++){
            map.put(keys[i], values[i]);
        }
        if(test == ConfigTest.YAML){
            try{
                ConfigManager.startConfigManager("ConfigTest.yml", hardwareMap);
            }catch(Exception e) {
                telemetryPut = "An exception was found:\n" + e.getMessage() + "\n";
                StackTraceElement[] stack = e.getStackTrace();
                for(int i = 0; i < stack.length; i++){
                    telemetryPut = telemetryPut + "\n" + stack[i].toString();
                }
            }
        }else if(test == ConfigTest.ARRAYS){
            try{
                ConfigManager.startConfigManager(keys, values);
            }catch(Exception e){
                telemetryPut = "An exception was found:\n" + e.getMessage() + "\n";
                StackTraceElement[] stack = e.getStackTrace();
                for(int i = 0; i < stack.length; i++){
                    telemetryPut = telemetryPut + "\n" + stack[i].toString();
                }
            }
        }else if(test == ConfigTest.MAP){
            ConfigManager.startConfigManager(map);
        }
        if (!telemetryPut.isEmpty()){
            while(opModeIsActive() && !gamepad1.x) {
                telemetry.addLine("Press x to continue the program from the exception screen");
                telemetry.addLine(telemetryPut);
                telemetry.update();
            }
        }
        for(int i = 0; i < keys.length; i++){
            if(!values[i].equals(ConfigManager.config.get(keys[i]))){
                successful = false;
            }
        }
        while(opModeIsActive()){
            if(!telemetryPut.isEmpty()){
                telemetry.addLine(telemetryPut);
            }
            if(successful){
                telemetry.addLine("the test was successful");
            }else{
                telemetry.addLine("the test failed");
            }
            telemetry.update();
        }
    }
}

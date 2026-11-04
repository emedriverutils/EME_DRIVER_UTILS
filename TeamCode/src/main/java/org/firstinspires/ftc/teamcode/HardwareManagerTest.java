package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.io.File;
import java.io.IOException;

@Autonomous(name = "HardwareManager test", group = "EME_DRIVER_UTILS")
public class HardwareManagerTest extends LinearOpMode {
    public void runOpMode(){
        try {
            ConfigManager.startConfigManager("HardwareTest.yml", hardwareMap);
        }catch(IOException e){
            telemetry.addLine("Please test your config manager first and make sure that the file HardwareTest.yml exists in the assets folder of main");
            telemetry.update();
        }
        HardwareManager.startHardwareManager(hardwareMap, ConfigManager.config.get("limelight") != null);
        String[] motorNames = HardwareManager.motorNames;
        String[] servoNames = HardwareManager.servoNames;
        Gamepad lastGamepad = new Gamepad();
        waitForStart();
        for(int i = 0; i < motorNames.length; i++){
            while(opModeIsActive()){
                telemetry.addLine("Press gamepad1.a if " + ConfigManager.config.get(motorNames[i]) + " is spinning in the direction you want it to");
                HardwareManager.getMotor(motorNames[i]).setPower(0.25);
                telemetry.update();
                if(gamepad1.a && !lastGamepad.a){
                    HardwareManager.getMotor(motorNames[i]).setPower(0);
                    telemetry.addLine("Success! You said the correct motor was spinning in the direction you wanted!");
                    telemetry.update();
                    sleep(3000);
                    break;
                }
                lastGamepad.copy(gamepad1);
            }
        }
        for(int i = 0; i < servoNames.length; i++){
            HardwareManager.getServo(servoNames[i]).setPosition(0);
            while(opModeIsActive()){
                telemetry.addLine("Press gamepad1.a if " + ConfigManager.config.get(servoNames[i]) + " is moving");
                HardwareManager.getServo(servoNames[i]).setPosition(HardwareManager.getServo(servoNames[i]).getPosition() + 0.01);
                telemetry.update();
                if(HardwareManager.getServo(servoNames[i]).getPosition() >= 0.1){
                    HardwareManager.getServo(servoNames[i]).setPosition(0);
                }
                if(gamepad1.a && !lastGamepad.a){
                    telemetry.addLine("Success! You said the correct servo was moving!");
                    telemetry.update();
                    sleep(3000);
                    break;
                }
                lastGamepad.copy(gamepad1);
            }
        }
    }
}

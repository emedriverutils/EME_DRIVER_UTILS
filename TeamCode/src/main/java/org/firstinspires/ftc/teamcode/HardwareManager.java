package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;
import java.util.Map;

public class HardwareManager {
    public static String[] motorNames = {"leftBackDrive", "rightBackDrive", "leftFrontDrive", "rightFrontDrive"};
    private static Map<String, DcMotor> motors;
    public static String[] servoNames = {};
    private static Map<String, Servo> servos;
    private static Limelight3A limelight;

    public static void startHardwareManager(HardwareMap hardwareMap, boolean hasLimelight){
        motors = new HashMap<String, DcMotor>((int) Math.ceil(motorNames.length/0.75));
        for(String name:motorNames) {
            motors.put(name, hardwareMap.get(DcMotor.class, ConfigManager.config.get(name)));
        }
        servos = new HashMap<String, Servo>((int) Math.ceil(servoNames.length/0.75));
        for(String name:servoNames){
            servos.put(name, hardwareMap.get(Servo.class, ConfigManager.config.get(name)));
        }
        if(hasLimelight) {
            limelight = hardwareMap.get(Limelight3A.class, ConfigManager.config.get("limelight"));
        }
    }
    public static DcMotor getMotor(String name){
        return motors.get(name);
    }
    public static Servo getServo(String name){
        return servos.get(name);
    }
    public static Limelight3A getLimelight(){
        return limelight;
    }
}

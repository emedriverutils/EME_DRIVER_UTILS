package org.firstinspires.ftc.eme_aar;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;
import java.util.Map;

public class HardwareManager {
    public static String[] motorNames = {"FrontLeft", "BackLeft", "FrontRight", "BackRight"};
    private static Map<String, DcMotor> motors;
    public static String[] servoNames = {};
    private static Map<String, Servo> servos;
    private static Limelight3A limelight;

    public static void startHardwareManager(HardwareMap hardwareMap, boolean hasLimelight, String[] newMotorNames, String[] newServoNames){
        if(newMotorNames != null && newMotorNames.length != 0){
            String[] currentNames = motorNames;
            motorNames = new String[currentNames.length + newMotorNames.length];
            System.arraycopy(currentNames, 0, motorNames, 0, currentNames.length);
            System.arraycopy(newMotorNames, 0, motorNames, currentNames.length, newMotorNames.length);
        }
        motors = new HashMap<String, DcMotor>((int) Math.ceil(motorNames.length/0.75));
        for(String name:motorNames) {
            DcMotor motor = hardwareMap.get(DcMotor.class, ConfigManager.config.get(name));
            motors.put(name, motor);
            if(ConfigManager.config.get(name + "Reversed").equals("T")){
                motor.setDirection(DcMotorSimple.Direction.REVERSE);
            }else{
                motor.setDirection(DcMotorSimple.Direction.FORWARD);
            }
        }
        if(newServoNames != null && newServoNames.length != 0){
            String[] currentNames = servoNames;
            servoNames = new String[currentNames.length + newServoNames.length];
            System.arraycopy(currentNames, 0, servoNames, 0, currentNames.length);
            System.arraycopy(newServoNames, 0, servoNames, currentNames.length, newServoNames.length);
        }
        servos = new HashMap<String, Servo>((int) Math.ceil(servoNames.length/0.75));
        for(String name:servoNames){
            servos.put(name, hardwareMap.get(Servo.class, ConfigManager.config.get(name)));
        }
        if(hasLimelight) {
            limelight = hardwareMap.get(Limelight3A.class, ConfigManager.config.get("limelight"));
        }
    }
    public static void startHardwareManager(HardwareMap hardwareMap, boolean hasLimelight){
        startHardwareManager(hardwareMap, hasLimelight, null, null);
    }
    public static void startHardwareManager(HardwareMap hardwareMap){
        startHardwareManager(hardwareMap, ConfigManager.config.get(limelight) != null);
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

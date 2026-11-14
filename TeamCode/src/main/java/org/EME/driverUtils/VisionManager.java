package org.EME.driverUtils;

import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

public class VisionManager {
    private final LimelightRunner limelight;
    private final ManualDrive drive;
    public PIDFController controller;
    public static String[] PIDFCoeffStrings = {"visionKp", "visionKi", "visionKd", "visionKf"};
    public VisionManager(ManualDrive drive){
        limelight = new LimelightRunner(HardwareManager.getLimelight());
        this.drive = drive;
        double[] coeffs = ConfigManager.getDoubles(PIDFCoeffStrings);
        controller = new PIDFController(coeffs[0], coeffs[1], coeffs[2], coeffs[3]);
        controller.setSetPoint(0.0);
    }
    public VisionManager(ManualDrive drive, PIDFController controller){
        limelight = new LimelightRunner(HardwareManager.getLimelight());
        this.drive = drive;
        this.controller = controller;
    }
    public Pose2D localize(){
        return limelight.getBotPose();
    }
    public void rotateTo(int aprilTag) throws IllegalAccessException{
        drive.runGivenTurn(controller.calculate(limelight.getDX(aprilTag)), ManualDrive.NaNTurnBehavior.SET_TO_GAMEPAD_INPUT);
    }

    //BIOBUZZ
    //ONLY if your camera is facing perpendicular to gravity
    public Boolean redGoalIsTipped/*is it facing up toward the flower*/(){
        Pose3D[] flowerSidePoses = {limelight.tagPoseFromCamera(30),
        limelight.tagPoseFromCamera(31),
        limelight.tagPoseFromCamera(32),
        limelight.tagPoseFromCamera(33)};
        Pose3D[] oppSidePoses = {limelight.tagPoseFromCamera(34),
        limelight.tagPoseFromCamera(35),
        limelight.tagPoseFromCamera(36),
        limelight.tagPoseFromCamera(37)};
        for(Pose3D aprilTag : flowerSidePoses){
            if(aprilTag != null) {
                if (AngleUnit.normalizeDegrees(aprilTag.getOrientation().getYaw(AngleUnit.DEGREES)) < 90 &&
                AngleUnit.normalizeDegrees(aprilTag.getOrientation().getYaw(AngleUnit.DEGREES)) > -90) {
                    return Boolean.TRUE;
                }else{
                    return Boolean.FALSE;
                }
            }
        }
        for(Pose3D aprilTag : oppSidePoses){
            if(aprilTag != null) {
                if (AngleUnit.normalizeDegrees(aprilTag.getOrientation().getYaw(AngleUnit.DEGREES)) > 90 ||
                        AngleUnit.normalizeDegrees(aprilTag.getOrientation().getYaw(AngleUnit.DEGREES)) < -90) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            }
        }
        return null;
    }
    public Boolean blueGoalIsTipped/*is it facing up toward the flower*/(){
        Pose3D[] flowerSidePoses = {limelight.tagPoseFromCamera(38),
                limelight.tagPoseFromCamera(39),
                limelight.tagPoseFromCamera(40),
                limelight.tagPoseFromCamera(41)};
        Pose3D[] oppSidePoses = {limelight.tagPoseFromCamera(42),
                limelight.tagPoseFromCamera(43),
                limelight.tagPoseFromCamera(44),
                limelight.tagPoseFromCamera(45)};
        for(Pose3D aprilTag : flowerSidePoses){
            if(aprilTag != null) {
                if (AngleUnit.normalizeDegrees(aprilTag.getOrientation().getYaw(AngleUnit.DEGREES)) < 90 &&
                AngleUnit.normalizeDegrees(aprilTag.getOrientation().getYaw(AngleUnit.DEGREES)) > -90) {
                    return Boolean.TRUE;
                }else{
                    return Boolean.FALSE;
                }
            }
        }
        for(Pose3D aprilTag : oppSidePoses){
            if(aprilTag != null) {
                if (AngleUnit.normalizeDegrees(aprilTag.getOrientation().getYaw(AngleUnit.DEGREES)) > 90 ||
                AngleUnit.normalizeDegrees(aprilTag.getOrientation().getYaw(AngleUnit.DEGREES)) < -90) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            }
        }
        return null;
    }
}

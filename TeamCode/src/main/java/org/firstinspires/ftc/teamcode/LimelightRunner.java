package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

public class LimelightRunner {
    Limelight3A limelight;
    public LimelightRunner(Limelight3A limelight, int pipeline){
        this.limelight = limelight;
        limelight.start();
        limelight.pipelineSwitch(pipeline);
    }
    public LimelightRunner(Limelight3A limelight){
        this(limelight, 0);
    }
    public double getDX(int goalTag){
        double DX;
        LLResult result = limelight.getLatestResult();
        DX = Double.NaN;
        if (result != null && result.isValid()) {
            DX = Double.NaN;
            // Get all detected AprilTags
            for (LLResultTypes.FiducialResult f : result.getFiducialResults()) {
                if (f.getFiducialId() == goalTag) {
                    DX = f.getTargetXDegrees() /*+ limeDist*/;
                }
            }
        }
        return DX;
    }
    public double getDX(int[] goalTags){
        int validTags = 0;
        double DX = 0;
        for(int i = 0; i < goalTags.length - 1; i++){
            double currentDX = getDX(goalTags[i]);
            if(!Double.isNaN(currentDX)){
                DX += currentDX;
                validTags++;
            }
        }
        if(validTags != 0){
            return DX/validTags;
        }else{
            return Double.NaN;
        }
    }
    public void switchPipeline(int pipeline){
        limelight.pipelineSwitch(pipeline);
    }
    public Pose2D getBotPose() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid() /*&& result.getFiducialResults().size() >= 2*/) {
            Pose3D BotPose = result.getBotpose();
            return new Pose2D(DistanceUnit.METER,
                    BotPose.getPosition().x * (/*-2*/ConfigManager.getDouble("Limelight scaling x")) + /*0*/ConfigManager.getDouble("Limelight offset x")/*limelight outputs a value between 0 and ~ -1.8, so adjust to meters*/,
                    BotPose.getPosition().y * (/*0*/ ConfigManager.getDouble("limelight scaling y")) + /*1.8*/ConfigManager.getDouble("Limelight offest y")/*limelight outputs between ~ -1.8 and 1.8, so adjust*/,
                    AngleUnit.RADIANS,
                    BotPose.getOrientation().getYaw(AngleUnit.RADIANS) + /*0*/ConfigManager.getDouble("Limelight rotation offset"));
        }
        return null;
    }
    public Pose3D tagPoseFromCamera(int goalTag){
        LLResult result = limelight.getLatestResult();
        if(result != null && result.isValid()){
            for (LLResultTypes.FiducialResult f : result.getFiducialResults()){
                if(f.getFiducialId() == goalTag){
                    return f.getTargetPoseCameraSpace();
                }
            }
        }
        return null;
    }
}

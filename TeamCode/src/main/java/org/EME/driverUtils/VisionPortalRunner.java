package org.EME.driverUtils;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class VisionPortalRunner {
    public VisionPortal portal;
    AprilTagProcessor aprilProcessor;
    public VisionPortalRunner(VisionPortal portal, AprilTagProcessor aprilProcessor){
        this.portal = portal;
        this.aprilProcessor = aprilProcessor;
    }
    public class SimpleAngleDeg{
        private double angle;
        public SimpleAngleDeg(double angle){
            if(angle >= 0) {
                for (this.angle = angle; this.angle > 360; this.angle -= 360) {}
            }else{
                for (this.angle = angle; this.angle < 0; this.angle += 360) {}
            }
        }
        public double getAngle(){
            return angle;
        }
        public boolean isBetween(SimpleAngleDeg bottom, SimpleAngleDeg top){
            double bottomAngle = bottom.getAngle();
            if (bottomAngle > top.getAngle()) {
                bottomAngle -= 360;
            }
            return angle >= bottomAngle;
        }
        public boolean equals(SimpleAngleDeg check, double tolerance){
            return angle <= check.getAngle() + tolerance && angle >= check.getAngle() - tolerance;
        }
    }
    private boolean comparePoses(Position robotPos, Position checkPos, AprilTagPoseFtc lookPos){
        return robotPos == null || checkPos == null || lookPos == null ||
                ((Double.isNaN(checkPos.x) || (checkPos.x - robotPos.x == lookPos.x)) &&
                (Double.isNaN(checkPos.y) || (checkPos.y - robotPos.y == lookPos.y)) &&
                (Double.isNaN(checkPos.z) || (checkPos.z - robotPos.z == lookPos.z)));
    }
    private boolean compareRotations(YawPitchRollAngles robotRot, YawPitchRollAngles checkRot, AprilTagPoseFtc lookRot){
        return robotRot == null || checkRot == null || lookRot == null ||
                ((Double.isNaN(checkRot.getYaw()) || checkRot.getYaw(AngleUnit.DEGREES) - robotRot.getYaw(AngleUnit.DEGREES) == lookRot.yaw) &&
                (Double.isNaN(checkRot.getPitch()) || checkRot.getPitch(AngleUnit.DEGREES) - robotRot.getPitch(AngleUnit.DEGREES) == lookRot.pitch) &&
                (Double.isNaN(checkRot.getRoll()) ||checkRot.getRoll(AngleUnit.DEGREES) - robotRot.getRoll(AngleUnit.DEGREES) == lookRot.roll));
    }
    //Doesn't quite work because direction matters and this doesn't determine direction
    private boolean isBetween(YawPitchRollAngles robotRot, YawPitchRollAngles checkRot0, YawPitchRollAngles checkRot1, AprilTagPoseFtc lookRot){
        SimpleAngleDeg yaw0 = new SimpleAngleDeg(checkRot0.getYaw(AngleUnit.DEGREES) - robotRot.getYaw(AngleUnit.DEGREES));
        SimpleAngleDeg yaw1 = new SimpleAngleDeg(checkRot1.getYaw(AngleUnit.DEGREES) - robotRot.getYaw(AngleUnit.DEGREES));
        SimpleAngleDeg pitch0 = new SimpleAngleDeg(checkRot0.getPitch(AngleUnit.DEGREES) - robotRot.getPitch(AngleUnit.DEGREES));
        SimpleAngleDeg pitch1 = new SimpleAngleDeg(checkRot1.getPitch(AngleUnit.DEGREES) - robotRot.getPitch(AngleUnit.DEGREES));
        SimpleAngleDeg roll0 = new SimpleAngleDeg(checkRot0.getRoll(AngleUnit.DEGREES) - robotRot.getRoll(AngleUnit.DEGREES));
        SimpleAngleDeg roll1 = new SimpleAngleDeg(checkRot1.getRoll(AngleUnit.DEGREES) - robotRot.getRoll(AngleUnit.DEGREES));
        boolean goodYaw = new SimpleAngleDeg(lookRot.yaw).isBetween(yaw0, yaw1);
        boolean goodPitch = new SimpleAngleDeg(lookRot.pitch).isBetween(pitch0, pitch1);
        boolean goodRoll = new SimpleAngleDeg(lookRot.roll).isBetween(roll0, roll1);
        return goodYaw && goodPitch && goodRoll;
    }
    //Will need a wrapper for speed correction/checking
    public double getDX(String checkFor, Pose3D currentPose, Pose3D targetPos, YawPitchRollAngles checkRot1){
        List<AprilTagDetection> detections = aprilProcessor.getDetections();
        for(int i = 0; i < detections.size(); i++){
            if(detections.get(i) instanceof AprilTagClusterDetection){
                AprilTagClusterDetection detection = (AprilTagClusterDetection) detections.get(i);
                if(checkFor == null || detection.metadata.name.startsWith(checkFor)){
                    if(targetPos != null) {
                        Position robotPos;
                        YawPitchRollAngles robotRot;
                        Position checkPos;
                        YawPitchRollAngles checkRot0;
                        if(currentPose == null) {
                            robotPos = null;
                            robotRot = null;
                        }else{
                            robotPos = currentPose.getPosition();
                            robotRot = currentPose.getOrientation();
                        }
                        checkPos = targetPos.getPosition();
                        checkRot0 = targetPos.getOrientation();
                        if(comparePoses(robotPos, checkPos, detection.ftcPose) &&
                        ((compareRotations(robotRot, checkRot0, detection.ftcPose) && compareRotations(robotRot, checkRot1, detection.ftcPose)) ||
                        (checkRot0 != null && checkRot1 != null && isBetween(robotRot, checkRot0, checkRot1, detection.ftcPose)))){
                            return detection.ftcPose.yaw;
                        }
                    }else{
                        return detection.ftcPose.yaw;
                    }
                }
            }
        }
        return Double.NaN;
    }
    public double getDX(String checkFor, double rawRoll0, double rawRoll1, AngleUnit angleUnit){
        return getDX(checkFor,
                new Pose3D(null, new YawPitchRollAngles(angleUnit, Double.NaN, Double.NaN, 0, 0)),
                new Pose3D(null, new YawPitchRollAngles(angleUnit, Double.NaN, Double.NaN, rawRoll0, 0)),
                new YawPitchRollAngles(angleUnit, Double.NaN, Double.NaN, rawRoll1, 0));
    }
    public double getDX(String checkFor){
        return getDX(checkFor, null, null, null);
    }
}

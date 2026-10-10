package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

public class PedroManager {
    public Follower robot;
    public final PoseFactory poseMaker = PoseFactory.degrees();

    public PedroManager(Pose2D startPose) {
        Scheduler.reset();
        robot = Constants.create(hardwareMap);
        robot.setPose(convertToPose(startPose));
        robot.update();
    }
    public PedroManager(Pose startPose) {
        Scheduler.reset();
        robot = Constants.create(hardwareMap);
        robot.setPose(startPose);
        robot.update();
    }

    public static Pose convertToPose(Pose2D toBeConverted) {
        return new Pose(toBeConverted.getX(DistanceUnit.INCH), toBeConverted.getY(DistanceUnit.INCH), toBeConverted.getHeading(AngleUnit.DEGREES));
    }

    public void goToPose(Pose startPose, Pose endPose) {
        Scheduler.schedule(follow(robot, line(startPose, endPose).linear(startPose, endPose)));
        robot.update();
        Scheduler.execute();
        robot.idle();
    }
    public void goToPoses(Pose[] poses){
        for(int i = 0; i < poses.length-1; i++){
            goToPose(poses[i], poses[i+1]);
        }
    }
    public void doAuto(Runnable[] thingsToDo) {
        for (int i = 0; i < thingsToDo.length -1; i++) {
            thingsToDo[i].run();
        }
    }
}

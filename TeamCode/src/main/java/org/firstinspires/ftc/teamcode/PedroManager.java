package org.firstinspires.ftc.teamcode;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import java.lang.reflect.InvocationTargetException;

public class PedroManager {
    public Follower robot;
    public static final PoseFactory poseMaker = PoseFactory.degrees();

    //constructors
    public PedroManager(Pose2D startPose, HardwareMap hardwareMap) {
        new PedroManager(convertToPose(startPose), hardwareMap);
    }

    public PedroManager(double x, double y, double heading, HardwareMap hardwareMap) {
        new PedroManager(convertToPose(x, y, heading), hardwareMap);
    }

    public PedroManager(Pose startPose, HardwareMap hardwareMap) {
        Scheduler.reset();
        robot = Constants.create(hardwareMap);
        robot.setPose(convertToPose(startPose.x() + ConfigManager.getDouble("RobotWidth"), startPose.y() + ConfigManager.getDouble("RobotHeight"), startPose.heading()));
        robot.update();
    }

    //converts different things to Poses
    public static Pose convertToPose(Pose2D toBeConverted) {
        return poseMaker.of(toBeConverted.getX(DistanceUnit.INCH), toBeConverted.getY(DistanceUnit.INCH), toBeConverted.getHeading(AngleUnit.DEGREES));
    }
    public static Pose convertToPose(int x, int y, int heading) {
        return poseMaker.of(x, y, heading);
    }
    public static Pose convertToPose(double x, double y, double heading) {
        return poseMaker.of(x, y, heading);
    }

    //makes the robot go to places
    public void goToPose(Pose startPose, Pose endPose) {
        Scheduler.schedule(follow(robot, line(startPose, endPose).linear(startPose, endPose)));
        robot.update();
        Scheduler.execute();
        robot.idle();
    }
    public void onlyGoToPose(Pose startPose, Pose endPose, LinearOpMode opMode) {
        Scheduler.schedule(follow(robot, line(startPose, endPose).linear(startPose, endPose)));
        robot.update();
        Scheduler.execute();
        robot.idle();
        while (robot.isBusy() && opMode.opModeIsActive()) {robot.update();}
    }
    public void goToPoses(Pose[] poses) {
        for(int i = 0; i < poses.length-1; i++){
            goToPose(poses[i], poses[i+1]);
        }
    }

    public Pose getRobotPose() {
        return robot.pose();
    }

    //takes an array of runnables and runs them all
    public void doAuto(Runnable[] thingsToDo) {
        for (int i = 0; i < thingsToDo.length -1; i++) {
            //all while loops in your runnables must have nameOfYourPedroManager.robot.update() in them.

            thingsToDo[i].run();
            robot.update();
        }
    }
}

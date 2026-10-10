package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ExampleAuto extends LinearOpMode {
    private Pose startPose = new Pose(0, 0, 0);
    private Pose[] positionsToGoTo = {
            new Pose(10, 10, 10),
            new Pose(20, 20, 20),
            new Pose(30, 30, 30),
            new Pose(40, 40, 40),
            new Pose(50, 50, 50)
    };
    public PedroManager movementManager = new PedroManager(startPose);

    //example runnable
    Runnable name = () -> {/*do stuff*/};
    //example runnable

    private void intake() {

    }
    private void stopIntake() {

    }
    public void shoot() {

    }

    public void runOpMode() {

        while (opModeIsActive()) {
            movementManager.goToPose(startPose, positionsToGoTo[1]);
            shoot();
            movementManager.goToPose(positionsToGoTo[1], positionsToGoTo[2]);
            intake();
            movementManager.goToPose(positionsToGoTo[2], positionsToGoTo[3]);
            stopIntake();
            movementManager.goToPose(positionsToGoTo[3], positionsToGoTo[4]);
            shoot();
            movementManager.goToPose(positionsToGoTo[4], positionsToGoTo[5]);
            //you have parked, yay you.
        }
    }
}

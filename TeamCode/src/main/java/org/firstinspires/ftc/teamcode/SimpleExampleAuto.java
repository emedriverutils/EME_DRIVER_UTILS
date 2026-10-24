package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

@Autonomous(name = "Simple Example Auto", group = "enigma")
public class SimpleExampleAuto extends LinearOpMode {

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
        Pose startPose = PedroManager.convertToPose(0, 0, 0);
        PedroManager movementManager = new PedroManager(startPose, hardwareMap);
        Pose[] positionsToGoTo = {
                PedroManager.convertToPose(10, 0, 0),
                PedroManager.convertToPose(20, 0, 0),
                PedroManager.convertToPose(30, 0, 0),
                PedroManager.convertToPose(40, 0, 0),
                PedroManager.convertToPose(50, 0, 0)
        };

        waitForStart();
        while (opModeIsActive()) {
            movementManager.goToPose(startPose, positionsToGoTo[0]);
            shoot();
            movementManager.goToPose(positionsToGoTo[0], positionsToGoTo[1]);
            intake();
            movementManager.goToPose(positionsToGoTo[1], positionsToGoTo[2]);
            stopIntake();
            movementManager.goToPose(positionsToGoTo[2], positionsToGoTo[3]);
            shoot();
            movementManager.goToPose(positionsToGoTo[3], positionsToGoTo[4]);
            //you have parked, yay you.
        }
    }
}

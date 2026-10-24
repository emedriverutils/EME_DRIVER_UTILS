package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;
import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Complex Example Auto", group = "enigma")
public class ComplexExampleAuto extends LinearOpMode {
    private Runnable[] thingsToDo = new Runnable[10];

    public void runOpMode() {

        Pose[] positionsToGoTo = {
                PedroManager.convertToPose(9, 9, 0),
                PedroManager.convertToPose(70, 24, 0),
                PedroManager.convertToPose(70, 118, 0),
                PedroManager.convertToPose(118, 118, 0),
                PedroManager.convertToPose(118, 24, 0),
                PedroManager.convertToPose(70, 24, 0)
        };
        PedroManager movementManager = new PedroManager(positionsToGoTo[0], hardwareMap);
        for (int i = 0; i < thingsToDo.length; i++) {

            if (i%2 == 0) {
                Pose startingPlace = positionsToGoTo[i / 2];
                Pose placeToGoTo = positionsToGoTo[i / 2 + 1];

                thingsToDo[i] = () -> {movementManager.onlyGoToPose(startingPlace, placeToGoTo, this);};
            } else {
                thingsToDo[i] = () -> {
                    double timer = System.currentTimeMillis();
                    while (System.currentTimeMillis() < timer + 1000 && opModeIsActive()) {

                    }
                };
            }
        }

        waitForStart();
        if (opModeIsActive()) {
            try {
                movementManager.doAuto(thingsToDo);
            } catch (Exception e) {
                telemetry.addLine("An exception occurred: " + e.getMessage());
                StackTraceElement[] stack = e.getStackTrace();
                for(StackTraceElement element:stack){
                    telemetry.addLine(element.toString());
                }
                telemetry.update();
            }
        }
    }
}

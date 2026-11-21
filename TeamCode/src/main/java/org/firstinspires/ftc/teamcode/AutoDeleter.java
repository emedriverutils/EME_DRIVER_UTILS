package org.firstinspires.ftc.teamcode;

import static java.io.File.separator;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.File;
import java.util.ArrayList;

@Autonomous(name = "Auto Deleter", group = "enigma")
public class AutoDeleter extends LinearOpMode {

    private ArrayList<String> autos = new ArrayList<>();
    File folder = new File(System.getProperty("user.dir") + separator + "sdcard" + separator + "FIRST" + separator + "autos");
    private String[] autoFiles;
    private int mouseLocation = 0;

    private boolean confirmation() {
        while (true) {
            telemetry.addData("do you actually want to delete ", autos.get(mouseLocation));
            telemetry.addLine("press start to confirm.");
            telemetry.addLine("press back to cancel.");
            telemetry.update();
            if (gamepad1.start) {
                return true;
            } else if (gamepad1.back) {
                return false;
            }
        }
    }

    public void runOpMode() {
        autoFiles = folder.list();

        for (int j = 0; j < autoFiles.length; j++) {
            boolean wasAdded = false;
            for (int i = 0; i < autos.size(); i++) {
                if (autos.get(i).compareTo(autoFiles[j]) > 0) {
                    autos.add(i, autoFiles[j]);
                    wasAdded = true;
                    break;
                }
            }
            if (!wasAdded) {
                autos.add(autoFiles[j]);
            }
        }

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("can the code find your file (if it cannot find the file check if it exists)", new Boolean(folder.exists()).toString());

            //mouse up
            if (gamepad1.yWasPressed() && mouseLocation != 0) {
                mouseLocation--;
                //mouse down
            } else if (gamepad1.aWasPressed() && mouseLocation != autos.size() - 1) {
                mouseLocation++;
            }

            for (int k = 0; k < autos.size(); k++) {
                if (mouseLocation == k) {
                    telemetry.addLine("->" + autos.get(k));
                } else {
                    telemetry.addLine(autos.get(k));
                }
            }
            if (gamepad1.right_bumper || gamepad1.left_bumper) {
                if (confirmation()) {
                    new File(folder, autos.get(mouseLocation)).delete();
                    autos.remove(mouseLocation);
                }
            }
            telemetry.update();
        }
    }
}

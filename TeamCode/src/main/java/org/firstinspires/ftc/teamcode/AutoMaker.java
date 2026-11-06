package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

@Autonomous(name = "Auto Maker", group = "enigma")
public class AutoMaker extends LinearOpMode {
    private int mouseLocation = 0;
    private ArrayList<String> autoCommands;
    private boolean commandIsSelected = false;
    private File autoFile;

    //Main menu function
    private void mainMenu() {
        //mouse up
        if (gamepad1.y && mouseLocation != 0) {
            mouseLocation--;
        //mouse down
        } else if (gamepad1.a && mouseLocation != autoCommands.size() - 1) {
            mouseLocation++;
        }

        //add the known commands to telemetry
        for(int i = 0; i < autoCommands.size(); i++){
            if (mouseLocation == i) {
                telemetry.addLine("-> " + autoCommands.get(i));
            } else {
                telemetry.addLine(autoCommands.get(i));
            }
        }
    }

    public void buttons() {
        //Home buttons
        if (gamepad1.right_stick_button || gamepad1.left_stick_button) {
            //go out of the command UI
            commandIsSelected = false;
        }

        //Selecting commands
        if (gamepad1.right_bumper || gamepad1.left_bumper) {
            //go into the command UI
            commandIsSelected = true;
        }
    }

    public void runCommandUIs() {
        if (commandIsSelected) {
            if (!autoCommands.isEmpty()) {
                try {
                    //find which command has been selected
                    AutoCommands.AutoCommand selectedCommand = (AutoCommands.AutoCommand) AutoCommands.class.getDeclaredField(autoCommands.get(mouseLocation)).get(null);

                    //if the commands UI is done exit its UI
                    if (selectedCommand.UI(telemetry, autoFile)) {
                        commandIsSelected = false;
                    }

                    //exeption catch/print thing
                } catch (Exception e) {
                    telemetry.addLine("An exception occurred: " + e.getMessage());
                    StackTraceElement[] stack = e.getStackTrace();
                    for (StackTraceElement element : stack) {
                        telemetry.addLine(element.toString());
                    }
                }
            }else{
                telemetry.addLine("You don't have any auto commands.");
            }
        }else{
            mainMenu();
        }
    }

    //runOpMode
    public void runOpMode() {
        autoCommands = new ArrayList<String>();
        try {
            FileSystems.getDefault().getPath("sdcard", "FIRST", "autos").toFile().createNewFile();
            int filePos = -1;
            for (int i = 0; !FileSystems.getDefault().getPath("sdcard", "FIRST", "autos", "auto" + i + ".txt").toFile().createNewFile(); i++) {filePos = i;}
            autoFile = FileSystems.getDefault().getPath("sdcard", "FIRST", "autos", "auto" + Integer.toString(filePos + 1) + ".txt").toFile();
            try(FileWriter writer = new FileWriter(autoFile)){
                writer.write("auto" + Integer.toString(filePos + 1) + "\n");
            }
        } catch (IOException e){
            telemetry.addLine("An exception occurred: " + e.getMessage());
            StackTraceElement[] stack = e.getStackTrace();
            for (StackTraceElement element : stack) {
                telemetry.addLine(element.toString());
            }
            telemetry.update();
            sleep(5000);
        }
        for(Field command : AutoCommands.class.getDeclaredFields()) {
            if(command.getType().getName().endsWith("AutoCommand")) {
                if(autoCommands.isEmpty()) {
                    autoCommands.add(command.getName());
                } else {
                    for(int i = 0; i < autoCommands.size(); i++) {
                        if(autoCommands.get(i).compareTo(command.getName()) > 0) {
                            autoCommands.add(i, command.getName());
                            break;
                        } else if(i == autoCommands.size() - 1) {
                            autoCommands.add(command.getName());
                            break;
                        }
                    }
                }
            }
        }
        while (opModeInInit()) {
            buttons();
            runCommandUIs();

            telemetry.update();
        }
        waitForStart();
    }
}

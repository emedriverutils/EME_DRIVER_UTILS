package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;
import java.io.FileWriter;

public class AutoCommands {
    public static interface UI {
        public boolean runUI (Telemetry telemetry, HardwareMap hardwareMap, File autoFile);
    }
    public static class AutoCommand {
        private final UI UI;
        private final Runnable run;
        public AutoCommand (UI UI, Runnable run) {
            this.UI = UI;
            this.run = run;
        }
        public boolean UI (Telemetry telemetry, HardwareMap hardwareMap, File autoFile) {
            return UI.runUI (telemetry, hardwareMap, autoFile);
        }
        public void run() {run.run();}
    }
    public static final AutoCommand example = new AutoCommand((Telemetry telemetry, HardwareMap hardwareMap, File autoFile) -> {/*UI*/ return true;}, () -> {/*run*/});
    /*for(Field command: AutoCommands.class.getDeclaredFields()){
        if(command.getGenericType().getTypeName() == "AutoCommand"){
            command.UI();
        }
    }
     */
    public static final AutoCommand move = new AutoCommand(
        new UI() {
            PedroManager robot = null;
            public boolean runUI(Telemetry telemetry, HardwareMap hardwareMap, File autoFile) {/*UI*/
                try {
                    if (robot == null) {
                        robot = new PedroManager(0, 0, 0, hardwareMap);
                    }
                    ManualDrive driver = new ManualDrive();
                    telemetry.addLine("move menu");
                    driver.runWheels();
                    if (GamepadManager.gamepad1.start) {
                        robot.robot.setPose(PedroManager.convertToPose(0, 0, 0));
                    }
                    robot.robot.update();
                    telemetry.addLine(robot.getRobotPose().toString());
                    if (GamepadManager.getOutBool("")) {
                        try (FileWriter fileWriter = new FileWriter(autoFile, true)) {
                            fileWriter.write("move" + robot.getRobotPose().toString());
                        }
                    }
                } catch (Exception e) {
                    //telemetry.addline(TelemetryManager.exeptionPrintout(e));
                }
                return false;
            };
        },
            () -> {/*run*/}
    );
}

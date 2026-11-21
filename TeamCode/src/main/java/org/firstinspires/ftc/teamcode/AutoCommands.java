package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;
import java.io.FileWriter;

public class AutoCommands {
    public static interface UI {
        public boolean runUI (Telemetry telemetry, HardwareMap hardwareMap, File autoFile);
    }
    public static interface BoolReturn {
        public boolean run();
    }
    public static class AutoCommand {
        private final UI UI;
        private final BoolReturn run;
        public AutoCommand (UI UI, BoolReturn run) {
            this.UI = UI;
            this.run = run;
        }
        public boolean UI (Telemetry telemetry, HardwareMap hardwareMap, File autoFile) {
            return UI.runUI (telemetry, hardwareMap, autoFile);
        }
        public boolean run() {return run.run();}
    }
    public static final AutoCommand example = new AutoCommand((Telemetry telemetry, HardwareMap hardwareMap, File autoFile) -> {/*UI*/ return true;}, () -> {/*run*/ return false;});
    /*for(Field command: AutoCommands.class.getDeclaredFields()){
        if(command.getGenericType().getTypeName() == "AutoCommand"){
            command.UI();
        }
    }
     */
    public static final AutoCommand move = new AutoCommand(
        new UI() {
            private PedroManager pedro = null;
            int numParallelCommands = 0;
            public boolean runUI(Telemetry telemetry, HardwareMap hardwareMap, File autoFile) {/*UI*/
                try {
                    if (pedro == null) {
                        pedro = new PedroManager(0, 0, 0, hardwareMap);
                    }
                    ManualDrive driver = new ManualDrive();
                    telemetry.addLine("move menu");
                    telemetry.addData("number of the commands after this one to run at the same time as it", numParallelCommands);
                    driver.runWheels();
                    if (GamepadManager.gamepad1.start) {
                        pedro.robot.setPose(PedroManager.convertToPose(0, 0, 0));
                    }
                    pedro.robot.update();
                    telemetry.addLine(pedro.getRobotPose().toString());
                    if (GamepadManager.gamepad1.yWasPressed()) {
                        numParallelCommands++;
                    } else if (GamepadManager.gamepad1.aWasPressed()) {
                        numParallelCommands--;
                    }
                    if (GamepadManager.gamepad1.right_trigger > 0.25 || GamepadManager.gamepad1.left_trigger > 0.25) {
                        try (FileWriter fileWriter = new FileWriter(autoFile, true)) {
                            fileWriter.write(
                                "move" +
                                PedroManager.roundPose(pedro.getRobotPose(), 3).toString() +
                                " next " + numParallelCommands + " commands" +
                                "\n"
                            );
                            numParallelCommands = 0;
                            return true;
                        }
                    }
                } catch (Exception e) {
                    telemetry.addLine(TelemetryManager.exceptionPrintout(e));
                }
                return false;
            };
        },
            () -> {/*run*/ return false;}
    );
}

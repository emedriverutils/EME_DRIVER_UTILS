package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class AutoCommands {
    public static interface UI{
        public boolean runUI(Telemetry telemetry, File autoFile);
    }
    public static class AutoCommand{
        private final UI UI;
        private final Runnable run;
        public AutoCommand(UI UI, Runnable run){
            this.UI = UI;
            this.run = run;
        }
        public boolean UI(Telemetry telemetry, File autoFile){
            return UI.runUI(telemetry, autoFile);
        }
        public void run(){run.run();}
    }
    public static final AutoCommand example = new AutoCommand((Telemetry telemetry, File autoFile) -> {/*UI*/ return true;}, () -> {/*run*/});
    /*for(Field command: AutoCommands.class.getDeclaredFields()){
        if(command.getGenericType().getTypeName() == "AutoCommand"){
            command.UI();
        }
    }
     */
    public static final AutoCommand move = new AutoCommand(
            (Telemetry telemetry, File autoFile) -> {/*UI*/
                    telemetry.addLine("move menu");
                    return false;
            },
            () -> {/*run*/}
    );
}

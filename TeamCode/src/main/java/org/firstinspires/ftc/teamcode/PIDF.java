package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.controller.PIDFController;

public class PIDF {
    PIDFController controller;
    PIDF(int Kp, int Ki, int Kd, int Kf){
        controller = new PIDFController(Kp, Ki, Kd, Kf);
    }
}

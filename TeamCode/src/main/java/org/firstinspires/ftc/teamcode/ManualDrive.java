package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class ManualDrive {
    public enum NaNTurnBehavior{
        SET_TO_ZERO,
        SET_TO_GAMEPAD_INPUT
    }

    // declare wheels
    public DcMotor leftFrontDrive;
    public DcMotor leftBackDrive;
    public DcMotor rightFrontDrive;
    public DcMotor rightBackDrive;
    public double speed = 1;

    public ManualDrive() {
        leftFrontDrive = HardwareManager.getMotor("FrontLeft");
        leftBackDrive = HardwareManager.getMotor("BackLeft");
        rightFrontDrive = HardwareManager.getMotor("FrontRight");
        rightBackDrive = HardwareManager.getMotor("BackRight");
    }

    public void runWheels() throws IllegalAccessException {
        // Run wheels in POV mode (note: The joystick goes negative when pushed forward, so negate it)
        // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
        // This way it's also easy to just drive straight, or just turn.\
        double boostMultiplier = 1.2;

        double drive = -GamepadManager.getOutDouble("drive") * boostMultiplier;
        double turn = GamepadManager.getOutDouble("turn");
        double strafe = GamepadManager.getOutDouble("strafe") * boostMultiplier;


        // Combine drive and turn for blended motion.
        // variables
        double leftFront = drive + turn + strafe;
        double leftBack = drive + turn - strafe;
        double rightFront = drive - turn - strafe;
        double rightBack = drive - turn + strafe;

        // Normalize the values so neither exceed +/- 1.0

        double max = Math.max(Math.abs(leftFront), Math.abs(rightFront));
        if (max > 1.0) {
            leftFront /= max;
            rightFront /= max;
        }


        // Output the safe vales to the motor drives.
        leftFrontDrive.setPower(leftFront * speed);
        leftBackDrive.setPower(leftBack * speed);
        rightFrontDrive.setPower(rightFront * speed);
        rightBackDrive.setPower(rightBack * speed);
    }
    public void runGivenTurn(double turn, NaNTurnBehavior NaNBehavior) throws IllegalAccessException{
        // Run wheels in POV mode (note: The joystick goes negative when pushed forward, so negate it)
        // In this mode the Left stick moves the robot fwd and back, the turn given rotates it.
        // This way it's easy to drive while using a set turn
        if (Double.isNaN(turn)){
            if (NaNBehavior == NaNTurnBehavior.SET_TO_GAMEPAD_INPUT){
                turn = GamepadManager.getOutDouble("turn");
            }else if (NaNBehavior == NaNTurnBehavior.SET_TO_ZERO){
                turn = 0;
            }
        }
        double boostMultiplier = 1.2;

        double drive = -GamepadManager.getOutDouble("drive") * boostMultiplier;
        double strafe = GamepadManager.getOutDouble("strafe") * boostMultiplier;


        // Combine drive and turn for blended motion.
        // variables
        double leftFront = drive + turn + strafe;
        double leftBack = drive + turn - strafe;
        double rightFront = drive - turn - strafe;
        double rightBack = drive - turn + strafe;

        // Normalize the values so neither exceed +/- 1.0

        double max = Math.max(Math.abs(leftFront), Math.abs(rightFront));
        if (max > 1.0) {
            leftFront /= max;
            rightFront /= max;
        }


        // Output the safe vales to the motor drives.
        leftFrontDrive.setPower(leftFront * speed);
        leftBackDrive.setPower(leftBack * speed);
        rightFrontDrive.setPower(rightFront * speed);
        rightBackDrive.setPower(rightBack * speed);
    }
}

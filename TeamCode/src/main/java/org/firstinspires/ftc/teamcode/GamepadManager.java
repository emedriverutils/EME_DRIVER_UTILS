package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class GamepadManager {
    public static String[] actions = {"drive", "strafe", "turn"};
    public static Map<String, Field> gameActions;
    public static Map<String, Gamepad> gamepads;

    public static void startGamepadManager/*set keybinds*/(Gamepad gamepad1, Gamepad gamepad2) throws Exception{
        gameActions = new HashMap<String, Field>((int) Math.ceil(actions.length/0.75));
        for(String name:actions){
            Gamepad gamepad;
            String[] strings = ConfigManager.config.get(name).split(".");
            if (strings.length != 2){
                throw new Exception("Please put a single dot in your button configurations. You currently have " + strings.length + "dots in your config.");
            }
            if(strings[0].equals("gamepad1")){
                gamepad = gamepad1;
            }else if(strings[0].equals("gamepad2")){
                gamepad = gamepad2;
            }else{
                throw new Exception("Please put a valid gamepad in your button configuration files.");
            }
            try {
                gameActions.put(name, gamepad.getClass().getDeclaredField(strings[1]));
            }catch(NoSuchFieldException e){
                throw new Exception("Please put a valid button in your button configuration files.");
            }
            gamepads.put(name, gamepad);
        }
    }
    public static double getOutDouble(String name) throws IllegalArgumentException, IllegalAccessException{
        return gameActions.get(name).getDouble(gamepads.get(name));
    }
    public static boolean getOutBool(String name) throws IllegalArgumentException, IllegalAccessException{
        return gameActions.get(name).getBoolean(gamepads.get(name));
    }
}

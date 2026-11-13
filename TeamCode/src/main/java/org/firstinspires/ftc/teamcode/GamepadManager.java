package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class GamepadManager {
    public static class GameAction{
        private Gamepad gamepad;
        private Field field;
        public GameAction(Gamepad gamepad, Field field){
            this.gamepad = gamepad;
            this.field = field;
        }
        public GameAction(Field field, Gamepad gamepad){
            this.gamepad = gamepad;
            this.field = field;
        }
        public Gamepad getGamepad(){
            return gamepad;
        }
        public Field getField(){
            return field;
        }
    }
    public static String[] doubleActions = {"drive", "strafe", "turn"};
    public static String[] booleanActions = {};
    private static String[] actions;
    public static Map<String, GameAction> gameActions;
    public static Gamepad gamepad1;
    public static Gamepad gamepad2;

    public static void startGamepadManager/*set keybinds*/(Gamepad gamepad1, Gamepad gamepad2) throws Exception{
        GamepadManager.gamepad1 = gamepad1;
        GamepadManager.gamepad2 = gamepad2;
        actions = new String[doubleActions.length + booleanActions.length];
        if(doubleActions.length != 0) {
            for (int i = 0; i < doubleActions.length; i++) {
                actions[i] = doubleActions[i];
            }
        }
        if(booleanActions.length != 0){
            for(int i = 0; i < booleanActions.length; i++){
                actions[i + doubleActions.length] = booleanActions[i];
            }
        }
        gameActions = new HashMap<String, GameAction>((int) Math.ceil(actions.length/0.75));
        for(String name:actions){
            Gamepad gamepad;
            String[] strings = ConfigManager.config.get(name).split("\\.");
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
                gameActions.put(name, new GameAction(gamepad, gamepad.getClass().getDeclaredField(strings[1])));
            }catch(NoSuchFieldException e){
                throw new Exception("Please put a valid button in your button configuration files.");
            }
        }
    }
    public static double getOutDouble(String name) throws IllegalArgumentException, IllegalAccessException{
        GameAction action = gameActions.get(name);
        return action.getField().getDouble(action.getGamepad());
    }
    public static boolean getOutBool(String name) throws IllegalArgumentException, IllegalAccessException{
        GameAction action = gameActions.get(name);
        return action.getField().getBoolean(action.getGamepad());
    }
}

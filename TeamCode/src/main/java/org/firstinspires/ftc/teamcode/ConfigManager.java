package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager{
    public static Map<String, String> config;
    public static void startConfigManager(String file, HardwareMap hardwareMap) throws IOException {
        Yaml yaml = new Yaml();
        try(InputStream stream = hardwareMap.appContext.getAssets().open(file) /*InputStream stream = new FileInputStream(file)*/) {
            //you can make a file directly in the robot without uploading using device manager -> open in device explorer
            config = yaml.load(stream);
        }
    }
    public static void startConfigManager(String[] names, String[] binds) throws IOException{
        if (names.length > binds.length){
            throw new IOException("Make sure to give enough binds to construct a map to all the names");
        }
        config = new HashMap<String, String>((int) Math.ceil(names.length/0.75));
        for(int i = 0; i < names.length; i++){
            config.put(names[i], binds[i]);
        }
    }
    public static void startConfigManager(Map<String, String> configuration){
        config = configuration;
    }
    public static double getDouble(String key) throws NullPointerException, NumberFormatException{
        return Double.parseDouble(config.get(key));
    }
    public static double[] getDoubles(String[] keys){
        double[] doubles = new double[keys.length];
        for(int i = 0; i < keys.length; i++){
            doubles[i] = getDouble(keys[i]);
        }
        return doubles;
    }
}

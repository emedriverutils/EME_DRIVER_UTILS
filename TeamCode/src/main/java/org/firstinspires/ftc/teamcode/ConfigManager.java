package org.firstinspires.ftc.teamcode;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager{
    public static Map<String, String> config;
    public static void startConfigManager(String file) throws IOException {
        Yaml yaml = new Yaml();
        try(InputStream stream = ConfigManager.class.getClassLoader().getResourceAsStream(file)) {
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
    public static double[] convertToDouble(String[] keys){
        double[] doubles = new double[keys.length];
        for(int i = 0; i < keys.length; i++){
            doubles[i] = Double.parseDouble(config.get(keys[i]));
        }
        return doubles;
    }
}

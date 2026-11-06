package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TelemetryManager {
    public static class Data{
        private final String caption;
        private final Object obj;
        Data(String caption, Object obj){
            this.caption = caption;
            this.obj = obj;
        }
        public String getCaption(){return caption;}
        public Object getObj(){return obj;}

        public boolean equals(String compareCaption, Object compareObj){
            boolean captionEqual = false;
            boolean dataEqual = false;
            if(caption == null){
                if(compareCaption == null){
                    captionEqual = true;
                }
            }else if(caption.equals(compareCaption)){
                captionEqual = true;
            }
            if(obj == null){
                if(compareObj == null){
                    dataEqual = true;
                }
            }else if(obj.equals(compareObj)){
                dataEqual = true;
            }
            return captionEqual && dataEqual;
        }
        public boolean equals(Data compare){return equals(compare.getCaption(), compare.getObj());}
    }
    public static Telemetry telemetry;
    public static com.bylazar.telemetry.TelemetryManager panels;
    public static ArrayList<Data> telemetryDatas;
    public static ArrayList<Data> panelsDatas;
    public static final Object lineObject = new Object();
    public static Map<Object, ArrayList<Data>> telemetryObjectDatas;
    public static Map<Object, ArrayList<Data>> panelsObjectDatas;
    public static void startTelemetryManager(Telemetry telemetry){
        startTelemetryManager(telemetry, PanelsTelemetry.INSTANCE.getTelemetry());
        telemetryDatas = new ArrayList<>();
        panelsDatas = new ArrayList<>();
        telemetryObjectDatas = new HashMap<>();
        panelsObjectDatas = new HashMap<>();
    }
    public static void startTelemetryManager(Telemetry telemetry, com.bylazar.telemetry.TelemetryManager panels){
        TelemetryManager.telemetry = telemetry;
        TelemetryManager.panels = panels;
    }

    public static void addTelemetryOneTime(String caption, Object data){telemetry.addData(caption, data);}
    public static void addTelemetryOneTime(String line){telemetry.addLine(line);}
    public static void addPanelsOneTime(String caption, Object data){panels.addData(caption, data);}
    public static void addPanelsOneTime(String line){panels.addLine(line);}
    public static void addOneTime(String caption, Object data){
        addTelemetryOneTime(caption, data);
        addPanelsOneTime(caption, data);
    }
    public static void addOneTime(String line){
        addTelemetryOneTime(line);
        addPanelsOneTime(line);
    }

    public static void addTelemetry(String caption, Object data){telemetryDatas.add(new Data(caption, data));}
    public static void addTelemetry(String line){telemetryDatas.add(new Data(line, lineObject));}
    public static void addPanels(String caption, Object data){panelsDatas.add(new Data(caption, data));}
    public static void addPanels(String line){panelsDatas.add(new Data(line, lineObject));}
    public static void add(String caption, Object data){
        addTelemetry(caption, data);
        addPanels(caption, data);
    }
    public static void add(String line){
        addTelemetry(line);
        addPanels(line);
    }

    private static void addToTelemetryKeys(Data data, Object key){
        if(telemetryObjectDatas.get(key) != null){
            telemetryObjectDatas.get(key).add(data);
        }else{
            ArrayList<Data> datas = new ArrayList<Data>();
            datas.add(data);
            telemetryObjectDatas.put(key, datas);
        }
    }
    private static void addToPanelsKeys(Data data, Object key){
        if(panelsObjectDatas.get(key) != null){
            panelsObjectDatas.get(key).add(data);
        }else{
            ArrayList<Data> datas = new ArrayList<Data>();
            datas.add(data);
            panelsObjectDatas.put(key, datas);
        }
    }
    public static void addKeyedTelemetry(String caption, Object data, Object key){
        addTelemetry(caption, data);
        addToTelemetryKeys(new Data(caption, data), key);
    }
    public static void addKeyedTelemetry(String line, Object key){addKeyedTelemetry(line, lineObject, key);}
    public static void addKeyedPanels(String caption, Object data, Object key){
        addPanels(caption, data);
        addToPanelsKeys(new Data(caption, data), key);
    }
    public static void addKeyedPanels(String line, Object key){addKeyedPanels(line, lineObject, key);}
    public static void addKeyed(String caption, Object data, Object key){
        addKeyedTelemetry(caption, data, key);
        addKeyedPanels(caption, data, key);
    }
    public static void addKeyed(String line, Object key){addKeyed(line, lineObject, key);}

    public static boolean removeTelemetry(String caption, Object data){
        for(int i = 0; i < telemetryDatas.size(); i++){
            if(telemetryDatas.get(i).equals(caption, data)){
                telemetryDatas.remove(i);
                return true;
            }
        }
        return false;
    }
    public static boolean removeTelemetry(String line){
        return removeTelemetry(line, lineObject);
    }
    public static boolean removePanels(String caption, Object data){
        for(int i = 0; i < panelsDatas.size(); i++){
            if(panelsDatas.get(i).equals(caption, data)){
                panelsDatas.remove(i);
                return true;
            }
        }
        return false;
    }
    public static boolean removePanels(String line){
        return removePanels(line, lineObject);
    }
    public static boolean[] remove(String caption, Object data){return new boolean[]{removeTelemetry(caption, data), removePanels(caption, data)};}
    public static boolean[] remove(String line){return new boolean[]{removeTelemetry(line), removePanels(line)};}

    public static void updateTelemetry(){
        for(int i = 0; i < telemetryDatas.size(); i++){
            Data data = telemetryDatas.get(i);
            if(lineObject.equals(data.getObj())){
                telemetry.addLine(data.getCaption());
            }else{
                telemetry.addData(data.getCaption(), data.getObj());
            }
        }
        telemetry.update();
    }
    public static void updatePanels(){
        for(int i = 0; i < panelsDatas.size(); i++){
            Data data = panelsDatas.get(i);
            if(lineObject.equals(data.getObj())){
                panels.addLine(data.getCaption());
            }else{
                panels.addData(data.getCaption(), data.getObj());
            }
        }
        panels.update();
    }
    public static void update(){
        updateTelemetry();
        updatePanels();
    }

    public static void updateTelemetryWithKeys(Object[] keys){
        updateTelemetry();
        if(keys != null){
            for(Object key : keys){
                ArrayList<Data> datas = telemetryObjectDatas.get(key);
                if(datas != null) {
                    for (int i = 0; i < datas.size(); i++) {
                        removeTelemetry(datas.get(i).getCaption(), datas.get(i).getObj());
                    }
                }
                telemetryObjectDatas.remove(key);
            }
        }
    }
    public static void updateTelemetryWithKey(Object key){updateTelemetryWithKeys(new Object[]{key});}
    public static void updatePanelsWithKeys(Object[] keys){
        updatePanels();
        if(keys != null){
            for(Object key : keys){
                ArrayList<Data> datas = panelsObjectDatas.get(key);
                if(datas != null){
                    for(int i = 0; i < datas.size(); i++){
                        removePanels(datas.get(i).getCaption(), datas.get(i).getObj());
                    }
                }
                panelsObjectDatas.remove(key);
            }
        }
    }
    public static void updatePanelsWithKey(Object key){updatePanelsWithKeys(new Object[]{key});}
    public static void updateWithKeys(Object[] keys){
        updateTelemetryWithKeys(keys);
        updatePanelsWithKeys(keys);
    }
    public static void updateWithKey(Object key){
        updateTelemetryWithKey(key);
        updatePanelsWithKey(key);
    }
}

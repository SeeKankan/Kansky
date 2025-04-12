package io.seekankan.github.kansky.js;

import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.Message;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class JS {
    private static final String ENGINE_NAME = "nashorn";
    private static final String JS_FOLDER = "js";
    static final ScriptEngine engine = new ScriptEngineManager().getEngineByName(ENGINE_NAME);

    public static void loadJS(){
        Kansky instance = Kansky.getInstance();
        Logger logger = instance.getLogger();
        File file = new File(instance.getDataFolder(),JS_FOLDER);
        if(!file.exists() && !file.isDirectory()) {
            if (file.mkdir()) {
                logger.info("Create js folder");
            } else {
                logger.warning("Create js folder fail.");
            }
        }
        File[] jsFolder = file.listFiles();
        if(jsFolder == null) return;
        TreeSet<JSDescription> descriptions = new TreeSet<>(new JSDescription.JSComparator());
        for(File folderFile : jsFolder){
            if(!folderFile.exists() && !folderFile.isDirectory()) continue;
            File plugYMLFile = new File(folderFile,"plugin.yml");
            YamlConfiguration plugYAML = YamlConfiguration.loadConfiguration(plugYMLFile);
            JSDescription description = new JSDescription(plugYAML,folderFile);
            descriptions.add(description);
        }
        ArrayList<Exception> exceptionList = new ArrayList<>();
        for(JSDescription jsDescription : descriptions){
            File main = jsDescription.getMain();
            Bindings bindings = engine.createBindings();
            bindings.put("__SELF_DIR__",jsDescription.getFolder());
            try (Reader reader = new FileReader(main)) {
                try {
                    engine.eval(reader,bindings);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                exceptionList.add(e);
            }
        }
        if(exceptionList.size() != 0){
            logger.severe("An exception(s) has been create on invoke javascript!");
            exceptionList.forEach(Throwable::printStackTrace);
        }
        logger.info(Message.JS__LOAD_SUCCESS.getMessage());
    }
}

package io.seekankan.github.kansky.kanattribute;

import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AttributeFormat {
    private static YamlConfiguration attributeFormatYAML;
    private static final HashMap<String, String> nameMap = new HashMap<>();

    public static void loadAttributeFormat() {
        attributeFormatYAML = KanskyUtil.getConfig("attribute_name.yml");
        nameMap.clear();
        for(String attributeId : attributeFormatYAML.getKeys(true)) {
            nameMap.put(attributeId,attributeFormatYAML.getString(attributeId,attributeId));
        }
    }
    public static Map<String, String> getNameMap(boolean writeable) {
        return writeable ? nameMap : Collections.unmodifiableMap(nameMap);
    }
    public static String format(String attrId) {
        return format(attrId,attrId);
    }
    public static String format(String attrId,String def) {
        return nameMap.getOrDefault(attrId,def);
    }

}

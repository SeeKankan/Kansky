package io.seekankan.github.kansky.js;

import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class JSDescription {
    public static class JSComparator implements Comparator<JSDescription> {

        @Override
        public int compare(JSDescription o1, JSDescription o2) {
            if(o1.equals(o2)) return 0;
            else{
                if(Arrays.asList(o1.depends).contains(o2.name)) return 1;
                else if(Arrays.asList(o2.depends).contains(o1.name)) return -1;
                else return o1.name.compareTo(o2.name);
            }
        }
    }

    private @NotNull String name;
    private @NotNull String version;
    private @NotNull File main;
    private @NotNull String[] depends;
    private @NotNull File folder;

    protected JSDescription(@NotNull String name, @NotNull String version, @NotNull File main, @Nullable String[] depends,@NotNull File folder) {
        this.name = name;
        this.version = version;
        this.main = main;
        this.depends = KanskyUtil.getNotNull(depends,new String[0]);
        this.folder = folder;
    }
    public JSDescription(@NotNull ConfigurationSection confSection,@NotNull File dataFolder){
        this.name = Objects.requireNonNull(confSection.getString("name"));
        this.version = Objects.requireNonNull(confSection.getString("version"));
        String tempMain = Objects.requireNonNull(confSection.getString("main"));
        this.main = new File(dataFolder,tempMain);
        List<String> dependList = confSection.getStringList("depend");
        this.depends = dependList != null ? dependList.toArray(new String[0]) : new String[0];
        this.folder = dataFolder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JSDescription)) return false;
        JSDescription that = (JSDescription) o;
        return name.equals(that.name) && version.equals(that.version) && main.equals(that.main) && Arrays.equals(depends, that.depends);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, version, main);
        result = 31 * result + Arrays.hashCode(depends);
        return result;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getVersion() {
        return version;
    }

    public @NotNull File getMain() {
        return main;
    }

    public @NotNull File getFolder() {
        return folder;
    }

    public String[] getDepends() {
        return depends;
    }
}

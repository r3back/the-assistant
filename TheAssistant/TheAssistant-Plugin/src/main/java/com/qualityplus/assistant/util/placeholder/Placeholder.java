package com.qualityplus.assistant.util.placeholder;

import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.math.MathUtils;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Placeholder implements IPlaceholder {
    private final String key;
    private final String value;
    private List<String> toReplace;

    public Placeholder(String key, String value) {
        this.key = "%" + key + "%";
        this.value = value;
    }

    public Placeholder(String key, int value) {
        this.key = "%" + key + "%";
        this.value = String.valueOf(value);
    }

    public Placeholder(String key, double value) {
        this.key = "%" + key + "%";
        this.value = MathUtils.round(value);
    }

    public Placeholder(String key, List<String> value) {
        this.key = "%" + key + "%";
        this.value = "";
        this.toReplace = value;
    }

    @Override
    public final String process(String line) {
        return line == null ? "" : line.replace(this.key, this.value);
    }

    @Override
    public List<String> processList(List<String> lore) {
        int specificIndex = getLine(lore, key);

        /*Bukkit.getConsoleSender().sendMessage("To Replace: " + key+" | Specific Index: " + specificIndex + " | " + Optional.ofNullable(toReplace)
                .filter(list -> list.size() > 0)
                .map(list -> list.get(0)).orElse("Nullable"));*/

        if (specificIndex != -1) {
            lore.remove(specificIndex);
            if(toReplace != null && toReplace.size() > 0){
                //Bukkit.getConsoleSender().sendMessage("Added");
                lore.addAll(specificIndex, toReplace);
            }
        }
        /*Bukkit.getConsoleSender().sendMessage("-------");
        lore.forEach(line -> Bukkit.getConsoleSender().sendMessage(StringUtils.color(line)));
        Bukkit.getConsoleSender().sendMessage("-------");*/

        return lore;
    }

    private int getLine(List<String> list, String toCheck){
        int init = list.indexOf(key);

        if(init != -1) return init;

        for(int i = 0; i<list.size(); i++)
            if(list.get(i).contains(toCheck)){
                init = i;
                break;
            }


        return init;
    }

    @Override
    public boolean isListPlaceholder() {
        return toReplace != null;
    }
}
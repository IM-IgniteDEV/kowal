package eu.archia.kowal.utils;

import eu.archia.kowal.Kowal;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class File {
    static Kowal inst = Kowal.getInst();
    private static YamlConfiguration dezantyn;
    private static YamlConfiguration idb;

    public static void checkFiles() {
        if (!inst.getDataFolder().exists()) {
            inst.getDataFolder().mkdir();
        }
        if (!new java.io.File(inst.getDataFolder(), "config.yml").exists()) {
            inst.saveDefaultConfig();
        }
        java.io.File m = new java.io.File(inst.getDataFolder(), "kowal.yml");
        if (!m.exists()) {
            inst.saveResource("kowal.yml", true);
        }
        java.io.File db = new java.io.File(Kowal.getInst().getConfig().getString("itemBaseLocation"), "ItemBase.yml");
        if (!db.exists()) {
            try {
                db.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        idb = YamlConfiguration.loadConfiguration(db);

        dezantyn = YamlConfiguration.loadConfiguration(m);
    }

    public static YamlConfiguration getKowal() {
        return dezantyn;
    }

    public static YamlConfiguration getItemBase() {
        return idb;
    }

}


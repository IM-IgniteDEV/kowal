package eu.archia.kowal;

import org.bukkit.plugin.java.JavaPlugin;

public final class Kowal extends JavaPlugin {

    private static Kowal inst;

    @Override
    public void onEnable() {
        inst = this;
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Kowal getInst (){
        return inst;
    }
}

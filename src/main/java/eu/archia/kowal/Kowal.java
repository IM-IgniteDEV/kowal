package eu.archia.kowal;

import eu.archia.kowal.guilistener.AnvilClickListener;
import eu.archia.kowal.guilistener.InventoryClickListener;
import eu.archia.kowal.utils.File;
import eu.archia.kowal.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.socketbyte.opengui.OpenGUI;

public final class Kowal extends JavaPlugin {

    private static Kowal inst;

    @Override
    public void onEnable() {
        inst = this;
        OpenGUI.INSTANCE.register(this);
        File.checkFiles();
        getServer().getPluginManager().registerEvents(new AnvilClickListener() , this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener() , this);
        Bukkit.getConsoleSender().sendMessage(Utils.colored("&6Uruchamianie: &eKowal"));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Kowal getInst (){
        return inst;
    }
}

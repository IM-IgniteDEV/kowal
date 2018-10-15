package eu.archia.kowal.inventories;

import eu.archia.kowal.Kowal;
import eu.archia.kowal.utils.File;
import eu.archia.kowal.utils.ItemConverter;
import eu.archia.kowal.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.socketbyte.opengui.GUI;
import pl.socketbyte.opengui.ItemBuilder;
import pl.socketbyte.opengui.Rows;
import pl.socketbyte.opengui.SimpleGUI;

import java.util.ArrayList;
import java.util.List;

public class AnvilInventory {
    public static void open(Player p) {
        ItemConverter conv = new ItemConverter(p.getInventory().getItemInMainHand());
        List<String> items = new ArrayList<>();
        items.add(Utils.colored("&aPotrzebujesz do ulepszenia:"));
        conv.getItems().forEach(is -> {
            items.add(Utils.colored("&6- "+is.getItemMeta().getDisplayName()+" &2x"+is.getAmount()));
        });

        ItemStack kowal = File.getItemBase().getItemStack(Kowal.getInst().getConfig().getString("podrecznikKowala"));
        ItemStack kamien = File.getItemBase().getItemStack(Kowal.getInst().getConfig().getString("kamienPerfekcji"));
        ItemStack rynsztunek = File.getItemBase().getItemStack(Kowal.getInst().getConfig().getString("rynsztunekKowala"));


        SimpleGUI gui = new SimpleGUI(new GUI("&4&l$$ &6Ulepsz Przedmiot &5&l$$", Rows.ONE));
        gui.getGuiSettings().setCanDrag(false);
        gui.getGuiSettings().setCanEnterItems(false);

        ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(" ");
        lore.add(Utils.colored("&6Przedmiot ktory ulepszasz"));

        gui.setItem(1, new ItemBuilder(Material.BIRCH_DOOR).setName("&6Ulepsz Przedmiot").setLore(items));
        if (p.getInventory().containsAtLeast(kowal, 1)){
            gui.setItem(2, new ItemBuilder(Material.BIRCH_DOOR).setName("&6Ulepsz Przedmiot za pomoca Podrecznika Kowala").setLore(items));
        }
        else{
            gui.setItem(2, new ItemBuilder(Material.BIRCH_DOOR).setName("&6Ulepsz Przedmiot za pomoca Podrecznika Kowala").setLore(" " , "&cNie posiadasz Podrecznika Kowala!" , "&7"));
        }

        gui.setItem(4, new ItemBuilder(p.getInventory().getItemInMainHand()).setLore(lore));

        gui.setItem(6, new ItemBuilder(Material.BIRCH_DOOR).setName("").setLore(items));
        gui.setItem(7, new ItemBuilder(Material.BIRCH_DOOR).setName("").setLore(""));

    }
}

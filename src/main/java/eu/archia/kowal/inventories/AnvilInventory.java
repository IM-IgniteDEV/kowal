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


        ItemStack kowal = File.getItemBase().getItemStack(Kowal.getInst().getConfig().getString("itemBase.podrecznikKowala"));
        ItemStack kamien = File.getItemBase().getItemStack(Kowal.getInst().getConfig().getString("itemBase.kamienPerfekcji"));
        ItemStack rynsztunek = File.getItemBase().getItemStack(Kowal.getInst().getConfig().getString("itemBase.rynsztunekKowala"));

        ItemStack charcoal = new ItemStack(Material.COAL);
        charcoal.getData().setData((byte) 1);

        SimpleGUI gui = new SimpleGUI(new GUI("&4&l$$ &6Ulepsz Przedmiot &5&l$$", Rows.TWO));
        gui.getGuiSettings().setCanDrag(false);
        gui.getGuiSettings().setCanEnterItems(false);

        ItemStack copy = p.getInventory().getItemInMainHand().clone();
        ItemMeta meta = copy.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(" ");
        lore.add(Utils.colored("&6Przedmiot ktory ulepszasz"));
        copy.getItemMeta().setLore(lore);

//item 1
        gui.setItem(10, new ItemBuilder(Material.ANVIL).setName("&6Ulepsz Przedmiot.").setLore(items));
//item 2
        if (p.getInventory().containsAtLeast(kowal, 1)){
            gui.setItem(12, new ItemBuilder(Material.BOOK).setName("&6Ulepsz Przedmiot z Podrecznikiem Kowala").setLore(items));
        }
        else{
            gui.setItem(12, new ItemBuilder(Material.BOOK).setName("&6Ulepsz Przedmiot za pomoca Podrecznika Kowala").setLore(" " , "&cNie posiadasz Podrecznika Kowala!" , "&7"));
        }

// item 3
        if (p.getInventory().containsAtLeast(kamien, 1)){
            gui.setItem(14, new ItemBuilder(charcoal).setName("&6Ulepsz Przedmiot za pomoca Kamienia Perfekcji").setLore(items));
        }
        else {
            gui.setItem(14, new ItemBuilder(charcoal).setName("&6Ulepsz Przedmiot za pomoca Kamienia Perfekcji").setLore(" " , "&cNie posiadasz Kamienia Perfekcji!"));
        }
// item 4
        gui.setItem(4, new ItemBuilder(copy));
// item 5
        gui.setItem(16, new ItemBuilder(Material.BOOK).setName("").setLore(""));

        gui.openInventory(p);
    }
}

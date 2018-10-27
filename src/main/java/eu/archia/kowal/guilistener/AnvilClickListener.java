package eu.archia.kowal.guilistener;

import eu.archia.kowal.inventories.AnvilInventory;
import eu.archia.kowal.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;


public class AnvilClickListener implements Listener {

    @EventHandler
    public void anvilClick(PlayerInteractEvent e) {
        if (e.getHand().equals(EquipmentSlot.HAND)) {
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getClickedBlock().getType().equals(Material.ANVIL)) {
                    e.setCancelled(true);
                    if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
                        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
                            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("+")) {
                                AnvilInventory.open(e.getPlayer());
                            } else
                                e.getPlayer().sendMessage(Utils.colored("&7[&c&l!&7] Nic nie mozesz zrobic z tym przedmiotem u kowala. mozna ulepszac tylko &6Bizuterie&7, &6Miecze &7i &6Zbroje"));
                        } else
                            e.getPlayer().sendMessage(Utils.colored("&7[&c&l!&7] Nic nie mozesz zrobic z tym przedmiotem u kowala. mozna ulepszac tylko &6Bizuterie&7, &6Miecze &7i &6Zbroje"));
                    } else
                        e.getPlayer().sendMessage(Utils.colored("&7[&c&l!&7] Nic nie mozesz zrobic z tym przedmiotem u kowala. mozna ulepszac tylko &6Bizuterie&7, &6Miecze &7i &6Zbroje"));

                }
            }
        }
    }

}

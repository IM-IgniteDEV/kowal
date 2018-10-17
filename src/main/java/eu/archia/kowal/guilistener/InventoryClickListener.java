package eu.archia.kowal.guilistener;

import eu.archia.kowal.Kowal;
import eu.archia.kowal.utils.File;
import eu.archia.kowal.utils.ItemConverter;
import eu.archia.kowal.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void clickListener(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            if (e.getClickedInventory().getName().contains("Ulepsz Przedmiot")) {
                if (e.getCurrentItem().hasItemMeta()) {
                    if (e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().hasLore()) {

                        Player p = (Player) e.getWhoClicked();
                        ItemStack handItem = p.getInventory().getItemInMainHand();
                        if (handItem.hasItemMeta()) {
                            if (handItem.getItemMeta().hasDisplayName() && handItem.getItemMeta().getDisplayName().contains(Utils.colored("&a&l+"))) {

                                if (e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().contains("Ulepsz Przedmiot.")) {
                                    this.upgrade(p, 0);
                                }
                                if (e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().contains("Ulepsz Przedmiot z Podrecznikiem Kowala")) {
                                    if (p.getInventory().containsAtLeast(File.getItemBase().getItemStack(Kowal.getInst().getConfig().getString("podrecznikKowala")), 1)){
                                        this.upgrade(p, 1);
                                    }
                                }
                                if (e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().contains("Ulepsz Przedmiot za pomoca Kamienia Perfekcji")) {
                                    if (p.getInventory().containsAtLeast(File.getItemBase().getItemStack(Kowal.getInst().getConfig().getString("podrecznikKowala")), 1)) {
                                        this.upgrade(p, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //0 - normal || 1 - kowal || 2 - kamien
    public void upgrade(Player p, int type) {
        ItemConverter item = new ItemConverter(p.getInventory().getItemInMainHand());

        if (item.getUpgrade() >= 9) {
            p.sendMessage(Utils.colored("&7[&c&l!&7] Ten przedmiot jest ulepszony maksymalnie!"));
            return;
        }

        for (ItemStack is : item.getItems()) {
            if (!p.getInventory().containsAtLeast(is, is.getAmount())) {
                p.sendMessage(Utils.colored("&7[&c&l!&7] Nie posiadasz wymaganych przedmiotow"));
                return;
            }
        }
        p.getInventory().removeItem(item.getItems().toArray(new ItemStack[0]));

        if (type == 1) {
            p.getInventory().removeItem(File.getItemBase().getItemStack(Kowal.getInst().getConfig().getString("podrecznikKowala")));
        } else if (type == 2) {
            p.getInventory().removeItem(File.getItemBase().getItemStack(Kowal.getInst().getConfig().getString("kamienPerfekcji")));
        }

        if (Math.random() < item.getChance()) {
            item.setUpgrade(item.getUpgrade() + 1);
            p.getInventory().setItemInMainHand(item.build());
            p.sendMessage(Utils.colored("&7[&a&l!&7] Pomyslnie ulepszono przedmiot"));
        } else {
            if (type == 0) {
                item.setDefaultItemStack(null);
            } else if (type == 1) {
                if (item.getUpgrade() > 0) {
                    item.setUpgrade(item.getUpgrade() - 1);
                }
            }
            p.sendMessage(Utils.colored("&7[&a&l!&7] Nie udalo sie ulepszyc przedmiotu"));

            p.getInventory().setItemInMainHand(item.build());
        }


    }
}

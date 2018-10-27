package eu.archia.kowal.pickuplistener;

import eu.archia.kowal.Kowal;
import eu.archia.kowal.enums.RpgQuality;
import eu.archia.kowal.utils.ItemConverter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PickUp implements Listener {

    @EventHandler
    public void onPickUp(InventoryPickupItemEvent e) {
        Bukkit.getConsoleSender().sendMessage("odpala event");
        ItemStack i = e.getItem().getItemStack();
        if (i.hasItemMeta()) {
            if (i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("+")) {
                ItemConverter item = new ItemConverter(i);
                if (item.getQuality().equals(RpgQuality.LOSOWANIE)) {

                    double chanceRare = Kowal.getInst().getConfig().getDouble("chanceRare");
                    double chanceEpic = Kowal.getInst().getConfig().getDouble("chanceRare");
                    double chanceLegendary = Kowal.getInst().getConfig().getDouble("chanceRare");
                    double chanceArtifact = Kowal.getInst().getConfig().getDouble("chanceRare");

                    double chancePlus1 = Kowal.getInst().getConfig().getDouble("chancePlus1");
                    double chancePlus2 = Kowal.getInst().getConfig().getDouble("chancePlus2");
                    double chancePlus3 = Kowal.getInst().getConfig().getDouble("chancePlus3");


                    double plus = Math.random();
                    double chance = Math.random();
                    //jakosc itemu
                    if (chance > chanceArtifact) {
                        item.setQuality(RpgQuality.ARTEFAKT);
                    } else if (chance > chanceLegendary) {
                        item.setQuality(RpgQuality.LEGENDARNY);
                    } else if (chance > chanceEpic) {
                        item.setQuality(RpgQuality.EPICKI);
                    } else if (chance > chanceRare) {
                        item.setQuality(RpgQuality.RZADKI);
                    } else
                        item.setQuality(RpgQuality.NORMALNY);
                    //plus itemu
                    if (plus > chancePlus1) {
                        item.setUpgrade(1);
                    } else if (plus > chancePlus2) {
                        item.setUpgrade(2);
                    } else if (plus > chancePlus3) {
                        item.setUpgrade(3);
                    } else
                        item.setUpgrade(0);

                    item.build();
                }
            }
        }
    }
}


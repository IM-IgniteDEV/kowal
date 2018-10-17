package eu.archia.kowal.guilistener;

import eu.archia.kowal.inventories.AnvilInventory;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;


public class AnvilClickListener implements Listener {

   @EventHandler
   public void anvilClick (PlayerInteractEvent e){
      if (e.getHand().equals(EquipmentSlot.HAND)){
          if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
              if (e.getClickedBlock().getType().equals(Material.ANVIL)){
                  e.setCancelled(true);
                  AnvilInventory.open(e.getPlayer());
              }
          }
      }
   }

}

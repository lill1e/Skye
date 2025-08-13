import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class InventoryHandler {
    private Set<CustomInventory> inventories;

    public InventoryHandler() {
        inventories = new HashSet<>();
    }

    public void add(CustomInventory inventory) {
        inventories.add(inventory);
    }

    public boolean contains(Inventory inventory) {
        return inventories.stream().map(wrapper -> wrapper.inventory).anyMatch(currInventory -> currInventory.equals(inventory));
    }

    public Optional<CustomInventory> getFirst(Inventory inventory) {
        return inventories.stream().filter(currInventory -> currInventory.inventory.equals(inventory)).findAny();
    }

    public void setInventory(ServerPlayer player, CustomInventory inventory) {
        player.player.openInventory(inventory.inventory);
    }

    public void onOpen(InventoryOpenEvent event) {
        getFirst(event.getInventory()).ifPresent(inventory -> inventory.onOpen(Plugin.playerManager.get(event.getPlayer())));
    }

    public void onClose(InventoryCloseEvent event) {
        getFirst(event.getInventory()).ifPresent(inventory -> inventory.onClose(Plugin.playerManager.get(event.getPlayer())));
    }

    public void onClick(InventoryClickEvent event) {
        getFirst(event.getInventory()).ifPresent(inventory -> inventory.onClick(Plugin.playerManager.get(event.getWhoClicked()), event));
    }
}

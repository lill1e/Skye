import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

abstract public class CustomInventory {
    Inventory inventory;

    public CustomInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    abstract void onOpen(ServerPlayer player);

    abstract void onClose(ServerPlayer player);

    abstract void onClick(ServerPlayer player, InventoryClickEvent event);
}

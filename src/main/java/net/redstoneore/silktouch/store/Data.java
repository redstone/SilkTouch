package net.redstoneore.silktouch.store;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.spongepowered.api.item.inventory.Inventory;

import net.redstoneore.rson.Rson;
import net.redstoneore.silktouch.SilkTouch;

public class Data extends Rson<Data> {
	
	// ----------------------------------------
	// SINGLETON & CONSTRUCT
	// ----------------------------------------
	
	private static transient Data i;
	public static Data get() {
		if (i == null) {
			i = new Data();
			i.setup(Paths.get(SilkTouch.get().getConfigDirectory().toString(), "data.json"), Charset.defaultCharset());
		}
		return i;
	}
	public Data() { }

	// ----------------------------------------
	// FIELDS
	// ----------------------------------------
	
	// backpack inventories, these are individual chest blocks
	private Map<UUID, Inventory> backpacks = new HashMap<UUID, Inventory>();

	// ----------------------------------------
	// BACKPACK METHODS
	// ----------------------------------------

	public void backpackStore(UUID chestUUID, Inventory inventory) {
		this.backpacks.put(chestUUID, inventory);
	}
	
	public Boolean backpackExists(UUID chestUUID) {
		return this.backpacks.containsKey(chestUUID);
	}
	
	public void backpackCleanupData(UUID chestUUID) {
		this.backpacks.remove(chestUUID);
	}
	
	public Inventory backpackGet(UUID chestUUID) {
		return this.backpacks.get(chestUUID);
	}
	
}

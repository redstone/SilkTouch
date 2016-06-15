package net.redstoneore.silktouch.store;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import net.redstoneore.rson.Rson;
import net.redstoneore.silktouch.SilkTouch;
import net.redstoneore.silktouch.store.elements.Backpack;

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
	private Map<String, Backpack> backpacks = new HashMap<String, Backpack>();

	// ----------------------------------------
	// BACKPACK METHODS
	// ----------------------------------------

	public void backpackStore(String backpackId, Backpack inventory) {
		this.backpacks.put(backpackId, inventory);
	}
	
	public Boolean backpackExists(String backpackId) {
		return this.backpacks.containsKey(backpackId);
	}
	
	public void backpackCleanupData(String backpackId) {
		this.backpacks.remove(backpackId);
	}
	
	public Backpack backpackGet(String backpackId) {
		return this.backpacks.get(backpackId);
	}
	
}

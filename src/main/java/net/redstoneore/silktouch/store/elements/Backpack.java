package net.redstoneore.silktouch.store.elements;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.spongepowered.api.item.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.redstoneore.rson.RsonTool;
import net.redstoneore.rson.adapter.type.TypeAdapter;
import net.redstoneore.rson.adapter.type.sponge.TypeAdapterItemStack;

public class Backpack extends TypeAdapter<Backpack> {
	
	// ----------------------------------------
	// FIELDS
	// ----------------------------------------
	
	private static final long serialVersionUID = -981624551583041768L;
	private static Boolean registered = false;
	
	private HashMap<Integer, ItemStack> map = new HashMap<Integer, ItemStack>();
	
	// ----------------------------------------
	// SETUP
	// ----------------------------------------
	
	public static void addAsAdapter() {
		if (registered) return;
		
		RsonTool.get().addAdapter(Backpack.class, new Backpack());
		
		registered = true;
	}
	
	// ----------------------------------------
	// MAP METHODS
	// ----------------------------------------
	// These are just based off regular map
	
	public int size() {
		return this.map.size();
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public boolean containsKey(Integer key) {
		return this.map.containsKey(key);
	}

	public boolean containsValue(ItemStack value) {
		return this.map.containsValue(value);
	}

	public ItemStack get(Integer key) {
		return this.map.get(key);
	}

	public ItemStack put(Integer key, ItemStack value) {
		return this.map.put(key, value);
	}

	public ItemStack remove(ItemStack key) {
		return this.map.remove(key);
	}

	public void clear() {
		this.map.clear();
	}
	
	public Set<Integer> keySet() {
		return this.map.keySet();
	}

	public Collection<ItemStack> values() {
		return this.map.values();
	}

	public Set<Entry<Integer, ItemStack>> entrySet() {
		return this.map.entrySet();
	}
	
	public Long getSerialVersionUID() {
		return serialVersionUID;
	}

	// ----------------------------------------
	// RSON METHODS
	// ----------------------------------------
	
	@Override
	public JsonElement toJsonElement(Backpack src, Type srcType, JsonSerializationContext ctx) {
		JsonArray slots = new JsonArray();
		
		for (Integer key : src.keySet()) {
			JsonObject slot = new JsonObject();
			
			slot.addProperty("slot", key);
			slot.add("is", null);
			
			slots.add(slot);
		}
		
		return slots;
	}

	@Override
	public Backpack valueOf(JsonElement json) {
		Backpack newBackpack = new Backpack();
		
		JsonArray backpack = json.getAsJsonArray();
		
		backpack.forEach((backpackSlot) -> {
			JsonObject slotData = backpackSlot.getAsJsonObject();
			
			Integer slot = slotData.get("slot").getAsInt();
			ItemStack is = TypeAdapterItemStack.from(slotData.get("is"));
			
			newBackpack.put(slot, is);
		});
		
		return newBackpack;
	}
	
}

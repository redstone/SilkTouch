package net.redstoneore.silktouch.criteria.backpack;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.redstoneore.silktouch.criteria.Criteria;
import net.redstoneore.silktouch.criteria.CriteriaListener;
import net.redstoneore.silktouch.criteria.EventInformation;
import net.redstoneore.silktouch.data.SilkTouchKeys;
import net.redstoneore.silktouch.store.Config;

// Pending InventoryItemData implementations
// https://github.com/SpongePowered/SpongeCommon/issues/8
public class CriteriaBackpack extends Criteria {

	// ----------------------------------------
	// SINGLETON
	// ----------------------------------------
	
	private static CriteriaBackpack i = new CriteriaBackpack();
	public static CriteriaBackpack get() { return i; }

	public static final Text BACKPACK_NAME = Text.builder().append(Text.of("Backpack")).color(TextColors.AQUA).toText();
	
	// ----------------------------------------
	// METHODS
	// ----------------------------------------
	
	@Override
	public Item alterItem(BlockSnapshot block, Item item, EventInformation eventInformation) {
		if ( ! Config.get().allowChestAsBackpack) return item;
		if ( item.getItemType() != ItemTypes.CHEST && item.getItemType() != ItemTypes.TRAPPED_CHEST) return item;
		/*
		String backpackId = UUID.randomUUID().toString();
		
		item.offer(Keys.DISPLAY_NAME, CriteriaBackpack.BACKPACK_NAME);
		item.offer(SilkTouchKeys.BACKPACK, Optional.of(backpackId));
		
		
		Inventory inventory = null;
		
		// Create a backpack 
		Backpack backpack = new Backpack();
		
		int i = 0;
		
		while (i < 30) {
			Optional<ItemStack> slotContents = inventory.query(new SlotIndex(8)).peek();
			if (slotContents.isPresent()) {
				backpack.put(i, slotContents.get());
			}
			i++;
		}
		
		Data.get().backpackStore(backpackId, backpack);
		
		eventInformation.setClearDrops(true);
		*/
		
		return item;
	}

	@Override
	public CriteriaListener getListener() {
		return CriteriaBackpackListener.get();
	}

}

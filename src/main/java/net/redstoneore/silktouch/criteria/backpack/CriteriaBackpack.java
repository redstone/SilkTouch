package net.redstoneore.silktouch.criteria.backpack;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.item.ItemTypes;

import net.redstoneore.silktouch.criteria.Criteria;
import net.redstoneore.silktouch.criteria.CriteriaListener;
import net.redstoneore.silktouch.store.Config;

public class CriteriaBackpack extends Criteria {

	// ----------------------------------------
	// SINGLETON
	// ----------------------------------------
	
	private static CriteriaBackpack i = new CriteriaBackpack();
	public static CriteriaBackpack get() { return i; }

	// ----------------------------------------
	// METHODS
	// ----------------------------------------
	
	@Override
	public Item alterItem(BlockSnapshot block, Item item) {
		if ( ! Config.get().allowChestAsBackpack) return item;
		if ( item.getItemType() != ItemTypes.CHEST &&item.getItemType() != ItemTypes.TRAPPED_CHEST) return item;
		
		// TODO: add a unique identifier
		// TODO: store chest contents somewhere 
		// TODO: clear drops from event
		
		return item;
	}

	@Override
	public CriteriaListener getListener() {
		return CriteriaBackpackListener.get();
	}

}

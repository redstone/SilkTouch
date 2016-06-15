package net.redstoneore.silktouch.criteria;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.Item;

public abstract class Criteria {
	
	// Request the criteria to alter an item
	public abstract Item alterItem(BlockSnapshot block, Item item, EventInformation eventInformation);
	
	// Fetch a CriteriaListener, return null if none 
	public abstract CriteriaListener getListener();
	
}

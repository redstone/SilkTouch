package net.redstoneore.silktouch.criteria;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.Item;

public abstract class Criteria {
	
	public abstract Item alterItem(BlockSnapshot block, Item item);
	
	public abstract CriteriaListener getListener();
	
}

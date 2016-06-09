package net.redstoneore.silktouch.criteria.mobspawner;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.item.ItemTypes;

import net.redstoneore.silktouch.criteria.Criteria;
import net.redstoneore.silktouch.criteria.CriteriaListener;

public class CriteriaMobSpawner extends Criteria {

	// ----------------------------------------
	// SINGLETON
	// ----------------------------------------
	
	private static CriteriaMobSpawner i = new CriteriaMobSpawner();
	public static CriteriaMobSpawner get() { return i; }

	// ----------------------------------------
	// METHODS
	// ----------------------------------------
	
	@Override
	public Item alterItem(BlockSnapshot block, Item item) {
		if (item.getItemType() != ItemTypes.MOB_SPAWNER) return item;
		
		// TODO: Pending https://github.com/SpongePowered/SpongeCommon/pull/710
		//item.offer(Keys.SPAWNER_ENTITIES, block.get(Keys.SPAWNER_ENTITIES).get());
		//item.offer(Keys.SPAWNER_NEXT_ENTITY_TO_SPAWN, block.get(Keys.SPAWNER_NEXT_ENTITY_TO_SPAWN).get());
		
		return item;
	}
	
	@Override
	public CriteriaListener getListener() {
		// No listener required
		return null;
	}

}

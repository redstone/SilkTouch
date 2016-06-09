package net.redstoneore.silktouch.criteria.backpack;

import java.util.UUID;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.redstoneore.silktouch.criteria.CriteriaListener;
import net.redstoneore.silktouch.store.Config;
import net.redstoneore.silktouch.store.Data;

public class CriteriaBackpackListener implements CriteriaListener {

	// ----------------------------------------
	// SINGLETON
	// ----------------------------------------
	
	private static CriteriaBackpackListener i = new CriteriaBackpackListener();
	public static CriteriaBackpackListener get() { return i; }
	
	// ----------------------------------------
	// METHODS
	// ----------------------------------------

	@Listener
	public void onChestPlace(ChangeBlockEvent.Place event, @Root Player player) {
		if ( ! Config.get().allowChestAsBackpack) return;
		
		event.getTransactions().stream().forEach((trans) -> {
			BlockSnapshot blockSnapshot = trans.getFinal();
			BlockState block = blockSnapshot.getState();
			
			// TODO: grab a unique identifier
			
			// Ensure its a chest 
			if (block.getType() != BlockTypes.CHEST && block.getType() != BlockTypes.TRAPPED_CHEST) return;
			
			// Location
			if ( ! blockSnapshot.getLocation().isPresent()) return;
			Location<World> location = blockSnapshot.getLocation().get();
			
			if (location.getTileEntity().isPresent()) {					
				Chest chest = (Chest) location.getTileEntity().get();
				
				// TODO: check for unique identifier in database
				// TODO: apply contents to chest
				// TODO: clear from db
			}
		});
	}
	
}

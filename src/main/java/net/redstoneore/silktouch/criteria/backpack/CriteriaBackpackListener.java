package net.redstoneore.silktouch.criteria.backpack;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.redstoneore.silktouch.SilkTouch;
import net.redstoneore.silktouch.criteria.CriteriaListener;
import net.redstoneore.silktouch.data.SilkTouchKeys;
import net.redstoneore.silktouch.store.Config;
import net.redstoneore.silktouch.store.Data;
import net.redstoneore.silktouch.store.elements.Backpack;

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
		// Are backpacks enabled?
		if ( ! Config.get().allowChestAsBackpack) return;
		
		// Get the item in hand
		if ( ! player.getItemInHand().isPresent()) return;
		ItemStack itemStackInHand = player.getItemInHand().get();
		
		// Check if we have the backpack key set
		if ( ! itemStackInHand.get(SilkTouchKeys.BACKPACK).isPresent()) return;
		if ( ! itemStackInHand.get(SilkTouchKeys.BACKPACK).get().isPresent()) return;
		
		// Grab the backpack id
		String backpackId = itemStackInHand.get(SilkTouchKeys.BACKPACK).get().get();
		
		// Check it exists still
		if ( ! Data.get().backpackExists(backpackId)) return;
		
		event.getTransactions().stream().forEach((trans) -> {
			if ( ! trans.isValid()) return;
			
			BlockSnapshot blockSnapshot = trans.getFinal();
			BlockState block = blockSnapshot.getState();
								
			// Ensure its a chest (it should be)
			if (block.getType() != BlockTypes.CHEST && block.getType() != BlockTypes.TRAPPED_CHEST) return;
				
			// Grab the location
			if ( ! blockSnapshot.getLocation().isPresent()) return;
			Location<World> location = blockSnapshot.getLocation().get();
			
			// Remove the backpack id
			itemStackInHand.offer(SilkTouchKeys.BACKPACK, Optional.empty());
			
			// Attempt to apply the contents
			applyContents(location, Data.get().backpackGet(backpackId));
		});
	}
	
	private void applyContents(Location<World> location, Backpack inv) {
		if ( ! location.getTileEntity().isPresent()) {
			// re run later
			SilkTouch.get().getTaskBuilder().execute(
					() -> this.applyContents(location, inv))
					.async().delay(20, TimeUnit.MILLISECONDS)
					.name("SilkTouch - (backpack) apply contents delay").submit(SilkTouch.get().getPlugin());
			
			return;
		}
		
		Chest chest = (Chest) location.getTileEntity().get();
		
		Inventory inventory = chest.getInventory().first();
		
		
	}

	@Override
	public void enabled() {}

	@Override
	public void disabled() {}
	
}

package net.redstoneore.silktouch;

import java.util.Optional;

import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

public class SilkTouchListener {

	private static SilkTouchListener i;
	public static SilkTouchListener get() {
		if (i == null) i = new SilkTouchListener();
		return i;
	}
	
	@Listener
	public void onBreakBlock(ChangeBlockEvent.Break event, @Root Player player) {
		
		// Don't drop anything if we're in creative mode
		if (player.gameMode().get() == GameModes.CREATIVE) return;
		
		// Ensure we're using an item when this broke
		if ( ! player.getItemInHand().isPresent()) return;
		
		// Find the EnchantmentData for that item
		Optional<EnchantmentData> data = player.getItemInHand().get().get(EnchantmentData.class);
		
		// Ensure it exists
		if ( ! data.isPresent()) return;
		
		EnchantmentData enchantmentData = data.get();
		
		// Ensure there is the presence of SILK_TOUCH
		Optional<ItemEnchantment> silkTouch = enchantmentData.enchantments().getAll().stream().filter(enchantment -> enchantment.getEnchantment() == Enchantments.SILK_TOUCH).findFirst();
		if ( ! silkTouch.isPresent()) return;
		
		// Check over each item
		event.getTransactions().stream().forEach((trans) -> {
			BlockSnapshot block = trans.getOriginal();
			
			// Check that it is
			if (Config.get().enableSilkTouchFor.contains(block.getState().getType())) {
				
				if (Config.get().requirePermission) {
					String name = block.getState().getType().getName();
					
					if (( ! player.hasPermission("silktouch." + name))) return;
					
				} 
				
				// Find the drop
				ItemType itemType = null;
				
				Optional<ItemType> oItemType = Sponge.getRegistry().getType(CatalogTypes.ITEM_TYPE, block.getState().getType().getId());			
				if (oItemType.isPresent()) {
					itemType = oItemType.get();
				} else {
					if (block.getState().getType().getItem().isPresent()) {
						itemType = block.getState().getType().getItem().get();
					}
				}
								
				// Create the drop
				ItemStack itemDropping = ItemStack.of(itemType, 1);
				Optional<Entity> optional = player.getLocation().getExtent().createEntity(EntityTypes.ITEM, block.getLocation().get().getPosition());
				if (optional.isPresent()) {
					// Prepare the item 
					Item item = (Item) optional.get();
					item.offer(Keys.REPRESENTED_ITEM, itemDropping.createSnapshot());
					
					/*
					// TODO: store NBT data for spawners, chests, etc, if configured
					if (Config.get().enableNBTStoringFor.contains(block.getState().getType())) {
						
					}
					*/
					
					// And drop it
					block.getLocation().get().getExtent().spawnEntity(
						item,
						Cause.source(EntitySpawnCause.builder()
							.entity(player)
							.type(SpawnTypes.DROPPED_ITEM)
							.build()
						).build());
				}
			}
		});
	}
	
	@Listener
	public void onPlaceBlock(ChangeBlockEvent.Place event, @Root Player player) {
		// TODO: load NBT data
	}
	
}

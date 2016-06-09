package net.redstoneore.silktouch;

import java.util.Optional;

import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;
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

import net.redstoneore.silktouch.criteria.Criteria;
import net.redstoneore.silktouch.criteria.Criterias;
import net.redstoneore.silktouch.store.Config;

public class SilkTouchListener {

	// ----------------------------------------
	// SINGLETON
	// ----------------------------------------
	
	private static SilkTouchListener i;
	public static SilkTouchListener get() {
		if (i == null) i = new SilkTouchListener();
		return i;
	}
	
	// ----------------------------------------
	// METHODS
	// ----------------------------------------
	
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
			
			BlockType blockType = block.getState().getType();
			
			// Check that it is enabled 
			if (Config.get().enableSilkTouchFor.contains(blockType)) {
				
				// do they required permissions?
				if (Config.get().requirePermission) {
					String name = blockType.getName();
					
					if (( ! player.hasPermission("silktouch." + name))) return;
					
				}
				
				// Find the drop
				ItemType itemType = null;
				
				Optional<ItemType> oItemType = Sponge.getRegistry().getType(CatalogTypes.ITEM_TYPE, blockType.getId());			
				if (oItemType.isPresent()) {
					itemType = oItemType.get();
				} else {
					if (blockType.getItem().isPresent()) itemType = blockType.getItem().get();
				}
				
				// Be paranoid 
				if (itemType == null) {
					SilkTouch.get().warn("Could not find itemType for " + blockType.getId() + " - " + blockType.getName());
					return;
				}
				
				// Create the drop
				Optional<Entity> oItem = player.getLocation().getExtent().createEntity(EntityTypes.ITEM, block.getLocation().get().getPosition());
				if (oItem.isPresent()) {
					// Prepare the item 
					Item item = (Item) oItem.get();
					item.offer(Keys.REPRESENTED_ITEM, ItemStack.of(itemType, 1).createSnapshot());
					item.offer(Keys.ITEM_BLOCKSTATE, block.getState()); // copy the state
					
					// Copy Lore
					if (block.get(Keys.ITEM_LORE).isPresent()) {
						item.offer(Keys.ITEM_LORE, block.get(Keys.ITEM_LORE).get());
					}
					
					// Copy Enchantments
					if (block.get(Keys.ITEM_ENCHANTMENTS).isPresent()) {
						item.offer(Keys.ITEM_ENCHANTMENTS, block.get(Keys.ITEM_ENCHANTMENTS).get()); 
					}
					
					// Copy Durability
					if (block.get(Keys.ITEM_DURABILITY).isPresent()) {
						item.offer(Keys.ITEM_DURABILITY, block.get(Keys.ITEM_DURABILITY).get()); 
					}
					
					// Go over our criterias
					for (Criteria criteria : Criterias.get().all()) {
						item = criteria.alterItem(block, item);
					}
					
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
	
}

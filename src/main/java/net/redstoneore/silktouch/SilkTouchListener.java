package net.redstoneore.silktouch;

import java.util.Optional;

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
	public void onBreakBlock(ChangeBlockEvent.Break event) {
		if ( ! event.getCause().first(Player.class).isPresent()) return;
		
		Player player = event.getCause().first(Player.class).get();
		
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
					
					if (( ! player.hasPermission("silktouch." + name) ) &&
						( name.contains(":") && ! player.hasPermission("silktouch." + name.split(":")[1]))
					) {
						return;
					}
				} 
								
				ItemType itemType = null;
				
				Optional<ItemType> isItemType = block.getState().getType().getItem();
				if (isItemType.isPresent()) {
					itemType = isItemType.get();
				} else {
					itemType = SilkTouch.get().findDropFor(block.getState().getType());
				}
				
				// Create the drop
				ItemStack itemDropping = ItemStack.of(itemType, 1);
				Optional<Entity> optional = player.getLocation().getExtent().createEntity(EntityTypes.ITEM, block.getLocation().get().getPosition());
				if (optional.isPresent()) {
					// Prepare the item 
					Item item = (Item) optional.get();
					item.offer(Keys.REPRESENTED_ITEM, itemDropping.createSnapshot());
					
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

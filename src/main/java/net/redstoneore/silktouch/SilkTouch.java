package net.redstoneore.silktouch;

import java.nio.file.Files;
import java.nio.file.Path;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import com.google.inject.Inject;

@Plugin(id = "net.redstoneore.silktouch", name = "SilkTouch")
public class SilkTouch {
	
	private static SilkTouch i;
	public static SilkTouch get() { return i; }
	public SilkTouch() { i = this; }
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path privateConfigDir;
	
	@Inject
	private PluginContainer plugin;
	
	@Listener
	public void enable(GameInitializationEvent event) {
		try {
			if ( ! Files.exists(privateConfigDir)) {
				Files.createDirectories(privateConfigDir);
			}
			Config.get().load().save();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		Sponge.getEventManager().registerListeners(this, SilkTouchListener.get());
	}
	
	public PluginContainer getPlugin() {
		return this.plugin;
	}
	
	public Path getConfigDirectory() {
		return this.privateConfigDir;
	}
	
	public ItemType findDropFor(BlockType type) {
		if (type == BlockTypes.CAKE) return ItemTypes.CAKE;
		
		return null;
	}
	
}

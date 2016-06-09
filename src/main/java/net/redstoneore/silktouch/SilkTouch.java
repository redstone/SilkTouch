package net.redstoneore.silktouch;

import java.nio.file.Files;
import java.nio.file.Path;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import com.google.inject.Inject;

import net.redstoneore.silktouch.store.Config;

@Plugin(id = "net.redstoneore.silktouch", name = "SilkTouch")
public class SilkTouch {
	
	// ----------------------------------------
	// SINGLETON
	// ----------------------------------------
	
	private static SilkTouch i;
	public static SilkTouch get() { return i; }
	public SilkTouch() { i = this; }
	
	// ----------------------------------------
	// FIELDS
	// ----------------------------------------
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path privateConfigDir;
	
	@Inject
	private PluginContainer plugin;
	
	// ----------------------------------------
	// METHODS
	// ----------------------------------------
	
	@Listener
	public void enable(GameInitializationEvent event) {
		try {
			if ( ! Files.exists(privateConfigDir)) {
				Files.createDirectories(privateConfigDir);
			}
			Config.get().load().save().update();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		Sponge.getEventManager().registerListeners(this, SilkTouchListener.get());
	}
	
	public void log(String msg) {
		this.getPlugin().getLogger().info(msg);
	}
	
	public void warn(String msg) {
		this.getPlugin().getLogger().warn(msg);
	}
	
	public PluginContainer getPlugin() {
		return this.plugin;
	}
	
	public Path getConfigDirectory() {
		return this.privateConfigDir;
	}
	
}

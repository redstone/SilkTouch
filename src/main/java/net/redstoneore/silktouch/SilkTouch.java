package net.redstoneore.silktouch;

import java.nio.file.Files;
import java.nio.file.Path;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;

import com.google.inject.Inject;

import net.redstoneore.silktouch.store.Config;
import net.redstoneore.silktouch.store.elements.Backpack;

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
	
	private Task.Builder taskBuilder;
	
	// ----------------------------------------
	// METHODS
	// ----------------------------------------
	
	@Listener
	public void enable(GameInitializationEvent event) {
		this.taskBuilder = Sponge.getScheduler().createTaskBuilder();
		
		try {
			if ( ! Files.exists(privateConfigDir)) {
				Files.createDirectories(privateConfigDir);
			}
			Config.get().load().save().update();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		Sponge.getEventManager().registerListeners(this, SilkTouchListener.get());
		
		// Add our adapter for backpack
		Backpack.addAsAdapter();
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
	
	public Task.Builder getTaskBuilder() {
		return this.taskBuilder;
	}
}

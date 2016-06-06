package net.redstoneore.silktouch.store;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;

import com.google.common.collect.Lists;

import net.redstoneore.rson.Rson;
import net.redstoneore.silktouch.SilkTouch;

public class Config extends Rson<Config> {

	// ----------------------------------------
	// SINGLETON & CONSTRUCT
	// ----------------------------------------
	
	private static transient Config i;
	public static Config get() {
		if (i == null) {
			i = new Config();
			i.setup(Paths.get(SilkTouch.get().getConfigDirectory().toString(), "config.json"), Charset.defaultCharset());
		}
		return i;
	}
	public Config() { }
	
	// ----------------------------------------
	// FIELDS
	// ----------------------------------------
	
	public Boolean requirePermission = false;
	
	public Boolean allowChestAsBackpack = false;
	
	public Boolean storeMobSpawnerType = true;
	
	public List<BlockType> enableSilkTouchFor = Lists.newArrayList(
		BlockTypes.MOB_SPAWNER
	);
		
}

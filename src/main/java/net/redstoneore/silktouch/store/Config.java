package net.redstoneore.silktouch.store;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;

import com.google.common.collect.Lists;

import net.redstoneore.rson.Rson;
import net.redstoneore.rson.tasks.WatchTask;
import net.redstoneore.silktouch.SilkTouch;
import net.redstoneore.silktouch.criteria.Criterias;
import net.redstoneore.silktouch.criteria.backpack.CriteriaBackpack;
import net.redstoneore.silktouch.criteria.mobspawner.CriteriaMobSpawner;

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
	
	// ----------------------------------------
	// METHODS
	// ----------------------------------------
	
	@WatchTask
	public void update() {
		// If the Backpack option is enabled, but there is no criteria for it - add it 
		if (this.allowChestAsBackpack && ! Criterias.get().all().contains(CriteriaBackpack.get())) {
			Criterias.get().addCriteria(CriteriaBackpack.get());
		}
		
		// If the Backpack option is disabled, and there is a criteria for it - remove it 
		if ( ! this.allowChestAsBackpack && Criterias.get().all().contains(CriteriaBackpack.get())) {
			Criterias.get().removeCriteria(CriteriaBackpack.get());
		}
		
		// If the Mob Spawner Type option is enabled, but there is no criteria for it - add it 
		if (this.storeMobSpawnerType && ! Criterias.get().all().contains(CriteriaMobSpawner.get())) {
			Criterias.get().addCriteria(CriteriaMobSpawner.get());
		}
		
		// If the Mob Spawner Type option is disabled, and there is a criteria for it - remove it 
		if ( ! this.storeMobSpawnerType && Criterias.get().all().contains(CriteriaMobSpawner.get())) {
			Criterias.get().removeCriteria(CriteriaMobSpawner.get());
		}
	}
	
}

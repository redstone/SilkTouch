package net.redstoneore.silktouch.data;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.OptionalValue;

public class SilkTouchKeys {
	
	// BACKPACK: stores UUID of Backpack which can be found in our data store 
	public static final Key<OptionalValue<String>> BACKPACK = KeyFactory.makeOptionalKey(String.class, SilkTouchQueries.BACKPACK);
	
}

package net.redstoneore.silktouch;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.block.BlockType;

public class Lists {
	
	public static List<BlockType> toList(Class<BlockType> target, BlockType... values) {
		List<BlockType> newList = new ArrayList<BlockType>();
		for (BlockType object : values) {
			newList.add(object);
		}
		return newList;
	}
	
	public static List<String> toList(Class<String> target, String... values) {
		List<String> newList = new ArrayList<String>();
		for (String object : values) {
			newList.add(object);
		}
		return newList;
	}
	
}

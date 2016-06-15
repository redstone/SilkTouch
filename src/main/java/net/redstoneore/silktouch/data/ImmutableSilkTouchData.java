package net.redstoneore.silktouch.data;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import com.google.common.collect.ComparisonChain;

public class ImmutableSilkTouchData extends AbstractImmutableData<ImmutableSilkTouchData, SilkTouchData> {
	
	// ----------------------------------------
	// CONSTRUCT
	// ----------------------------------------

	public ImmutableSilkTouchData(String backpackId) {
		this.dataBackpackId = backpackId;
	}
	
	// ----------------------------------------
	// FIELDS
	// ----------------------------------------
	
	private String dataBackpackId;

	// ----------------------------------------
	// UTIL METHODS
	// ----------------------------------------
	
	public Optional<String> getBackpackId() {
		return Optional.of(this.dataBackpackId);
	}
	
	public ImmutableValue<Optional<String>> backpacks() {
		return Sponge.getRegistry().getValueFactory()
				.createOptionalValue(SilkTouchKeys.BACKPACK, this.dataBackpackId).asImmutable();
	}
	
	// ----------------------------------------
	// AbstractData METHODS
	// ----------------------------------------
	
	@Override
	public SilkTouchData asMutable() {
		return new SilkTouchData(this.dataBackpackId);
	}
	
	@Override
	public int compareTo(ImmutableSilkTouchData o) {
		return ComparisonChain.start()
				.compare(o.dataBackpackId.hashCode(), this.dataBackpackId.hashCode())
				.result();
	}
	
	@Override
	public int getContentVersion() {
		return 1;
	}

	@Override
	protected void registerGetters() {
		this.registerFieldGetter(SilkTouchKeys.BACKPACK, () -> this.dataBackpackId);
		this.registerKeyValue(SilkTouchKeys.BACKPACK, this::backpacks);
	}

}

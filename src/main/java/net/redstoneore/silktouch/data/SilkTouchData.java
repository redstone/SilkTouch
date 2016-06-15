package net.redstoneore.silktouch.data;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class SilkTouchData extends AbstractData<SilkTouchData, ImmutableSilkTouchData> implements DataSerializable {
	
	// ----------------------------------------
	// CONSTRUCT
	// ----------------------------------------
	
	public SilkTouchData() {
		this(null);
	}
	
	public SilkTouchData(String backpackId) {
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
	
	public Value<Optional<String>> backpacks() {
		return Sponge.getRegistry().getValueFactory()
				.createOptionalValue(SilkTouchKeys.BACKPACK, this.dataBackpackId);
	}
	
	// ----------------------------------------
	// AbstractData METHODS
	// ----------------------------------------
	
	@Override
	protected void registerGettersAndSetters() {
		this.registerFieldGetter(SilkTouchKeys.BACKPACK, () -> this.dataBackpackId);
		this.registerFieldSetter(SilkTouchKeys.BACKPACK, value -> this.dataBackpackId = value.get());
		this.registerKeyValue(SilkTouchKeys.BACKPACK, this::backpacks);
	}
	
	@Override
	public ImmutableSilkTouchData asImmutable() {
		return new ImmutableSilkTouchData(this.dataBackpackId);
	}

	@Override
	public SilkTouchData copy() {
		return new SilkTouchData(this.dataBackpackId);
	}

	@Override
	public Optional<SilkTouchData> fill(DataHolder dataholder, MergeFunction overlap) {
		return Optional.empty();
	}

	@Override
	public Optional<SilkTouchData> from(DataContainer container) {
        if ( ! container.contains(SilkTouchKeys.BACKPACK.getQuery())) return Optional.empty();
        
        String backpackUUID = container.getString(SilkTouchKeys.BACKPACK.getQuery()).get();
        
        return Optional.of(new SilkTouchData(backpackUUID));
	}

	@Override
	public int compareTo(SilkTouchData o) {
		return ComparisonChain.start()
				.compare(o.dataBackpackId.hashCode(), this.dataBackpackId.hashCode())
				.result();
	}

	@Override
	public int getContentVersion() {
		return 1;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
			.add("backpack", this.dataBackpackId)
			.toString();
	}
	
}

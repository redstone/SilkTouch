package net.redstoneore.silktouch.criteria;

public class EventInformation {

	private Boolean cancelled = false;
	private Boolean clearDrops = false;
	private Boolean hardClearDrops = false;
	
	public Boolean isCancelled() {
		return this.cancelled;
	}
	
	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public Boolean doClearDrops() {
		return this.clearDrops;
	}
	
	public void setClearDrops(Boolean clearDrops) {
		this.clearDrops = clearDrops;
	}
	
	public Boolean doHardClearDrops() {
		return this.hardClearDrops;
	}
	
	public void setHardClearDrops(Boolean hardClearDrops) {
		this.hardClearDrops = hardClearDrops;
	}
	
}

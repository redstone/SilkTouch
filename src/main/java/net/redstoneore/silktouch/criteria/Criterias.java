package net.redstoneore.silktouch.criteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.spongepowered.api.Sponge;

public final class Criterias {

	// ----------------------------------------
	// SINGLETON & CONSTRUCT
	// ----------------------------------------
	
	public static Criterias i = new Criterias();
	public static Criterias get() { return i; }
	protected Criterias() { } 
	
	// ----------------------------------------
	// FIELDS
	// ----------------------------------------
	
	private List<Criteria> registeredCriterias = new ArrayList<Criteria>();
	
	// ----------------------------------------
	// METHODS
	// ----------------------------------------
	
	// Add a criteria and register its listener
	public void addCriteria(Criteria criteria) {
		this.registeredCriterias.add(criteria);
		
		if (criteria.getListener() != null) {
			Sponge.getEventManager().registerListeners(this, criteria.getListener());
		}
	}
	
	// Remove a criteria and unregister its listener
	public void removeCriteria(Criteria criteria) {
		if (criteria.getListener() != null) {
			Sponge.getEventManager().unregisterListeners(criteria.getListener());
		}
		
		this.registeredCriterias.remove(criteria);
	}
	
	// Fetch a read only list of all criterias 
	public List<Criteria> all() {
		return Collections.unmodifiableList(this.registeredCriterias);
	}
	
}

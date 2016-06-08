package net.redstoneore.silktouch.criteriadrops;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.Sponge;

public class Criterias {

	// ----------------------------------------
	// SINGLETON & CONSTRUCT
	// ----------------------------------------
	
	public static Criterias i = new Criterias();
	public static Criterias get() { return i; }
	
	// ----------------------------------------
	// FIELDS
	// ----------------------------------------
	
	private List<Criteria> registeredCriterias = new ArrayList<Criteria>();
	
	// ----------------------------------------
	// METHODS
	// ----------------------------------------
	
	public void addCriteria(Criteria criteria) {
		this.registeredCriterias.add(criteria);
		
		if (criteria.getListener() != null) {
			Sponge.getEventManager().registerListeners(this, criteria.getListener());
		}
	}
	
	public void removeCriteria(Criteria criteria) {
		if (criteria.getListener() != null) {
			Sponge.getEventManager().unregisterListeners(criteria.getListener());
		}
		
		this.registeredCriterias.remove(criteria);
	}
	
	public List<Criteria> all() {
		return new ArrayList<Criteria>(this.registeredCriterias);
	}
	
}

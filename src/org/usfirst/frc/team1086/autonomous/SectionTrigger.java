package org.usfirst.frc.team1086.autonomous;

/**
 * Used in CombinedSection & children. 
 * This is used to define when certain autonomous sections should be run.
 * Specifically, it is used to make sure different parts of combined autonomous
 * sections line up. 
 * 
 * @author Jack
 */
public interface SectionTrigger {
	/**
	 * @return whether or not to run an associated autonomous section.
	 */
	public boolean trigger();
}

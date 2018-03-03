package org.usfirst.frc.team1086.autonomous;

import java.util.HashMap;

/**
 * Runs a series of autonomous section concurrently.
 * "section" is a list of triggers and autonomous sections.
 * Each autonomous section will only run while it's trigger is active.
 * This is to allow the user to ensure that the autonomous sections line up
 * as they should. For example, if I am driving forward exactly 100 inches and
 * want to spin a motor after I have gone 50, I can define the trigger for the
 * driving section to always return true and the trigger for the motor spinning
 * to return true if the distance driver is >= 50. This can be extended to run
 * with any number of autonomous sections. Note that both the constructor and
 * isFinished methods must be defined when overriding this class.
 * @author Jack
 */
public abstract class CombinedSection extends AutonomousSection {
	HashMap<SectionTrigger, AutonomousSection> sections = new HashMap<>();
	@Override public void update() {
		sections.forEach((trigger, section) -> {
			if(trigger.trigger()) {
				if(!section.isStarted())
					section.start();
				section.update();
			}
		});
	}
	@Override public void finish() {
		sections.forEach((trigger, section) -> section.finish());
	}
}
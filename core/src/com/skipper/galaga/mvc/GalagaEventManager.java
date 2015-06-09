package com.skipper.galaga.mvc;

import java.util.LinkedList;


public class GalagaEventManager {
	
	private final LinkedList<Float> timer = new LinkedList<Float>();
	private final LinkedList<GalagaEvent> events = new LinkedList<GalagaEvent>();
	
	protected void cleanUp() {
		timer.clear();
		events.clear();
	}
	
	protected void addEvent(float time, GalagaEvent e) {
		timer.add(time);
		events.add(e);
	}
	
	protected void pollEvents(float gameTime) {
		
		if (timer.size() > 0) {
			if (gameTime >= timer.peek()) {
				timer.pop();
				events.pop().start();
			}
		}
	}
}
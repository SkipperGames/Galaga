package com.skipper.galaga.mvc;


import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Array;
import com.skipper.galaga.Settings;


public class GalagaEffectPool {
	
	private static final Array<Array<GalagaEffect>> particleCollection = 
			new Array<Array<GalagaEffect>>(15);
	
	private static final Array<GalagaEffect> effects = new Array<GalagaEffect>();
	
	static {
		
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
		particleCollection.add(new Array<GalagaEffect>());
	}
	
	protected static void draw(float deltaTime) {
		
		if (!Settings.useEffects()) return;
		
		if (effects.size > 0) {
			for (GalagaEffect p : effects) {
				
				if (p.isComplete()) {
					effects.removeValue(p, true);
					particleCollection.get(p.id).add(p);
				}
				else p.draw(GalagaModelView.batch, deltaTime);
			}
		}
	}
	
	protected static void create(float x, float y, GalagaEffect effect) {
		
		if (!Settings.useEffects()) return;
		
		final GalagaEffect temp;
		
		if (particleCollection.get(effect.id).size > 0 ) {
			
			temp = particleCollection.get(effect.id).pop();
			temp.reset();
		}
		else
			temp = new GalagaEffect(effect);
		
		temp.setPosition(x, y);
		effects.add(temp);
	}
	
	protected static void free(GalagaEffect effect) {
		particleCollection.get(effect.id).add(effect);
	}
	
	protected static void cleanUp() {
		
		for (GalagaEffect p : effects) 
			particleCollection.get(p.id).add(p);
		effects.clear();
	}
}

class GalagaEffect extends ParticleEffect {
	
	protected int id = 0;
	
	public GalagaEffect(GalagaEffect effect) {
		super(effect);
		
		this.id = effect.id;
	}
	
	public GalagaEffect(int id, ParticleEffect effect) {
		super(effect);
		
		this.id = id;
		scaleEffect(Constants.EFFECT_SCALE);
	}
}
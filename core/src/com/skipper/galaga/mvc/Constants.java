package com.skipper.galaga.mvc;

public class Constants {

	// box 2d world constants
	protected static final float TIME_STEP = 0.0167f;
	protected static final int VELOCITY_ITERATIONS = 6;
	protected static final int POSITION_ITERATIONS = 2;
	protected static final float MAX_LAG_TIME = 0.1f;
	
	// visual scaling; see comments below
	
	// adjust only this
	protected static final float BOX2D_SCALE = 1 / 80f;
	
	// reference only these
	protected static final float SPRITE_SCALE = BOX2D_SCALE * 2f;
	protected static final float EFFECT_SCALE = SPRITE_SCALE / 3f;
	
	// lights freely adjust
	protected static final float POINT_LIGHT_DISTANCE = 100f * Constants.BOX2D_SCALE;
	protected static final int NUM_LIGHT_RAYS = 32;
}

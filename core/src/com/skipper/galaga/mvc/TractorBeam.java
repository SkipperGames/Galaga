package com.skipper.galaga.mvc;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.skipper.galaga.Resources;

public class TractorBeam implements IGalagaEntity {


	private static final float VCOORD_LEN = 0.312500f - 0.234375f;

	private Animation animation;
	private TextureRegion[] frames;
	private TextureRegion currentFrame;

	private float elapsed = 0f;

	private int state = 0;
	private float length = 0f;
	private Body body;



	public TractorBeam() {

		final TextureRegion region = Resources.getGalagaAtlas().findRegion("tractor_beam");

		final TextureRegion[][] tmp = region.split(46, 80);
		frames = new TextureRegion[3];

		for (int i = 0; i < 3; i++) {
			frames[i] = tmp[0][i];
			frames[i].flip(false, true);
		}

		animation = new Animation(0.167f, frames);
		currentFrame = frames[0];

		body = BodyModel.createDynamic(BodyModel.fixture(BodyModel.coneShape(), true), false);
		body.setUserData(this);
	}

	@Override
	public void draw(float deltaTime) {

		if (elapsed >= 7f) {
			GalagaModelView.tractorBeamDisabled();
			return;
		}

		elapsed += deltaTime;
		currentFrame = animation.getKeyFrame(elapsed, true);

		state = state == 4 ? 4 :
			(int)elapsed == 5 ? 3 :
				(int)elapsed == 2 ? 1 :
					state == 0 ? 0 : 2;

		if (state == 3) {

			state = 4;
			GalagaModelView.disableCollection.add(this);
		}
		else if (state == 1) {

			state = 2;
			GalagaModelView.enableCollection.add(this);
		}

		length = state == 0 ? elapsed * 0.5f : state == 4 ? (7f - elapsed) * 0.5f : 1f;
		currentFrame.setV(currentFrame.getV2() + (VCOORD_LEN * length));

		GalagaModelView.batch.draw(currentFrame, 
				body.getPosition().x - 23f, body.getPosition().y + 8f,
				23f, -8f,
				46f, 80f * length,
				Constants.SPRITE_SCALE, -Constants.SPRITE_SCALE, 
				0f);
	}


	@Override
	public IGalagaEntity enable(Vector2 to) { 

		elapsed = 0f;
		state = 0;
		length = 0f;

		body.setTransform(to, 0f);

		return this;
	}


	@Override
	public void disable(boolean override) {

		elapsed = 0f;
		state = 2;
	}


	@Override
	public void enableBody() { body.setActive(true); }


	@Override
	public void disableBody() { body.setActive(false); }


	@Override
	public int getId() { return -10; }
}
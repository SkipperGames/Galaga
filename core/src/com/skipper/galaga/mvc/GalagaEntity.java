package com.skipper.galaga.mvc;

import box2dLight.Light;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.skipper.galaga.Resources;
import com.skipper.galaga.Settings;


public class GalagaEntity implements IGalagaEntity {

	protected Animation animation;
	protected TextureRegion[] frames;
	protected TextureRegion currentFrame;

	protected float elapsed = 0f;

	protected boolean canUpdate = true;
	protected boolean isOutside = false;

	protected float offsetX = 0f;
	protected float offsetY = 0f;

	protected Body body;
	protected Light light;

	protected float idleFPS;
	protected float walkFPS;



	public GalagaEntity() {};

	public GalagaEntity(String name, int numFrames, float fps) {

		final TextureRegion region = Resources.getGalagaAtlas().findRegion(name);

		offsetX = (float)(region.getRegionWidth() / numFrames) * 0.5f;
		offsetY = region.getRegionHeight() * 0.5f;

		final TextureRegion[][] tmp = 
				region.split(region.getRegionWidth() / numFrames, 
						region.getRegionHeight());
		frames = new TextureRegion[numFrames];

		for (int i = 0; i < numFrames; i++) {
			frames[i] = tmp[0][i];
		}

		animation = new Animation(1f / fps, frames);
		currentFrame = frames[0];
	}

	protected void setAnimationSpeed(float fps) {

		animation.setFrameDuration(1f / fps);
		currentFrame = animation.getKeyFrame(0, true);
	}

	@Override
	public IGalagaEntity enable(Vector2 to) {

		elapsed = 0f;
		isOutside = false;

		body.setTransform(to, 0f);

		GalagaModelView.enableCollection.add(this);

		return this;
	}

	@Override
	public void disable(boolean override) {
		GalagaModelView.disableCollection.add(this);
	}

	@Override
	public void draw(float deltaTime) {

		isOutside = (body.getPosition().x < -3f - (offsetX * Constants.SPRITE_SCALE) ||
				body.getPosition().x > 3f + (offsetX * Constants.SPRITE_SCALE) ||
				body.getPosition().y < -5f - (offsetY * Constants.SPRITE_SCALE) ||
				body.getPosition().y > 5f + (offsetY * Constants.SPRITE_SCALE));

		if (canUpdate) {

			elapsed += deltaTime;
			currentFrame = animation.getKeyFrame(elapsed, true);
		}

		if (!isOutside) {

			GalagaModelView.batch.draw(currentFrame, 
					body.getPosition().x - offsetX, body.getPosition().y - offsetY,
					offsetX, offsetY,
					offsetX * 2f, offsetY * 2f,
					Constants.SPRITE_SCALE, Constants.SPRITE_SCALE, 
					body.getAngle() * 57.295779513082320876798154814105f);
		}
	}

	@Override
	public void enableBody() {

		GalagaModelView.drawCollection.add(this);
		body.setActive(true);
		light.setActive(true);
	}

	@Override
	public void disableBody() {

		GalagaModelView.drawCollection.remove(this);
		body.setLinearVelocity(0f, 0f);
		body.setActive(false);
		light.setActive(false);
	}

	@Override
	public int getId() {
		return -1;
	}

}

class DebugEntity implements IGalagaEntity {

	@Override
	public IGalagaEntity enable(Vector2 to) {
		return this;
	}

	@Override
	public void disable(boolean override) {
		// do nothing
	}

	@Override
	public void draw(float deltaTime) {
		// do nothing
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public void enableBody() {
		// do nothing
	}

	@Override
	public void disableBody() {
		// do nothing
	}

}

class Player extends GalagaEntity {

	private static final float DEFAULT_RPS = 0.25f;
	private static final Vector2 STARTING_POS = new Vector2(0f, -5f + (88f * Constants.BOX2D_SCALE));
	private static float SPEED = 10f;

	private float x1 = 0f;
	private float timer = 0f;
	private float nextShot = 0f;
	private float rps = DEFAULT_RPS;


	public Player() {

		super("player", 1, 1f);

		canUpdate = false;

		Enemy.player = this;

		body = BodyModel.createKinematic(
				BodyModel.fixture(
						BodyModel.circleShape(16f), false));
		body.setUserData(this);
		light = LightModel.light_player.createAndAttachLight(body);
	}

	public void setRPS(float rps) {

		this.rps = 1f / rps;
		timer = 0f;
		nextShot = rps;
	}

	protected void controlPlayerPointer(float xto, boolean shoot) {

		x1 = (xto - 240f) * Constants.BOX2D_SCALE;

		controlPlayer(shoot);
	}

	protected void controlPlayerKeyboard(boolean left, boolean right, boolean shoot) {

		if (left) x1 -= SPEED * Constants.BOX2D_SCALE;
		if (right) x1 += SPEED * Constants.BOX2D_SCALE;

		controlPlayer(shoot);
	}


	private void controlPlayer(boolean isShooting) {

		if (isShooting) {

			if (timer >= nextShot) {

				// shoot
				PlayerBulletPool.take().enable(
						body.getPosition().add(0f, offsetY * Constants.BOX2D_SCALE));

				//
				nextShot = timer + rps;
			}
		}
	}

	@Override
	public IGalagaEntity enable(Vector2 to) {

		elapsed = 0f;
		isOutside = false;

		x1 = 0f;
		timer = 0f;
		nextShot = 0f;

		body.setTransform(STARTING_POS, 0f);
		body.setActive(true);
		light.setActive(true);

		return this;
	}

	@Override
	public void disable(boolean override) {

		GalagaModelView.playerDisabled(true);

		if (!override) {

			GalagaEffectPool.create(
					body.getPosition().x, body.getPosition().y, 
					GalagaModelView.fx_player_destroy);

			GalagaModelView.snd_player_destroyed.play(Settings.volume());
		}
	}

	@Override
	public void draw(float deltaTime) {

		super.draw(deltaTime);

		if (x1 < -3f) x1 = -3f;
		if (x1 > 3f) x1 = 3f;

		body.setLinearVelocity((x1 - body.getPosition().x) * SPEED, 0);
		timer += deltaTime;
	}

	@Override
	public int getId() {
		return 0;
	}
}

class PlayerBullet extends GalagaEntity {

	private static final Vector2 dir = new Vector2(0f, 1400f * Constants.BOX2D_SCALE);

	public PlayerBullet() {
		super("player_bullet", 1, 1f);

		canUpdate = false;

		body = BodyModel.createDynamic(
				BodyModel.fixture(
						BodyModel.lineShape(16f), true), true);
		body.setUserData(this);
		light = LightModel.light_player_bullet.createAndAttachLight(body);
	}

	@Override
	public IGalagaEntity enable(Vector2 to) {

		super.enable(to);

		GalagaModelView.snd_player_shoot.play(Settings.volume() * 0.5f);
		body.setLinearVelocity(dir);

		return this;
	}

	@Override
	public void disable(boolean override) {

		super.disable(override);
		PlayerBulletPool.give(this);
	}

	@Override
	public void draw(float deltaTime) {

		if (isOutside) {
			disable(false);
			return;
		}

		super.draw(deltaTime);
	}

	@Override
	public int getId() {
		return 1;
	}
}

class Enemy extends GalagaEntity {

	protected static Player player;

	public Enemy(String name, int numFrames, float fps) {

		super(name, numFrames, fps);
	}

	@Override
	public int getId() {
		return 10;
	}
}

class EnemyBullet extends Enemy {

	private static final float SPEED = 200f * Constants.BOX2D_SCALE;

	private Vector2 dir;

	public EnemyBullet() {
		super("enemy_bullet", 1, 1f);

		canUpdate = false;

		body = BodyModel.createDynamic(
				BodyModel.fixture(
						BodyModel.lineShape(16f), true), true);
		body.setUserData(this);
		light = LightModel.light_enemy_bullet.createAndAttachLight(body);
	}

	@Override
	public IGalagaEntity enable(Vector2 from) {

		super.enable(from);

		GalagaModelView.snd_enemy_shoot1.play(Settings.volume());

		dir = player.body.getPosition().sub(from).nor().scl(SPEED);

		body.setTransform(from, -(float)Math.atan2(dir.x, dir.y));
		body.setLinearVelocity(dir);

		return this;
	}

	@Override
	public void disable(boolean override) {

		super.disable(override);
		EnemyBulletPool.give(this);
	}

	@Override
	public void draw(float deltaTime) {

		if (isOutside) {
			disable(false);
			return;
		}

		super.draw(deltaTime);
	}
}

class Boss extends Enemy {


	private static final float TRACTOR_HEIGHT = -1.6f;

	protected static Boss tractorUser;

	public Boss() {		

		super("tractor1", 2, 2f);

		idleFPS = 2f;
		walkFPS = 0f;

		body = BodyModel.createDynamic(
				BodyModel.fixture(
						BodyModel.circleShape(16f), false), false);		
		body.setUserData(this);
		light = LightModel.light_enemy.createAndAttachLight(body);
	}

	@Override
	public void disable(boolean override) {

		super.disable(override);

		if (!override) {

			GalagaEffectPool.create(
					body.getPosition().x, body.getPosition().y, 
					GalagaModelView.fx_boss);

			GalagaModelView.snd_enemy_hit2.play(Settings.volume());

			if (tractorUser == this) GalagaModelView.tractorBeamDisabled();
		}

		BossPool.give(this);
	}
}

class BossTransformed extends GalagaEntity {

	public BossTransformed() {
		super("tractor2", 2, 2f);
	}
}

class Arrowhead extends Enemy {

	public Arrowhead() {

		super("arrowhead", 2, 2f);

		idleFPS = 2f;
		walkFPS = 6f;

		body = BodyModel.createDynamic(
				BodyModel.fixture(
						BodyModel.circleShape(11f), false), false);
		body.setUserData(this);
		light = LightModel.light_enemy.createAndAttachLight(body);
	}

	@Override
	public void disable(boolean override) {

		super.disable(override);

		if (!override) {

			GalagaEffectPool.create(
					body.getPosition().x, body.getPosition().y, 
					GalagaModelView.fx_arrowhead);

			GalagaModelView.snd_enemy_hit1.play(Settings.volume());

		}

		ArrowheadPool.give(this);
	}
}

class Bee extends Enemy {

	public Bee() {

		super("bee", 2, 8f);

		idleFPS = 8f;
		walkFPS = 24f;

		body = BodyModel.createDynamic(
				BodyModel.fixture(
						BodyModel.circleShape(10f), false), false);
		body.setUserData(this);
		light = LightModel.light_enemy.createAndAttachLight(body);
	}

	@Override
	public void disable(boolean override) {

		super.disable(override);

		if (!override) {

			GalagaEffectPool.create(
					body.getPosition().x, body.getPosition().y, 
					GalagaModelView.fx_bee);

			GalagaModelView.snd_enemy_hit4.play(Settings.volume());

		}

		BeePool.give(this);
	}
}

class Fly extends Enemy {

	public Fly() {

		super("fly", 2, 4f);

		idleFPS = 4f;
		walkFPS = 12f;

		body = BodyModel.createDynamic(
				BodyModel.fixture(
						BodyModel.circleShape(10f), false), false);
		body.setUserData(this);
		light = LightModel.light_enemy.createAndAttachLight(body);
	}

	@Override
	public void disable(boolean override) {

		super.disable(override);

		if (!override) {

			GalagaEffectPool.create(
					body.getPosition().x, body.getPosition().y, 
					GalagaModelView.fx_fly);

			GalagaModelView.snd_enemy_hit3.play(Settings.volume());

		}

		FlyPool.give(this);
	}
}

class Greenguy extends Enemy {

	public Greenguy() {

		super("greenguy", 2, 3f);

		idleFPS = 3f;
		walkFPS = 6f;

		body = BodyModel.createDynamic(
				BodyModel.fixture(
						BodyModel.circleShape(12f), false), false);
		body.setUserData(this);
		light = LightModel.light_enemy.createAndAttachLight(body);
	}

	@Override
	public void disable(boolean override) {

		super.disable(override);

		if (!override) {

			GalagaEffectPool.create(
					body.getPosition().x, body.getPosition().y, 
					GalagaModelView.fx_greenguy);

			GalagaModelView.snd_enemy_hit3.play(Settings.volume());

		}

		GreenguyPool.give(this);
	}
}

class Heli extends Enemy {

	public Heli() {

		super("heli", 3, 9f);

		idleFPS = 9f;
		walkFPS = 12f;

		body = BodyModel.createDynamic(
				BodyModel.fixture(
						BodyModel.circleShape(15f), false), false);
		body.setUserData(this);
		light = LightModel.light_enemy.createAndAttachLight(body);
	}

	@Override
	public void disable(boolean override) {

		super.disable(override);

		if (!override) {

			GalagaEffectPool.create(
					body.getPosition().x, body.getPosition().y, 
					GalagaModelView.fx_heli);

			GalagaModelView.snd_enemy_hit3.play(Settings.volume());

		}

		HeliPool.give(this);
	}
}

class Scorpion extends Enemy {

	public Scorpion() {

		super("scorpion", 2, 2f);

		idleFPS = 2f;
		walkFPS = 4f;

		body = BodyModel.createDynamic(
				BodyModel.fixture(
						BodyModel.circleShape(11f), false), false);
		body.setUserData(this);
		light = LightModel.light_enemy.createAndAttachLight(body);
	}

	@Override
	public void disable(boolean override) {

		super.disable(override);

		if (!override) {

			GalagaEffectPool.create(
					body.getPosition().x, body.getPosition().y, 
					GalagaModelView.fx_scorpion);

			GalagaModelView.snd_enemy_hit2.play(Settings.volume());

		}

		ScorpionPool.give(this);
	}
}

class Stickguy extends Enemy {

	public Stickguy() {

		super("stickguy", 2, 8f);

		idleFPS = 8f;
		walkFPS = 16f;

		body = BodyModel.createDynamic(
				BodyModel.fixture(
						BodyModel.circleShape(12f), false), false);
		body.setUserData(this);
		light = LightModel.light_enemy.createAndAttachLight(body);
	}

	@Override
	public void disable(boolean override) {

		super.disable(override);

		if (!override) {

			GalagaEffectPool.create(
					body.getPosition().x, body.getPosition().y, 
					GalagaModelView.fx_stickguy);

			GalagaModelView.snd_enemy_hit3.play(Settings.volume());

		}

		StickguyPool.give(this);
	}
}

class Voyager extends Enemy {

	public Voyager() {

		super("voyager", 2, 1f);

		idleFPS = 1f;
		walkFPS = 4f;

		body = BodyModel.createDynamic(
				BodyModel.fixture(
						BodyModel.circleShape(16f), false), false);
		body.setUserData(this);
		light = LightModel.light_enemy.createAndAttachLight(body);
	}

	@Override
	public void disable(boolean override) {

		super.disable(override);

		if (!override) {

			GalagaEffectPool.create(
					body.getPosition().x, body.getPosition().y, 
					GalagaModelView.fx_voyager);

			GalagaModelView.snd_enemy_hit3.play(Settings.volume());

		}

		VoyagerPool.give(this);
	}
}
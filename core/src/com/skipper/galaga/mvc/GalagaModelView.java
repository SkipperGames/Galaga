package com.skipper.galaga.mvc;

import java.util.ArrayList;
import java.util.List;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.skipper.galaga.Resources;
import com.skipper.galaga.Settings;



public class GalagaModelView {


	protected static Player player;
	protected static Boss boss;
	protected static final BossTransformed boss_transformed = new BossTransformed();
	protected static TractorBeam tractor;
	protected static Arrowhead arrowhead;
	protected static Bee bee;
	protected static Fly fly;
	protected static Greenguy greenguy;
	protected static Heli heli;
	protected static Scorpion scorpion;
	protected static Stickguy stickguy;
	protected static Voyager voyager;

	private static final DebugEntity debugEntity = new DebugEntity();

	private static IGalagaEntity debugPlayer = debugEntity;
	private static IGalagaEntity debugTractor = debugEntity;


	protected static final List<IGalagaEntity> drawCollection = 
			new ArrayList<IGalagaEntity>();

	protected static final List<IGalagaEntity> enableCollection = 
			new ArrayList<IGalagaEntity>();

	protected static final List<IGalagaEntity> disableCollection = 
			new ArrayList<IGalagaEntity>();

	// sounds
	protected static Sound snd_challenge_imperfect;
	protected static Sound snd_challenge_perfect;
	protected static Sound snd_challenge_start;
	protected static Sound snd_credit;
	protected static Sound snd_enemy_hit1;
	protected static Sound snd_enemy_hit2;
	protected static Sound snd_enemy_hit3;
	protected static Sound snd_enemy_hit4;
	protected static Sound snd_enemy_kamikaze;
	protected static Sound snd_enemy_shoot1;
	protected static Sound snd_enemy_shoot2;
	protected static Sound snd_transform;
	protected static Sound snd_extra_life;
	protected static Sound snd_captured_destroyed;
	protected static Sound snd_player_captured;
	protected static Sound snd_player_destroyed;
	protected static Sound snd_player_in_tractor;
	protected static Sound snd_rescued;
	protected static Sound snd_player_shoot;
	protected static Sound snd_rank;
	protected static Sound snd_game_start;
	protected static Sound snd_tractor_beam;


	// particle effects
	protected static GalagaEffect fx_arrowhead;
	protected static GalagaEffect fx_bee;
	protected static GalagaEffect fx_fly;
	protected static GalagaEffect fx_greenguy;
	protected static GalagaEffect fx_heli;
	protected static GalagaEffect fx_scorpion;
	protected static GalagaEffect fx_stickguy;
	protected static GalagaEffect fx_voyager;
	protected static GalagaEffect fx_boss;
	protected static GalagaEffect fx_boss_transform;
	protected static GalagaEffect fx_tractor;
	protected static GalagaEffect fx_player_destroy;
	protected static GalagaEffect fx_capture_destroy;
	protected static GalagaEffect fx_sparkle;
	protected static ParticleEffect fx_star_field;


	// events TODO: pool events!
	private static GalagaEventManager eventManager = new GalagaEventManager();

	private static float elapsedGameTime = 0f;

	// box2d
	protected static final World world = new World(new Vector2(0f, 0f), false);

	private static float frameTime = 0f;
	private static float accumulator = 0f;

	// lights	
	protected static RayHandler rayHandler;

	// debug renderer
	private static final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

	public static boolean useDebugRender = false;

	// ui
	private static GalagaStats stats;

	// cameras
	private static final OrthographicCamera box2d_camera = new OrthographicCamera(
			480f * Constants.BOX2D_SCALE, 800f * Constants.BOX2D_SCALE);

	private static final OrthographicCamera ui_camera = new OrthographicCamera(480f, 800f);

	// sprite batch
	protected static final SpriteBatch batch = new SpriteBatch();

	// init
	private static boolean isInitialized = false;



	public static void init() {

		// world
		world.setContactListener(new GalagaContactListener());

		// lights
		RayHandler.setGammaCorrection(false);
		RayHandler.useDiffuseLight(false);

		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(box2d_camera.combined);

		rayHandler.setShadows(true);
		rayHandler.setBlur(false);
		rayHandler.setCulling(true);
		rayHandler.setAmbientLight(Color.BLACK);

		// cameras
		batch.setProjectionMatrix(box2d_camera.projection);

		// entities
		player = new Player();
		boss = new Boss();
		tractor = new TractorBeam();
		arrowhead = new Arrowhead();
		bee = new Bee();
		fly = new Fly();
		greenguy = new Greenguy();
		heli = new Heli();
		scorpion = new Scorpion();
		stickguy = new Stickguy();
		voyager = new Voyager();

		// sounds
		snd_challenge_imperfect = Resources.sound("challenge_imperfect");
		snd_challenge_perfect = Resources.sound("challenge_perfect");
		snd_challenge_start = Resources.sound("challenge_start");
		snd_credit = Resources.sound("credit");
		snd_enemy_hit1 = Resources.sound("enemy_hit_1");
		snd_enemy_hit2 = Resources.sound("enemy_hit_2");
		snd_enemy_hit3 = Resources.sound("enemy_hit_3");
		snd_enemy_hit4 = Resources.sound("enemy_hit_4");
		snd_enemy_kamikaze = Resources.sound("enemy_kamikaze");
		snd_enemy_shoot1 = Resources.sound("enemy_shoot_1");
		snd_enemy_shoot2 = Resources.sound("enemy_shoot_2");
		snd_transform = Resources.sound("enemy_transform");
		snd_extra_life = Resources.sound("extra_life");
		snd_captured_destroyed = Resources.sound("fighter_captured_destroyed");
		snd_player_captured = Resources.sound("fighter_captured");
		snd_player_destroyed = Resources.sound("fighter_destroyed");
		snd_player_in_tractor = Resources.sound("fighter_in_tractor");
		snd_rescued = Resources.sound("fighter_rescued");
		snd_player_shoot = Resources.sound("fighter_shoot");
		snd_rank = Resources.sound("rank");
		snd_game_start = Resources.sound("theme");
		snd_tractor_beam = Resources.sound("tractor_beam");

		// particles
		GalagaEffectPool.free(fx_arrowhead = new GalagaEffect(0, Resources.effect("arrowhead")));
		GalagaEffectPool.free(fx_bee = new GalagaEffect(1, Resources.effect("bee")));
		GalagaEffectPool.free(fx_fly = new GalagaEffect(2, Resources.effect("fly")));
		GalagaEffectPool.free(fx_greenguy = new GalagaEffect(3, Resources.effect("greenguy")));
		GalagaEffectPool.free(fx_heli = new GalagaEffect(4, Resources.effect("heli")));
		GalagaEffectPool.free(fx_scorpion = new GalagaEffect(5, Resources.effect("scorpion")));
		GalagaEffectPool.free(fx_stickguy = new GalagaEffect(6, Resources.effect("stickguy")));
		GalagaEffectPool.free(fx_voyager = new GalagaEffect(7, Resources.effect("voyager")));
		GalagaEffectPool.free(fx_boss = new GalagaEffect(8, Resources.effect("boss")));
		GalagaEffectPool.free(fx_boss_transform = new GalagaEffect(9, Resources.effect("boss_transform")));
		GalagaEffectPool.free(fx_tractor = new GalagaEffect(10, Resources.effect("tractor_effect")));
		GalagaEffectPool.free(fx_player_destroy = new GalagaEffect(11, Resources.effect("player_destroyed")));
		GalagaEffectPool.free(fx_capture_destroy = new GalagaEffect(12, Resources.effect("capture_destroyed")));
		fx_star_field = new ParticleEffect(Resources.effect("starfield"));
		fx_star_field.scaleEffect(Constants.BOX2D_SCALE);
		fx_star_field.setPosition(0f, 5f);

		// ui
		stats = new GalagaStats();
	}


	public static void update() {

		frameTime = Math.min(Gdx.graphics.getDeltaTime(), Constants.MAX_LAG_TIME);
		accumulator += frameTime;

		elapsedGameTime += frameTime;
		eventManager.pollEvents(elapsedGameTime);



		for (IGalagaEntity item : enableCollection) item.enableBody();
		enableCollection.clear();



		while (accumulator >= Constants.TIME_STEP) {

			world.step(Constants.TIME_STEP, 
					Constants.VELOCITY_ITERATIONS, 
					Constants.POSITION_ITERATIONS);
			accumulator -= Constants.TIME_STEP;
		}



		batch.setProjectionMatrix(box2d_camera.projection);
		if (Settings.useLights()) rayHandler.updateAndRender();


		batch.begin();


		fx_star_field.draw(batch, frameTime);
		GalagaEffectPool.draw(frameTime);


		debugTractor.draw(frameTime);


		for (IGalagaEntity item : drawCollection) item.draw(frameTime);


		debugPlayer.draw(frameTime);


		// TODO: remove when development finishes
		if (useDebugRender) debugRenderer.render(world, batch.getProjectionMatrix());


		batch.setProjectionMatrix(ui_camera.projection);
		stats.draw(batch);


		batch.end();



		disableCollection.removeAll(enableCollection);
		for (IGalagaEntity item : disableCollection)  item.disableBody();
		disableCollection.clear();

	}


	public static void startGame() {

		if (!isInitialized) {

			isInitialized = true;

			debugPlayer = player.enable(null);

			//
			//			ArrowheadPool.take().enable(new Vector2(-2.5f, 3f));
			//			BeePool.take().enable(new Vector2(-1.25f, 3f));
			BossPool.take().enable(new Vector2(0f, -1.6f));
			//			EnemyBulletPool.take().shoot(new Vector2(1.25f, 3f));
			//			FlyPool.take().enable(new Vector2(2.5f, 3f));

			//			GreenguyPool.take().enable(new Vector2(-2.5f, 2.25f));
			//			HeliPool.take().enable(new Vector2(-1.25f, 2.25f));
			//			ScorpionPool.take().enable(new Vector2(0f, 2.25f));
			//			StickguyPool.take().enable(new Vector2(1.25f, 2.25f));
			//			VoyagerPool.take().enable(new Vector2(2.5f, 2.25f));

			addEvent(1f, GalagaEvent.ev_tractor_beam);

			setStats(2, 1, 0);

			//snd_game_start.stop();
			//snd_game_start.play(Settings.volume() * 0.8f);


			fx_star_field.start();
		}
	}


	protected static void cleanUp() {

		elapsedGameTime = 0f;
		frameTime = 0f;
		accumulator = 0f;


		// TODO: stop sounds
		snd_game_start.stop();


		playerDisabled(true);
		tractorBeamDisabled();


		drawCollection.addAll(enableCollection);
		for (IGalagaEntity item : drawCollection) item.disable(true);
		for (IGalagaEntity item : disableCollection) item.disableBody();


		enableCollection.clear();
		drawCollection.clear();
		disableCollection.clear();


		eventManager.cleanUp();
		GalagaEffectPool.cleanUp();


		isInitialized = false;

	}


	protected static void addEvent(float time, GalagaEvent ev) {
		eventManager.addEvent(elapsedGameTime + time, ev);
	}

	protected static void addStats(long lives, long ranks, long score) {
		stats.add(lives, ranks, score);
	}

	protected static void setStats(long lives, long ranks, long score) {
		stats.set(lives, ranks, score);
	}


	protected static void playerEnabled() {
		debugPlayer = player.enable(null);
	}


	protected static void playerDisabled(boolean override) {

		debugPlayer = debugEntity;
		if (override) disableCollection.add(player);


		if (stats.getLives() > 0) {

			addEvent(3f, GalagaEvent.ev_init_player);
			stats.add(-1, 0, 0);
		}
		else addEvent(3f, GalagaEvent.ev_game_end);
	}


	protected static void tractorBeamEnabled() {

		debugTractor = tractor;
		tractor.enable(Boss.tractorUser.body.getPosition());

		snd_tractor_beam.stop();
		snd_tractor_beam.loop(Settings.volume() * 0.75f);
	}


	protected static void tractorBeamDisabled() {

		debugTractor = debugEntity;
		snd_tractor_beam.stop();
		snd_player_in_tractor.stop();
	}


	protected static void playerCaptured() {

		snd_tractor_beam.stop();
		snd_player_in_tractor.stop();
		snd_player_in_tractor.loop(Settings.volume() * 0.8f);
		
		tractor.disable(true);
		
		player.body.setAngularVelocity(10f);
	}

}

class GalagaContactListener implements ContactListener {

	//private static Vector2 v1;
	//private static Vector2 v2;

	//private static Body body1;
	//private static Body body2;

	private static IGalagaEntity sprite1;
	private static IGalagaEntity sprite2;

	private static int a;
	private static int b;

	@Override
	public void beginContact(Contact contact) {

		sprite1 = (IGalagaEntity)contact.getFixtureA().getBody().getUserData();
		sprite2 = (IGalagaEntity)contact.getFixtureB().getBody().getUserData();

		a = Math.min(sprite1.getId(), sprite2.getId());
		b = Math.max(sprite1.getId(), sprite2.getId());

		if (a == b) return;


		if (a + b >= 10) {

			sprite1.disable(false);
			sprite2.disable(false);
		}
		else if (a + b == -10) {

			GalagaModelView.playerCaptured();
		}

	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}
}
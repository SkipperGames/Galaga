package com.skipper.galaga.mvc;

import com.badlogic.gdx.utils.Pool;


class PlayerBulletPool extends Pool<PlayerBullet> {
	
	private static PlayerBulletPool pool = new PlayerBulletPool();
	
	public static void give(PlayerBullet object) { pool.free(object); }
	public static PlayerBullet take() { return pool.obtain(); }
	
	@Override
	protected PlayerBullet newObject() {
		return new PlayerBullet();
	}
}

class EnemyBulletPool extends Pool<EnemyBullet> {
	
	private static EnemyBulletPool pool = new EnemyBulletPool();
	
	public static void give(EnemyBullet object) { pool.free(object); }
	public static EnemyBullet take() { return pool.obtain(); }
	
	@Override
	protected EnemyBullet newObject() {
		return new EnemyBullet();
	}
}

class ArrowheadPool extends Pool<Arrowhead> {
	
	private static ArrowheadPool pool = new ArrowheadPool();
	
	public static void give(Arrowhead object) { pool.free(object); }
	public static Arrowhead take() { return pool.obtain(); }
	
	@Override
	protected Arrowhead newObject() {
		return new Arrowhead();
	}
}

class BeePool extends Pool<Bee> {
	
	private static BeePool pool = new BeePool();
	
	public static void give(Bee object) { pool.free(object); }
	public static Bee take() { return pool.obtain(); }
	
	@Override
	protected Bee newObject() {
		return new Bee();
	}
}

class FlyPool extends Pool<Fly> {
	
	private static FlyPool pool = new FlyPool();
	
	public static void give(Fly object) { pool.free(object); }
	public static Fly take() { return pool.obtain(); }
	
	@Override
	protected Fly newObject() {
		return new Fly();
	}
}

class GreenguyPool extends Pool<Greenguy> {
	
	private static GreenguyPool pool = new GreenguyPool();
	
	public static void give(Greenguy object) { pool.free(object); }
	public static Greenguy take() { return pool.obtain(); }
	
	@Override
	protected Greenguy newObject() {
		return new Greenguy();
	}
}

class HeliPool extends Pool<Heli> {
	
	private static HeliPool pool = new HeliPool();
	
	public static void give(Heli object) { pool.free(object); }
	public static Heli take() { return pool.obtain(); }
	
	@Override
	protected Heli newObject() {
		return new Heli();
	}
}

class ScorpionPool extends Pool<Scorpion> {
	
	private static ScorpionPool pool = new ScorpionPool();
	
	public static void give(Scorpion object) { pool.free(object); }
	public static Scorpion take() { return pool.obtain(); }
	
	@Override
	protected Scorpion newObject() {
		return new Scorpion();
	}
}

class StickguyPool extends Pool<Stickguy> {
	
	private static StickguyPool pool = new StickguyPool();
	
	public static void give(Stickguy object) { pool.free(object);}
	public static Stickguy take() { return pool.obtain(); }
	
	@Override
	protected Stickguy newObject() {
		return new Stickguy();
	}
}

class VoyagerPool extends Pool<Voyager> {
	
	private static VoyagerPool pool = new VoyagerPool();
	
	public static void give(Voyager object) { pool.free(object); }
	public static Voyager take() { return pool.obtain(); }
	
	@Override
	protected Voyager newObject() {
		return new Voyager();
	}
}

class BossPool extends Pool<Boss> {
	
	private static BossPool pool = new BossPool();
	
	public static void give(Boss object) { pool.free(object); }
	public static Boss take() { 
		return Boss.tractorUser = pool.obtain();
	}
	
	@Override
	protected Boss newObject() {
		return new Boss();
	}
}
/*
class CapturedPool extends Pool<Captured> {
	
	private static CapturedPool pool = new CapturedPool();
	
	public static void give(Captured object) { pool.free(object); }
	public static Captured take() { return pool.obtain(); }
	
	@Override
	protected Captured newObject() {
		return new Captured(GalagaModelView.captured);
	}
}*/
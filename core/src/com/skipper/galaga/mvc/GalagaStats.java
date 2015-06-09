package com.skipper.galaga.mvc;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.skipper.galaga.Resources;

public class GalagaStats {
	
	private final byte LIFE = 0;
	private final byte RANK_1 = 1;
	private final byte RANK_5 = 2;
	private final byte RANK_10 = 3;
	private final byte RANK_20 = 4;
	private final byte RANK_30 = 5;
	private final byte RANK_50 = 6;
	
	private TextureRegion back;
	
	private GalagaUI rank1;
	private GalagaUI rank5;
	private GalagaUI rank10;
	private GalagaUI rank20;
	private GalagaUI rank30;
	private GalagaUI rank50;
	
	private final Array<Array<GalagaUI>> collection = new Array<Array<GalagaUI>>(7);
	private final Array<GalagaUI> activeCollection = new Array<GalagaUI>();
	
	private Vector2 v = new Vector2();
	private int x, y;
	private long count = 0;
	
	private long lives = 2;
	private long rank = 1;
	private long score = 0L;
	
	private BitmapFont font = new BitmapFont();
	
	public GalagaStats() {
		
		back = new TextureRegion(Resources.getGalagaAtlas().findRegion("button_down"));
		
		rank1 = new GalagaUI(Resources.getGalagaAtlas().findRegion("rank", 0), RANK_1);
		rank5 = new GalagaUI(Resources.getGalagaAtlas().findRegion("rank", 1), RANK_5);
		rank10 = new GalagaUI(Resources.getGalagaAtlas().findRegion("rank", 2), RANK_10);
		rank20 = new GalagaUI(Resources.getGalagaAtlas().findRegion("rank", 3), RANK_20);
		rank30 = new GalagaUI(Resources.getGalagaAtlas().findRegion("rank", 4), RANK_30);
		rank50 = new GalagaUI(Resources.getGalagaAtlas().findRegion("rank", 5), RANK_50);
		
		collection.add(new Array<GalagaUI>());
		collection.add(new Array<GalagaUI>());
		collection.add(new Array<GalagaUI>());
		collection.add(new Array<GalagaUI>());
		collection.add(new Array<GalagaUI>());
		collection.add(new Array<GalagaUI>());
		collection.add(new Array<GalagaUI>());
		
		font.setColor(1f, 1f, 1f, 1f);
	}
	
	public void draw(SpriteBatch batch) {
		
		batch.draw(back, -240f, -400f, 480f, 64f);
		for (GalagaUI item : activeCollection)
			batch.draw(item, 
					item.getPosition().x, item.getPosition().y, 
					item.getRegionWidth(), item.getRegionHeight());
		
		font.draw(batch, "Lives", -236f, y - 1f);
		font.draw(batch, "Rank", -1f, -355f);
		font.draw(batch, "Score " + score, -1f, -384f);
	}
	
	public void update() {
		
		// free up all active ui sprites
		for (GalagaUI t : activeCollection) {
			collection.get(t.getId()).add(t);
		}
		activeCollection.clear();
		
		// update active ui sprites
		
		// lives
		if (lives > 0) {
			
			count = lives = lives > 20 ? 20 : lives;
			
			for (y = 0; y < 2; y++) {
				
				for (x = 0; x < 10; x++) {
					
					if (count == 0) break;
					
					v.x = (x * 15) - 235f;
					v.y = -354f - (y * 16);
					
					count--;
					
					activeCollection.add(obtain(LIFE).setPosition(v));
				}
			}
			
			y = (int)v.y;
		}
		else {
			
			y = -354;
		}
		
		// stage and rank
		v.set(0f, -354f);
		count = rank = rank > 750 ? 750 : rank;
		
		while (count > 0) {
			
			if (count >= 50) {
				
				activeCollection.add(obtain(RANK_50).setPosition(v));
				v.add(rank50.getRegionWidth(), 0f);
				count -= 50;
			} 
			else if (count >= 30) {
					
				activeCollection.add(obtain(RANK_30).setPosition(v));
				v.add(rank30.getRegionWidth(), 0f);
				count -= 30;
					
			} else if (count >= 20) {
				
				activeCollection.add(obtain(RANK_20).setPosition(v));
				v.add(rank20.getRegionWidth(), 0f);
				count -= 20;
			}
			else if (count >= 10) {
				
				activeCollection.add(obtain(RANK_10).setPosition(v));
				v.add(rank10.getRegionWidth(), 0f);
				count -= 10;
			}
			else if (count >= 5) {
								
				activeCollection.add(obtain(RANK_5).setPosition(v));
				v.add(rank5.getRegionWidth(), 0f);
				count -= 5;
			} 
			else {
									
				activeCollection.add(obtain(RANK_1).setPosition(v));
				v.add(rank1.getRegionWidth(), 0f);
				count--;
			}
		}
	}
	
	private GalagaUI obtain(byte id) {		
		return collection.get(id).size > 0 ? collection.get(id).pop() : newObject(id);
	}
	
	private GalagaUI newObject(byte id) {

		switch (id) {
		
		case LIFE:
			return new GalagaUI(GalagaModelView.player.currentFrame, id);
			
		case RANK_1:
			return new GalagaUI(rank1, id);
			
		case RANK_5:
			return new GalagaUI(rank5, id);
			
		case RANK_10:
			return new GalagaUI(rank10, id);
			
		case RANK_20:
			return new GalagaUI(rank20, id);
			
		case RANK_30:
			return new GalagaUI(rank30, id);
			
		case RANK_50:
			return new GalagaUI(rank50, id);
			
		default:
			return null;
		}
	}
	
	public void add(long lives, long ranks, long score) {
		this.lives += lives;
		this.rank += ranks;
		this.score += score;
		
		update();
	}
	
	public void set(long lives, long ranks, long score) {
		this.lives = lives;
		this.rank = ranks;
		this.score = score;
		
		update();
	}
	
	public final long getLives() {
		return lives;
	}
	
	public final long getRank() {
		return rank;
	}
	
	public final long getScore() {
		return score;
	}
}

class GalagaUI extends TextureRegion {
	
	
	private byte id = -1;
	protected byte getId() { return id; }
	
	private final Vector2 pos = new Vector2();
	
	public GalagaUI(TextureRegion region, byte id) {
		super(region);
		
		this.id = id;
	}
	
	protected Vector2 getPosition() { return pos; }
	
	protected GalagaUI setPosition(Vector2 pos) {
		this.pos.set(pos);
		return this;
	}
	
	protected GalagaUI setScale(float value) {
		this.pos.scl(value);
		return this;
	}
}

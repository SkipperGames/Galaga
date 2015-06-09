package com.skipper.galaga;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;



public class LoadingScreen extends Screen {

	
	private boolean isLoaded = false;
	
	private final ProgressBar bar;
	
	
	public LoadingScreen() {
		
		final ProgressBarStyle style = new ProgressBarStyle(progressBarStyle);
		style.knobBefore = style.knob;
		
		bar = new ProgressBar(0f, 1f, 0.1f, false, style);
		bar.setPosition(80f, 400f);
		bar.setSize(320f, 10f);
	}
	
	
	@Override
	protected Screen fillStage() {
		
		stage.addActor(bar);
		
		if (!isLoaded) {
			
			Resources.loadSounds(new String[] {
					"challenge_imperfect",
					"challenge_perfect",
					"challenge_start",
					"credit",
					"enemy_hit_1",
					"enemy_hit_2",
					"enemy_hit_3",
					"enemy_hit_4",
					"enemy_kamikaze",
					"enemy_shoot_1",
					"enemy_shoot_2",
					"enemy_transform",
					"extra_life",
					"fighter_captured_destroyed",
					"fighter_captured",
					"fighter_destroyed",
					"fighter_in_tractor",
					"fighter_rescued",
					"fighter_shoot",
					"rank",
					"theme",
					"tractor_beam",
					});
			
			Resources.loadParticleEffects(new String[] {
					"arrowhead",
					"bee",
					"fly",
					"greenguy",
					"heli",
					"scorpion",
					"stickguy",
					"voyager",
					"boss",
					"boss_transform",
					"tractor_effect",
					"player_destroyed",
					"capture_destroyed",
					"starfield",
					"capture_destroyed",
					});
			
		}		
		
		return this;
	}
	
	
	@Override
	protected void draw() {
		
		if (isLoaded) {
			ScreenStateManager.changeScreen(Screens.GAME);
			return;
		}
		
		isLoaded = Resources.update();
		bar.setValue(Resources.getProgress());
		
		stage.draw();
		
	}
	
	
}
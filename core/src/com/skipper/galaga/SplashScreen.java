package com.skipper.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;



public class SplashScreen extends Screen {

	private static final float DURATION = 5f;
	
	private final Image logo;
	private final Label label;
	
	private float currentTime;
	private float fadeInTime;
	private float fadeOutTime;
	
	private float alpha = 0f;
	
	
	public SplashScreen() {
		
		logo = new Image(skin.getDrawable("logo"));
		logo.setPosition(0, 240);
		
		label = new Label("Skipper Games TM", labelStyle);
		label.setPosition(122, 160);
	}
	
	
	@Override
	protected Screen fillStage() {
		
		stage.addActor(logo);
		stage.addActor(label);
		
		alpha = currentTime = 0f;
		fadeInTime = currentTime + 1f;
		fadeOutTime = DURATION - 1f;
		
		return this;
	}

	
	@Override
	protected void draw() {

		if (currentTime < DURATION) {
			
			alpha = currentTime < fadeInTime ? currentTime / fadeInTime : 
				currentTime > fadeOutTime ? (DURATION - currentTime) / (DURATION - fadeOutTime) : alpha;
			currentTime += Gdx.graphics.getDeltaTime();
			
		}
		else {
			
			ScreenStateManager.changeScreen(Screens.TITLE);
			return;
			
		}
		
		logo.setColor(1f, 1f, 1f, alpha);
		label.setColor(1f, 1f, 1f, alpha);
		
		stage.draw();
		
	}
	
}

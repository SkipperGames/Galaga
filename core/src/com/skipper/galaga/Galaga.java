package com.skipper.galaga;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Galaga extends ApplicationAdapter {
	
	
	
	@Override
	public void create() {
		
		ScreenStateManager.changeScreen(Screens.SPLASH);
	}

	
	@Override
	public void render() {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		ScreenStateManager.drawScreen();
	}
	
	
	@Override
	public void pause () {
		super.pause();
	}

	@Override
	public void resume () {
		super.resume();
	}
	
	
}

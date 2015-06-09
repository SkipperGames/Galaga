package com.skipper.galaga;

import com.badlogic.gdx.Gdx;

public final class ScreenStateManager {
	
	private static final SplashScreen splashScreen = new SplashScreen();
	private static TitleScreen titleScreen;
	private static HighscoreScreen scoreScreen;
	private static LoadingScreen loadingScreen;
	private static GameScreen gameScreen;
	private static final SettingsScreen settingsScreen = new SettingsScreen();
	private static PauseScreen pauseScreen;
	
	private static Screen currentScreen = titleScreen;
	public static final Screen GetScreen() { return currentScreen; }
	
	
	
	public static void changeScreen(Screens screen) {
		
		Screen.stage.clear();
		
		switch (screen) {
		
		case SPLASH:
			
			currentScreen = splashScreen.fillStage();
			Gdx.input.setInputProcessor(null);
			
			break;
		
		case TITLE:
			
			if (titleScreen == null) titleScreen = new TitleScreen();
			
			currentScreen = titleScreen.fillStage();
			Gdx.input.setInputProcessor(Screen.stage);
			
			break;
			
		case SCORES:
			
			if (scoreScreen == null) scoreScreen = new HighscoreScreen();
			
			currentScreen = scoreScreen.fillStage();
			Gdx.input.setInputProcessor(Screen.stage);
			
			break;
			
		case LOADING:
			
			if (loadingScreen == null)  loadingScreen = new LoadingScreen();
			
			currentScreen = loadingScreen.fillStage();
			Gdx.input.setInputProcessor(null);
			
			break;
			
		case GAME:
			
			if (gameScreen == null) gameScreen = new GameScreen();
			
			currentScreen = gameScreen.fillStage();
			Gdx.input.setInputProcessor(gameScreen);
			
			if (pauseScreen == null) pauseScreen = new PauseScreen();
			
			break;
			
		case SETTINGS:
			
			currentScreen = settingsScreen.fillStage();
			Gdx.input.setInputProcessor(Screen.stage);
			
			break;
		
		case PAUSE:
	
			currentScreen = pauseScreen.fillStage();
			Gdx.input.setInputProcessor(Screen.stage);
			
			break;
		}		
		
	}
	
	
	public static void drawScreen() {
		
		currentScreen.draw();
	}
	
	

}

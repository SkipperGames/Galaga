package com.skipper.galaga;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



public class TitleScreen extends Screen {

	private final Table table;
	
	private final Image title;
	private final TextButton playButton;
	private final TextButton helpButton;
	private final TextButton settingsButton;
	private final TextButton quitButton;
	
	
	
	public TitleScreen() {
		
		table = new Table();
		table.setSize(480f, 800f);
		table.align(Align.top | Align.center);
		
		title = new Image(skin.getDrawable("title"));
		
		playButton = new TextButton("Play", textButtonStyle);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenStateManager.changeScreen(Screens.LOADING);
			}
		});
		
		helpButton = new TextButton("How to Play", textButtonStyle);
		helpButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		
		settingsButton = new TextButton("Settings", textButtonStyle);
		settingsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SettingsScreen.prevScreen = Screens.TITLE;
				ScreenStateManager.changeScreen(Screens.SETTINGS);
			}
		});
		
		quitButton = new TextButton("Quit", textButtonStyle);
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		
		table.add(title).padTop(32f).padBottom(32f).row();
		table.add(playButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(16f).row();
		table.add(helpButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(16f).row();
		table.add(settingsButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(16f).row();
		table.add(quitButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
	}
	
	
	@Override
	protected Screen fillStage() {
		
		stage.addActor(table);		
		return this;
	}
	
	
	@Override
	protected void draw() {
		
		stage.draw();
	}
	
}

package com.skipper.galaga;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;



public class SettingsScreen extends Screen {

	private final Table table;
	
	private final Label settingsLabel;
	
	private final Label volumeLabel;
	private final ProgressBar volumeBar;
	
	private final Label effectsLabel;
	private final TextButton effectsButton;
	
	private final Label lightsLabel;
	private final TextButton lightsButton;
	
	private final TextButton backButton;
	
	private final Vector2 coords = new Vector2();
	
	protected static Screens prevScreen;
	
	
	public SettingsScreen() {
		
		
		table = new Table();
		table.setSize(480f, 800f);
		table.align(Align.top | Align.center);
		
		settingsLabel = new Label("Settings", labelStyle);
		
		volumeLabel = new Label("Volume", labelStyle);
		volumeBar = new ProgressBar(0f, 1.0f, 0.1f, false, progressBarStyle);
		volumeBar.setValue(Settings.volume());
		volumeBar.addListener(new DragListener() {
			@Override
			public void drag (InputEvent event, float x, float y, int pointer) {
				event.toCoordinates(volumeBar, coords);
				volumeBar.setValue(coords.x / 320f);
			}
			@Override
			public void dragStop (InputEvent event, float x, float y, int pointer) {
				Settings.setVolume(volumeBar.getValue());
			}
		});
		
		effectsLabel = new Label("Effects", labelStyle);
		effectsButton = new TextButton(Settings.useEffects() ? "ON" : "off", textButtonStyle);
		effectsButton.setColor(Settings.useEffects() ? Color.GREEN : Color.GRAY);
		effectsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.setEffects(!Settings.useEffects());
				effectsButton.setColor(Settings.useEffects() ? Color.GREEN : Color.GRAY);
				effectsButton.setText(Settings.useEffects() ? "ON" : "off");
			}
		});
		
		lightsLabel = new Label("Lights", labelStyle);
		lightsButton = new TextButton(Settings.useLights() ? "ON" : "off", textButtonStyle);
		lightsButton.setColor(Settings.useLights() ? Color.GREEN : Color.GRAY);
		lightsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.setLights(!Settings.useLights());
				lightsButton.setColor(Settings.useLights() ? Color.GREEN : Color.GRAY);
				lightsButton.setText(Settings.useLights() ? "ON" : "off");
			}
		});
		
		backButton = new TextButton("Back", textButtonStyle);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenStateManager.changeScreen(prevScreen);
				
				Settings.flushSettings();
			}
		});
		
		table.add(settingsLabel).padTop(32f).padBottom(96f).colspan(2);
		
		table.row();
		
		table.add(volumeLabel);
		
		table.row();
		
		table.add(volumeBar).width(320f).height(32f).padBottom(48f).colspan(2);
		
		table.row();
		
		table.add(effectsLabel).padBottom(24f);
		table.add(effectsButton).width(128f).height(64f).padBottom(24f);
		
		table.row();
		
		table.add(lightsLabel).padBottom(128f);
		table.add(lightsButton).width(128f).height(64f).padBottom(128f);
		
		table.row();
		
		table.add(backButton).center().width(BUTTON_WIDTH).height(BUTTON_HEIGHT).colspan(2);
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
package com.skipper.galaga;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.skipper.galaga.mvc.GalagaController;



public class PauseScreen extends Screen {
	
	
	private final Table pauseTable;
	private final Label pauseLabel;
	private final TextButton resumeButton;
	private final TextButton helpButton;
	private final TextButton settingsButton;
	private final TextButton quitButton;
	
	private final Table confirmationTable;
	private final Label confirmationLabel;
	private final TextButton iquitButton;
	private final TextButton iwontQuitButton;
	
	
	public PauseScreen() {
		
		pauseTable = new Table();
		pauseTable.setSize(480f, 800f);
		pauseTable.align(Align.top | Align.center);
		
		pauseLabel = new Label("Paused", labelStyle);
		
		resumeButton = new TextButton("Resume Game", textButtonStyle);
		resumeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenStateManager.changeScreen(Screens.GAME);
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
				SettingsScreen.prevScreen = Screens.PAUSE;
				ScreenStateManager.changeScreen(Screens.SETTINGS);
			}
		});
		
		quitButton = new TextButton("Quit to Title", textButtonStyle);
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showConfirmationBox();
			}
		});
		
		pauseTable.add(pauseLabel).padTop(32f).padBottom(96f).row();
		pauseTable.add(resumeButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(24f).row();
		pauseTable.add(helpButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(24f).row();
		pauseTable.add(settingsButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(128f).row();
		pauseTable.add(quitButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
		
		confirmationTable = new Table();
		confirmationTable.setSize(480f, 480f);
		confirmationTable.setPosition(0f, 160f);
		confirmationTable.align(Align.top | Align.center);
		
		confirmationLabel = 
				new Label("You will lose your progress if you quit.", 
						labelStyle);
		confirmationLabel.setColor(1f, 0f, 0f, 1f);
		confirmationLabel.setWrap(true);
		confirmationLabel.setWidth(BUTTON_WIDTH);
		confirmationLabel.setAlignment(Align.center);

		iquitButton = new TextButton("Quit", textButtonStyle);
		iquitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GalagaController.cleanUp();
				ScreenStateManager.changeScreen(Screens.TITLE);
			}
		});
		
		iwontQuitButton = new TextButton("Return", textButtonStyle);
		iwontQuitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hideConfirmationBox();
			}
		});
		
		confirmationTable.add(confirmationLabel).width(BUTTON_WIDTH).height(160f).row();
		confirmationTable.add(iquitButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).row().pad(24f);
		confirmationTable.add(iwontQuitButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);	
	}
	
	
	private void showConfirmationBox() {
		
		stage.clear();
		stage.addActor(confirmationTable);
	}
	
	private void hideConfirmationBox() {
		
		stage.clear();
		fillStage();
	}
		
	
	@Override
	protected Screen fillStage() {
		
		stage.addActor(pauseTable);
		return this;
	}

	
	@Override
	protected void draw() {
		
		
		stage.draw();
		
	}
	
}

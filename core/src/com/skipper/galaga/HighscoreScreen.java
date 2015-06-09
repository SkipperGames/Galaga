package com.skipper.galaga;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



public class HighscoreScreen extends Screen {

	private final Table table;
	
	private final Label scoreLabel;
	
	
	public HighscoreScreen() {
		
		
		table = new Table();
		table.setSize(480f, 800f);
		table.align(Align.top | Align.center);
		
		scoreLabel = new Label("Top Five", labelStyle);
		table.add(scoreLabel).padTop(32f).padBottom(96f).colspan(2).center();
		
		String[] keys = new String[] {"1st", "2nd", "3rd", "4th", "5th"};
		
		table.row();
		
		for (int i = 0; i < 5; i++) {
			
			Label key = new Label(keys[i], labelStyle);
			Label score = new Label((CharSequence) Settings.getStats().get(keys[i])[0], labelStyle);
			
			table.add(key).left().padBottom(24f);
			table.add(score).right().padBottom(24f);
			
			table.row();
		}
		
		TextButton backButton = new TextButton("Back", textButtonStyle);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenStateManager.changeScreen(Screens.TITLE);
				
				Settings.flushSettings();
			}
		});
		
		table.row();
		
		table.add(backButton).center().width(BUTTON_WIDTH).height(BUTTON_HEIGHT).colspan(2).padTop(64f);
	}
	
	
	public void addScore(long value) {
		
		
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

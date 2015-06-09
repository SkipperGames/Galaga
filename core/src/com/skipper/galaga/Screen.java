package com.skipper.galaga;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.InputAdapter;


public abstract class Screen extends InputAdapter {
	
	protected static final Skin skin = new Skin(Resources.getGalagaAtlas());
	protected static final BitmapFont font = new BitmapFont();
	protected static final LabelStyle labelStyle = new LabelStyle();
	protected static final TextButtonStyle textButtonStyle = new TextButtonStyle();
	protected static final ProgressBarStyle progressBarStyle = new ProgressBarStyle();
	
	protected static final Stage stage = new Stage();
	
	protected Screen fillStage() { return this; }
	protected abstract void draw();
	
	protected static final float BUTTON_WIDTH = 320f;
	protected static final float BUTTON_HEIGHT = 80f;
	
	
	static {
		
		font.setScale(2f);
		
		labelStyle.font = font;
		
		textButtonStyle.down = skin.getDrawable("button_down");
		textButtonStyle.up = skin.getDrawable("button_up");
		textButtonStyle.font = font;
		
		final Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
		
		skin.add("green", new Texture(pixmap));
		
		progressBarStyle.background = skin.newDrawable("green", Color.GRAY);
		progressBarStyle.knob = skin.newDrawable("green", Color.WHITE);
	}
	
}

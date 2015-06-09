package com.skipper.galaga;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.skipper.galaga.mvc.GalagaController;
import com.skipper.galaga.mvc.GalagaModelView;


public class GameScreen extends Screen {


	private boolean left = false;
	private boolean right = false;
	private boolean shoot = false;


	public GameScreen() {

		GalagaModelView.init();
	}


	@Override
	protected void draw() {


		//
		GalagaController.controlPlayerKeyboard(left, right, shoot);
		//
		GalagaModelView.update();

	}

	@Override
	protected Screen fillStage() {

		GalagaModelView.startGame();
		return this;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (pointer == 0) {
			shoot = button == Buttons.LEFT ? true : shoot;
			GalagaController.controlPlayerPointer(screenX, shoot);
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if (pointer == 0)
			shoot = button == Buttons.LEFT ? false : shoot;

		return true;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) {

		GalagaController.controlPlayerPointer(screenX, shoot);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		if (pointer == 0) {
			GalagaController.controlPlayerPointer(screenX, shoot);
		}

		return true;
	}

	@Override
	public boolean keyDown(int keycode) {

		left = keycode == Keys.LEFT ? true : left;
		right = keycode == Keys.RIGHT ? true : right;
		shoot = keycode == Keys.SPACE ? true : shoot;

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {

		if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			ScreenStateManager.changeScreen(Screens.PAUSE);
		}

		if (keycode == Keys.ENTER) {
			GalagaModelView.useDebugRender = !GalagaModelView.useDebugRender;
		}

		left = keycode == Keys.LEFT ? false : left;
		right = keycode == Keys.RIGHT ? false : right;
		shoot = keycode == Keys.SPACE ? false : shoot;

		return true;
	}

}

package com.skipper.galaga.mvc;



public class GalagaController {


	public static void controlPlayerPointer(int screenX, boolean shoot) {
		GalagaModelView.player.controlPlayerPointer(screenX, shoot);
	}

	public static void controlPlayerKeyboard(boolean left, boolean right, boolean shoot) {
		GalagaModelView.player.controlPlayerKeyboard(left, right, shoot);
	}

	public static void cleanUp() {
		GalagaModelView.cleanUp();
	}

}
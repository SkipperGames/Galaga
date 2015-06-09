package com.skipper.galaga.mvc;

import com.skipper.galaga.ScreenStateManager;
import com.skipper.galaga.Screens;

public enum GalagaEvent {

	ev_none(-1),
	ev_init_player(0),
	ev_game_end(1),
	ev_tractor_beam(2);

	private int id = -1;

	GalagaEvent(int num) {
		id = num;
	}

	public void start() {

		if (id == 0) {

			GalagaModelView.playerEnabled();
		}
		else if (id == 1) {

			GalagaModelView.cleanUp();
			ScreenStateManager.changeScreen(Screens.SCORES);
		}
		else if (id == 2) {

			GalagaModelView.tractorBeamEnabled();
		}
	}
}
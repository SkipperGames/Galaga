package com.skipper.galaga.mvc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;

import box2dLight.Light;
import box2dLight.PointLight;

enum LightModel {

	light_default(-1),
	light_player(0),
	light_player_bullet(1),
	light_enemy(2),
	light_enemy_bullet(3),
	light_capture(4);

	private int id = -1;

	LightModel(int id) {

		this.id = id;
	}

	public Light createAndAttachLight(Body body) {

		final Light light;

		switch (id) {

		case 0:

			light = new PointLight(
					GalagaModelView.rayHandler,
					Constants.NUM_LIGHT_RAYS,
					new Color(0f, 1f, 1f, 0.5f),
					Constants.POINT_LIGHT_DISTANCE,
					0f, 0f);

			break;

		case 1:

			light = new PointLight(
					GalagaModelView.rayHandler,
					Constants.NUM_LIGHT_RAYS,
					new Color(0f, 1f, 1f, 0.5f),
					Constants.POINT_LIGHT_DISTANCE,
					0f, 0f);

			break;

		case 2:

			light = new PointLight(
					GalagaModelView.rayHandler,
					Constants.NUM_LIGHT_RAYS,
					new Color(1f, 1f, 1f, 0.5f),
					Constants.POINT_LIGHT_DISTANCE,
					0f, 0f);

			break;

		case 3:

			light = new PointLight(
					GalagaModelView.rayHandler,
					Constants.NUM_LIGHT_RAYS,
					new Color(1f, 0f, 0f, 0.5f),
					Constants.POINT_LIGHT_DISTANCE + 50f,
					0f, 0f);

			break;

		case 4:

			light = new PointLight(
					GalagaModelView.rayHandler,
					Constants.NUM_LIGHT_RAYS,
					new Color(0f, 1f, 0f, 0.5f),
					Constants.POINT_LIGHT_DISTANCE,
					0f, 0f);

			break;
			
		default:

			light = new PointLight(
					GalagaModelView.rayHandler,
					Constants.NUM_LIGHT_RAYS,
					new Color(1f, 1f, 1f, 0.5f),
					Constants.POINT_LIGHT_DISTANCE,
					0f, 0f);

			break;
		}

		light.setXray(true);
		light.setSoft(false);
		light.attachToBody(body, 0f, 0f);
		light.setActive(false);

		return light;
	}
}
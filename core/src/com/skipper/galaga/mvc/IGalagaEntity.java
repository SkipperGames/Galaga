package com.skipper.galaga.mvc;

import com.badlogic.gdx.math.Vector2;

interface IGalagaEntity {


	IGalagaEntity enable(Vector2 to);
	void disable(boolean override);


	void draw(float deltaTime);


	void enableBody();
	void disableBody();


	int getId();
}
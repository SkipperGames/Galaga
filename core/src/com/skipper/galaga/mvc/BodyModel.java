package com.skipper.galaga.mvc;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public final class BodyModel {
	
	public static Body createDynamic(FixtureDef fixture, boolean isBullet) {
		
		final Body body;
		
		final BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		bdef.bullet = isBullet;
		bdef.position.set(0f, 0f);
		
		body = GalagaModelView.world.createBody(bdef);
		body.createFixture(fixture);
		body.setActive(false);
		
		return body;
	}
	
	public static Body createKinematic(FixtureDef fixture) {
		
		final Body body;
		
		final BodyDef bdef = new BodyDef();
		bdef.type = BodyType.KinematicBody;
		bdef.position.set(0f, 0f);
		
		body = GalagaModelView.world.createBody(bdef);
		body.createFixture(fixture);
		body.setActive(false);
		
		return body;
	}
	
	public static FixtureDef fixture(Shape shape, boolean isSensor) {
		
		final FixtureDef fdef = new FixtureDef();
		
		fdef.shape = shape;
		fdef.density = 0.1f;
		fdef.friction = 0f;
		fdef.restitution = 0.1f;
		fdef.isSensor = isSensor;
		
		return fdef;
	}
	
	public static CircleShape circleShape(float radius) {
		
		final CircleShape shape = new CircleShape();
		shape.setRadius(radius * Constants.BOX2D_SCALE);
		
		return shape;
	}
	
	public static PolygonShape coneShape() {
		
		final PolygonShape shape = new PolygonShape();
        shape.set(new float[] {
        		0f, 24f * Constants.SPRITE_SCALE,
        		-23f * Constants.SPRITE_SCALE, -88f * Constants.SPRITE_SCALE,
        		23f * Constants.SPRITE_SCALE, -88f * Constants.SPRITE_SCALE});
        
        return shape;
	}
	
	public static EdgeShape lineShape(float len) {
		
		final EdgeShape shape = new EdgeShape();
        shape.set(0f, -len * Constants.BOX2D_SCALE * 0.5f, 
        		0f, len * Constants.BOX2D_SCALE * 0.5f);

        return shape;
	}
}
package com.skipper.galaga;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Resources {

	
	private static final AssetManager assets;
	private static final InternalFileHandleResolver resolver;
	private static final TextureParameter textureParams = new TextureParameter();
	private static final ParticleEffectParameter particleParams = new ParticleEffectParameter();
	
	private static final TextureAtlas galagaAtlus;
	
	
	static {
		
		resolver = new InternalFileHandleResolver();
		assets = new AssetManager(resolver);
		
		textureParams.minFilter = TextureFilter.Nearest;
		textureParams.magFilter = TextureFilter.Nearest;
		textureParams.format = Format.RGBA8888;
		textureParams.genMipMaps = false;
		
		particleParams.imagesDir = resolver.resolve("particles/arrowhead.p").parent();
		
		assets.load("textures/galaga.pack", TextureAtlas.class);
		while (!assets.update()) {}
		galagaAtlus = assets.get("textures/galaga.pack", TextureAtlas.class);
	}
	
	public static void loadSound(String fileName) {
		assets.load("audio/" + fileName + ".wav", Sound.class);
	}
	
	public static void loadSounds(String[] fileNames) {
		for (String s : fileNames)
			assets.load("audio/" + s + ".wav", Sound.class);
	}
	
	public static Sound sound(String fileName) {
		
		if (!assets.isLoaded("audio/" + fileName + ".wav")) {
			assets.load("audio/" + fileName + ".wav", Sound.class);
			while (!assets.update()) {}
		}
		
		return assets.get("audio/" + fileName + ".wav", Sound.class);
	}
	
	public static void loadParticleEffect(String fileName) {
		assets.load("particles/" + fileName + ".p", ParticleEffect.class, particleParams);
	}
	
	public static void loadParticleEffects(String[] fileNames) {
		for (String s : fileNames)
			assets.load("particles/" + s + ".p", ParticleEffect.class, particleParams);
	}
	
	public static ParticleEffect effect(String fileName) {
		return assets.get("particles/" + fileName + ".p", ParticleEffect.class);
	}
	
	public static boolean update() {
		return assets.update();
	}
	
	public static float getProgress() {
		return assets.getProgress();
	}
	
	public static FileHandle getFileHandle(String fileName) {
		return resolver.resolve(fileName);
	}
	
	public static String getFilePath(String fileName) {
		return resolver.resolve(fileName).path();
	}
	
	public static TextureAtlas getGalagaAtlas() {
		return galagaAtlus;
	}
	
}

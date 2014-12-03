package uk.ac.lincoln.games.coffin.countdown;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CoffinCountdown extends ApplicationAdapter {
	
	private SpriteBatch batch;
	private BitmapFont font,font2;
	private boolean mode_active;
	private int time;
	private Timer timer;
	
	private int pause;
	
	private class CountdownTask extends Task {
		public void run() {
			time--;
		}
	}
	
	@Override
	public void create () {
		pause = 300;
		mode_active=true;
		batch = new SpriteBatch();
		timer = new Timer();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("DEATH_FONT.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 96;
		font = generator.generateFont(parameter); // font size 12 pixels
		parameter.size=148;
		font2 = generator.generateFont(parameter);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		time = 1000;
		
	}
	
	public void setTimer() {
		pause = 300;
		time = new Random().nextInt(946000000);
		timer.clear();
		timer.scheduleTask(new CountdownTask(), 1, 1);
		
	}
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if(!mode_active) {
			font.draw(batch, "calibrating", 100, 230);
			font.draw(batch, "please remain at rest", 100, 150);
			if(Gdx.input.getAccelerometerZ()<-0.99){
				pause--;
				if (pause<=0){
					setTimer();
					mode_active = true;
				}
			}
			else {
				pause = 300;
			}
		}
		else {
			font2.draw(batch, String.valueOf(time), 100, 300);
			font.draw(batch, "seconds until death", 100, 150);
			if(Gdx.input.getAccelerometerZ()>-0.99){
				mode_active = false;
			}
		}
		batch.end();
	}
	
}

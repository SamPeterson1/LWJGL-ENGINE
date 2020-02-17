package main;

import GUI.MasterGUI;
import events.EventHandler;
import misc.Game;
import misc.Time;
import text.Text;
import window.GLFWWindow;

public class Main {
	
	public static void main(String[] args) {
		
		long frameStartTime;
		Game game = new Game();
		game.init();
		GLFWWindow.sendResize();
		float fpsLast10Frames = 0;
		int frame = 0;
		Text fpsCounter = (Text) MasterGUI.getComponent("fps");
		while(!GLFWWindow.closed()) {
			Time.beginFrame();
			frameStartTime = System.currentTimeMillis();
			GLFWWindow.update();
			game.render();
			GLFWWindow.swapBuffer();
			EventHandler.clearEvents();
			fpsLast10Frames += (int) (1/((System.currentTimeMillis() - frameStartTime)/1000f));
			frame ++;
			if(frame % 10 == 0) {
				frame = 0;
				fpsCounter.setText(String.valueOf(fpsLast10Frames/10));
				fpsLast10Frames = 0;
			}
			Time.waitForNextFrame();
		}
		
		game.dispose();
	}

}

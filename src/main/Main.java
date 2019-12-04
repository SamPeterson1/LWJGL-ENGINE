package main;

import misc.Game;
import misc.Time;
import window.GLFWWindow;

public class Main {
	
	public static void main(String[] args) {

		Game game = new Game();
		Time.setCap(10);
		game.init();
		GLFWWindow.sendResize();
		
		while(!GLFWWindow.closed()) {
			Time.beginFrame();
			GLFWWindow.update();
			game.render();
			GLFWWindow.updateSize();
			GLFWWindow.swapBuffer();
			Time.waitForNextFrame();
		}
		
		game.dispose();
	}

}

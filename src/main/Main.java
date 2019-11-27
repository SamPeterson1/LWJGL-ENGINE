package main;

import rendering.GLFWWindow;
import rendering.Game;
import rendering.Time;

public class Main {
	
	public static void main(String[] args) {

		Game game = new Game();
		game.init();
		
		while(!GLFWWindow.closed()) {
			Time.beginFrame();
			GLFWWindow.update();
			game.render();
			GLFWWindow.swapBuffer();
			Time.waitForNextFrame();
		}
		
		game.dispose();
	}

}

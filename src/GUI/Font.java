package GUI;

import java.util.HashMap;

public class Font {
	
	public static final int ID = 0;
	public static final int X = 1;
	public static final int Y = 2;
	public static final int WIDTH = 3;
	public static final int HEIGHT = 4;
	public static final int XOFF = 5;
	public static final int YOFF = 6;
	public static final int ADV = 7;
	
	private HashMap<Character, int[]> data = new HashMap<>();
	private String texAtlas;
	
	public void setAtlas(String path) {
		this.texAtlas = path;
	}
	
	public String getTexAtlas() {
		return this.texAtlas;
	}
	
	public void addChar(char c, int[] data) {
		this.data.put(c, data);
	}
	
	public int getData(char c, int data) {
		return this.data.get(c)[data];
	}
	
}

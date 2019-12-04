package misc;

public class Utils {
	
	public static boolean inBounds(int x, int y, int width, int height, int x2, int y2) {
		return x2 >= x && x2 <= x + width && y2 >= y && y2 <= y + height;
	}
	
}

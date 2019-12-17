package misc;

import math.Vector2f;
import math.Vector3f;

public class Utils {
	
	public static boolean inBounds(int x, int y, int width, int height, int x2, int y2) {
		return x2 >= x && x2 <= x + width && y2 >= y && y2 <= y + height;
	}
	
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.getZ() - p3.getZ()) * (p1.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (p1.getZ() - p3.getZ());
		float l1 = ((p2.getZ() - p3.getZ()) * (pos.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (pos.getY() - p3.getZ())) / det;
		float l2 = ((p3.getZ() - p1.getZ()) * (pos.getX() - p3.getX()) + (p1.getX() - p3.getX()) * (pos.getY() - p3.getZ())) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.getY() + l2 * p2.getY() + l3 * p3.getY();
	}
	
}

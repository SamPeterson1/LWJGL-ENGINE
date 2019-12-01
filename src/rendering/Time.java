package rendering;

public class Time {
	
	public static float maxDeltaTime;
	public static float deltaTime;
	private static long frameStart;
	
	public static void setCap(int fpsCap) {
		maxDeltaTime = 1f/fpsCap;
	}
	
	private static long getTimeMillis() {
		return System.currentTimeMillis();
	}
	
	public static float getDeltaTime() {
		return (float)(getTimeMillis() - frameStart)/1000f;
	}
	
	public static void beginFrame() {
		frameStart = getTimeMillis();
	}
	
	public static void waitForNextFrame() {
		//while(getDeltaTime() < maxDeltaTime) {
		//}
		Time.deltaTime = getDeltaTime();
	}
	
}

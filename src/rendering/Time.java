package rendering;

public class Time {
	
	public static float maxDeltaTime;
	public static float deltaTime;
	private static long frameStart;
	
	public static void setCap(float fpsCap) {
		maxDeltaTime = 1f/(float)fpsCap;
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
		if(getDeltaTime() > maxDeltaTime) {
			Time.deltaTime = getDeltaTime();
			System.out.println(1/Time.deltaTime);
			return;
		}
		while(getDeltaTime() < maxDeltaTime && !GLFWWindow.closed()) {
			GLFWWindow.update();
		}
		Time.deltaTime = getDeltaTime();
		System.out.println(1/Time.deltaTime + " foo ");
	}
	
}

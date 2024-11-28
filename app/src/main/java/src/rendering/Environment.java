package rendering;

import java.util.ArrayList;
import java.util.List;

import lights.PointLight;
import math.Vector3f;

public class Environment {
	
	public static Vector3f sunDirection = new Vector3f(0f, 1f, 0f);
	public static Vector3f sunColor = new Vector3f(1f, 1f, 1f);
	public static Vector3f fogColor = new Vector3f(1f, 0f, 0f);
	
	public static List<PointLight> pointLights = new ArrayList<>();
	
}

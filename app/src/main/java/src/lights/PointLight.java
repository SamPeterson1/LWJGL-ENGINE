package lights;

import java.util.List;
import java.util.Map;

import math.Transform;
import math.TransformListener;
import math.Vector3f;
import misc.FrameListener;
import misc.Game;
import models.Entity;
import models.Mesh;
import models.ModelBatch;
import rendering.MasterRenderer;
import shadows.ShadowCubeMap;
import shadows.ShadowCubemapRenderer;

public class PointLight implements TransformListener, FrameListener {
	
	private Vector3f position;
	private Vector3f color;
	private Vector3f attenuation;
	private boolean enabled = true;
	private boolean castsShadow = true;
	private boolean updated = false;
	private ShadowCubeMap shadowMap;
	
	public PointLight(Vector3f position, Vector3f color, Vector3f attenuation) {
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
		this.shadowMap = new ShadowCubeMap(1024);
		MasterRenderer.addPointShadowMap(this.shadowMap.getTexture());
		Entity.addListener(this);
		Game.addFrameListener(this);
		this.bakeShadowMap();
	}
	
	public PointLight(Vector3f position, Vector3f color, Vector3f attenuation, boolean castsShadow) {
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
		this.castsShadow = castsShadow;
		if(this.castsShadow) {
			this.shadowMap = new ShadowCubeMap(1024);
			MasterRenderer.addPointShadowMap(this.shadowMap.getTexture());
			Entity.addListener(this);
			Game.addFrameListener(this);
			this.bakeShadowMap();
		}
		System.out.println(this.castsShadow);
	}
	
	public void bakeShadowMap() {
		if(this.castsShadow) {
			System.out.println("baking");
			ShadowCubemapRenderer.begin(this);
			Map<Mesh, List<Entity>> meshesMap = ModelBatch.getEntities();
			
			for(Mesh mesh: meshesMap.keySet()) {
				if(mesh.isEnabled() && mesh.getMaterial().castsShadow()) {
					List<Entity> entities = meshesMap.get(mesh);
					ShadowCubemapRenderer.render(mesh, entities);
				}
			}
			ShadowCubemapRenderer.end();
		}
		
	}
	
	public ShadowCubeMap getShadowMap() {
		return this.shadowMap;
	}
	
	public void disable() {
		this.enabled = false;
	}
	
	public void enable() {
		this.enabled = true;
	}
	
	public void toggle() {
		this.enabled = !this.enabled;
	}
	
	public boolean enabled() {
		return this.enabled;
	}
	
	public Vector3f getAttenuation() {
		return this.attenuation;
	}

	public void setAttenuation(Vector3f attenuation) {
		this.attenuation = attenuation;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	@Override
	public float getRadius() {
		return 100f;
	}

	@Override
	public void update() {
		this.updated = true;
	}

	@Override
	public void beginFrame() {
		this.updated = false;
	}

	@Override
	public void endFrame() {
		if(this.updated) this.bakeShadowMap();
	}
	
}


package rendering;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import models.TextMesh;
import shaders.TextShader;

public class TextRenderer {
	
	TextShader shader;
	
	public TextRenderer(TextShader shader) {
		this.shader = shader;
		this.shader.createUniforms();
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE,GL_ONE_MINUS_SRC_ALPHA);
		glAlphaFunc(GL_GREATER,0);
	}
	
	
	private void begin(TextMesh model) {
		glDisable(GL_DEPTH_TEST);
		GL30.glBindVertexArray(model.getModel().getVAO_ID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
	}
	
	private void end() {
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
	}
	
	public void renderText(TextMesh mesh) {
		this.shader.bind();
		this.begin(mesh);
		this.shader.setTextColor(mesh.getColor());
		glActiveTexture(GL_TEXTURE0);
		mesh.getTexture().bind();
		this.shader.setSampler(GL_TEXTURE0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		this.end();
		this.shader.unbind();
	}

}

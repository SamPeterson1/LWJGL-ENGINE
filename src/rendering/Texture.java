package rendering;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import files.PNGFile;

public class Texture {
	
	int id;
	
	public Texture(String path) {

		PNGFile file = new PNGFile(path);
		ByteBuffer image = file.read();

	    int id = glGenTextures();
	    glBindTexture(GL_TEXTURE_2D, id);

	    glPixelStorei(GL_UNPACK_ALIGNMENT, 1); 
	    
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, file.getWidth(), file.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
	    glGenerateMipmap(GL_TEXTURE_2D);
	    
	}
	
	public int getID() {
		return this.id;
	}
	
	public void remove() {
		glDeleteTextures(this.id);
	}
}

package rendering;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
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

	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, file.getWidth(), file.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
	    //glGenerateMipmap(GL_TEXTURE_2D);
	    
	}
	
	public int getID() {
		return this.id;
	}
	
	public void remove() {
		glDeleteTextures(this.id);
	}
}

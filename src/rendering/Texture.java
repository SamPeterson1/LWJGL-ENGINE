package rendering;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import files.PNGFile;

public class Texture {
	
	int id;
	
	private void loadBytes(ByteBuffer bytes, int width, int height) {
		this.id = glGenTextures();
	    glBindTexture(GL_TEXTURE_2D, id);

	    glPixelStorei(GL_UNPACK_ALIGNMENT, 1); 
	    
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, bytes);
	    glGenerateMipmap(GL_TEXTURE_2D);
	}
	
	public Texture(BufferedImage image) {
		
		byte[] pixelData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    ByteBuffer buff = BufferUtils.createByteBuffer(pixelData.length);
	    buff.put(pixelData);
		this.loadBytes(buff, image.getWidth(), image.getHeight());
	    
	}
	
	public Texture(String path) {

		PNGFile file = new PNGFile(path);
		ByteBuffer image = file.read();
		this.loadBytes(image, file.getWidth(), file.getHeight());  
	    
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, this.id);
	}
	
}

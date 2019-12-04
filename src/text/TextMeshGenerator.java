package text;

import models.RawModel;
import window.GLFWWindow;
import models.ModelLoader;

public class TextMeshGenerator {
	
	private Font f;
	private	int windowWidth;
	private int windowHeight;
	
	public TextMeshGenerator(Font f) {
		
		this.f = f;
		
		this.windowWidth = GLFWWindow.getWidth();
		this.windowHeight = GLFWWindow.getHeight();
		
	}
	
	public void updateMesh(Text mesh) {
		String text = mesh.getText();
		int x = GLFWWindow.getWidth()/2 - this.f.pixelWidth(text, 1)/2;
		int y = GLFWWindow.getHeight()/2 - this.f.pixelHeight(text, 1)/2;
		this.windowWidth = GLFWWindow.getWidth();
		this.windowHeight = GLFWWindow.getHeight();
		
		float xFactor = (float)GLFWWindow.getWidth()/(float)f.pixelWidth(text, 1);
		float yFactor = (float)GLFWWindow.getHeight()/(float)f.pixelHeight(text, 1);
		
		float[] vertices = new float[text.length()*8];
		float[] textCoords = new float[text.length()*8];
		int[] indices = new int[text.length()*6];
		int vtPointer = 0;
		int indexPointer = 0;
		int texPointer = 0;
		int cursor = 0;
		int loops = 0;
		
		for(char c: text.toCharArray()) {
			
			//v1
			vertices[vtPointer] = ((x + cursor + f.getData(c, Font.XOFF))/(this.windowWidth/2f) - 1) * xFactor;
			vertices[vtPointer + 1] = ((y + f.getData(c, Font.YOFF))/(this.windowHeight/2f) - 1) * yFactor;
			
			//v2
			vertices[vtPointer + 2] = ((x + cursor + f.getData(c, Font.XOFF) + f.getData(c, Font.WIDTH))/(this.windowWidth/2f) - 1) * xFactor;
			vertices[vtPointer + 3] = ((y + f.getData(c, Font.YOFF))/(this.windowHeight/2f) - 1) * yFactor;
			
			//v3
			vertices[vtPointer + 4] = ((x + cursor + f.getData(c, Font.XOFF) + f.getData(c, Font.WIDTH))/(this.windowWidth/2f) - 1) * xFactor;
			vertices[vtPointer + 5] = ((y + f.getData(c, Font.YOFF) + f.getData(c, Font.HEIGHT))/(this.windowHeight/2f) - 1) * yFactor;
			
			//v4
			vertices[vtPointer + 6] = ((x + cursor + f.getData(c, Font.XOFF))/(this.windowWidth/2f) - 1) * xFactor;
			vertices[vtPointer + 7] = ((y + f.getData(c, Font.YOFF) + f.getData(c, Font.HEIGHT))/(this.windowHeight/2f) - 1) * yFactor;
			
			vtPointer += 8;
			
			cursor += f.getData(c, Font.ADV);
			
			//top triangle
			indices[indexPointer++] = 4*loops;
			indices[indexPointer++] = 4*loops + 1;
			indices[indexPointer++] = 4*loops + 2;
			
			//bottom triangle
			indices[indexPointer++] = 4*loops;
			indices[indexPointer++] = 4*loops + 2;
			indices[indexPointer++] = 4*loops + 3;
			
			//gen texture coords		
			
			textCoords[texPointer++] = (f.getData(c, Font.X))/1024f;
			textCoords[texPointer++] = (f.getData(c, Font.Y) + f.getData(c, Font.HEIGHT))/1024f;
			
			textCoords[texPointer++] = (f.getData(c, Font.X) + f.getData(c, Font.WIDTH))/1024f;
			textCoords[texPointer++] = (f.getData(c, Font.Y) + f.getData(c, Font.HEIGHT))/1024f;
			
			textCoords[texPointer++] = (f.getData(c, Font.X) + f.getData(c, Font.WIDTH))/1024f;
			textCoords[texPointer++] = (f.getData(c, Font.Y))/1024f;
			
			textCoords[texPointer++] = (f.getData(c, Font.X))/1024f;
			textCoords[texPointer++] = (f.getData(c, Font.Y))/1024f;
			
			loops ++;
		}
		
		mesh.getModel().updateBuffers(vertices, textCoords, indices);
	}
	
	public RawModel genMesh(Text mesh) {
		
		String text = mesh.getText();
		int x = GLFWWindow.getWidth()/2 - this.f.pixelWidth(text, 1)/2;
		int y = GLFWWindow.getHeight()/2 - this.f.pixelHeight(text, 1)/2;
		float xFactor = (float)GLFWWindow.getWidth()/(float)f.pixelWidth(text, 1);
		float yFactor = (float)GLFWWindow.getHeight()/(float)f.pixelHeight(text, 1);
		
		float[] vertices = new float[text.length()*12];
		float[] textCoords = new float[text.length()*8];
		int[] indices = new int[text.length()*6];
		int vtPointer = 0;
		int indexPointer = 0;
		int texPointer = 0;
		int cursor = 0;
		int loops = 0;
		
		for(char c: text.toCharArray()) {
						
			//v1
			vertices[vtPointer++] = ((x + cursor + f.getData(c, Font.XOFF))/(this.windowWidth/2f) - 1) * xFactor;
			vertices[vtPointer++] = ((y + f.getData(c, Font.YOFF))/(this.windowHeight/2f) - 1) * yFactor;
			
			//v2
			vertices[vtPointer++] = ((x + cursor + f.getData(c, Font.XOFF) + f.getData(c, Font.WIDTH))/(this.windowWidth/2f) - 1) * xFactor;
			vertices[vtPointer++] = ((y + f.getData(c, Font.YOFF))/(this.windowHeight/2f) - 1) * yFactor;
			
			//v3
			vertices[vtPointer++] = ((x + cursor + f.getData(c, Font.XOFF) + f.getData(c, Font.WIDTH))/(this.windowWidth/2f) - 1) * xFactor;
			vertices[vtPointer++] = ((y + f.getData(c, Font.YOFF) + f.getData(c, Font.HEIGHT))/(this.windowHeight/2f) - 1) * yFactor;
			
			//v4
			vertices[vtPointer++] = ((x + cursor + f.getData(c, Font.XOFF))/(this.windowWidth/2f) - 1) * xFactor;
			vertices[vtPointer++] = ((y + f.getData(c, Font.YOFF) + f.getData(c, Font.HEIGHT))/(this.windowHeight/2f) - 1) * yFactor;
			
			cursor += f.getData(c, Font.ADV);
			
			//top triangle
			indices[indexPointer++] = 4*loops;
			indices[indexPointer++] = 4*loops + 1;
			indices[indexPointer++] = 4*loops + 2;
			
			//bottom triangle
			indices[indexPointer++] = 4*loops;
			indices[indexPointer++] = 4*loops + 2;
			indices[indexPointer++] = 4*loops + 3;
			
			//gen texture coords		
			
			textCoords[texPointer++] = (f.getData(c, Font.X))/1024f;
			textCoords[texPointer++] = (f.getData(c, Font.Y) + f.getData(c, Font.HEIGHT))/1024f;
			
			textCoords[texPointer++] = (f.getData(c, Font.X) + f.getData(c, Font.WIDTH))/1024f;
			textCoords[texPointer++] = (f.getData(c, Font.Y) + f.getData(c, Font.HEIGHT))/1024f;
			
			textCoords[texPointer++] = (f.getData(c, Font.X) + f.getData(c, Font.WIDTH))/1024f;
			textCoords[texPointer++] = (f.getData(c, Font.Y))/1024f;
			
			textCoords[texPointer++] = (f.getData(c, Font.X))/1024f;
			textCoords[texPointer++] = (f.getData(c, Font.Y))/1024f;
			
			loops ++;
		}
		
		return ModelLoader.load2DModel(vertices, textCoords, indices, true);
		
	}
	
}

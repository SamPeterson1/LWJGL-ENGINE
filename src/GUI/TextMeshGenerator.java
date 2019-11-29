package GUI;

import files.FontFile;
import models.Model;
import models.ModelLoader;
import models.Text;
import rendering.GLFWWindow;

public class TextMeshGenerator {
	
	private Font f;
	private	int windowWidth;
	private int windowHeight;
	
	public TextMeshGenerator(String fontFile) {
		
		FontFile file = new FontFile(fontFile);
		this.f = file.read();
		file.close();
		
		this.windowWidth = GLFWWindow.getWidth();
		this.windowHeight = GLFWWindow.getHeight();
		
	}
	
	public Model genMesh(Text mesh) {
		
		String text = mesh.getText();
		float size = mesh.getRelativeSize();
		int x = GLFWWindow.getWidth()/2 - this.f.pixelWidth(text, size)/2;
		int y = GLFWWindow.getHeight()/2 - this.f.pixelHeight(text, size)/2;
		
		float[] vertices = new float[text.length()*12];
		float[] textCoords = new float[text.length()*8];
		int[] indices = new int[text.length()*6];
		int vtPointer = 0;
		int indexPointer = 0;
		int texPointer = 0;
		int cursor = 0;
		int loops = 0;
		
		for(char c: text.toCharArray()) {
			
			System.out.println(c + " " + f.getData(c, Font.HEIGHT));
			
			//v1
			vertices[vtPointer++] = (x + cursor + f.getData(c, Font.XOFF)*size)/(this.windowWidth/2f) - 1;
			vertices[vtPointer++] = (y + f.getData(c, Font.YOFF)*size)/(this.windowHeight/2f) - 1;
			
			//v2
			vertices[vtPointer++] = (x + cursor + f.getData(c, Font.XOFF)*size + f.getData(c, Font.WIDTH)*size)/(this.windowWidth/2f) - 1;
			vertices[vtPointer++] = (y + f.getData(c, Font.YOFF)*size)/(this.windowHeight/2f) - 1;
			
			//v3
			vertices[vtPointer++] = (x + cursor + f.getData(c, Font.XOFF)*size + f.getData(c, Font.WIDTH)*size)/(this.windowWidth/2f) - 1;
			vertices[vtPointer++] = (y + f.getData(c, Font.YOFF)*size + f.getData(c, Font.HEIGHT)*size)/(this.windowHeight/2f) - 1;
			
			//v4
			vertices[vtPointer++] = (x + cursor + f.getData(c, Font.XOFF)*size)/(this.windowWidth/2f) - 1;
			vertices[vtPointer++] = (y + f.getData(c, Font.YOFF)*size + f.getData(c, Font.HEIGHT)*size)/(this.windowHeight/2f) - 1;
			
			cursor += f.getData(c, Font.ADV) * size;
			
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
		
		return ModelLoader.load2DModel(vertices, textCoords, indices);
		
	}
	
}

package files;

import GUI.Font;

public class FontFile implements File {
	
	TextFile file;
	String atlasName;
	
	public FontFile(String path) {
		this.file = new TextFile(path);
	}

	@Override
	public Font read() {
		
		String line;
		Font font = new Font();
		
		while((line = file.readLine()) != null) {
			String[] tokens = line.split("\\s+");
			if(tokens[0].equals("char")) {
				
				char c = (char) (int) Integer.valueOf(tokens[1].split("=")[1]);
				int[] data = new int[tokens.length - 1];
				//System.out.println(line);
				System.out.print(c + " ");
				for(int i = 1; i < tokens.length; i ++) {
					//System.out.println(tokens[i]);		
					data[i-1] = Integer.valueOf(tokens[i].split("=")[1]);
					System.out.print(data[i-1] + " ");
				}
				System.out.println("");
				font.addChar(c, data);
			} else if(tokens[0].equals("page")) {
				font.setAtlas(tokens[2].split("=")[1]);
			}
		}
		
		return font;
	}

	@Override
	public void close() {
		file.close();
	}
	
	
	
}

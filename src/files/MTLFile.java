package files;

public class MTLFile implements File {
	
	String mtlName;
	String material = "";
	TextFile file;
	
	public MTLFile(String path) {
		this.file = new TextFile(path);
	}
	
	@Override
	public String read() {
		String line;
		while((line = file.readLine()) != null) {
			String[] tokens = line.split(" ");
			if(tokens[0].equals("newmtl")) {
				this.mtlName = tokens[1];
			} else if(tokens[0].equals("map_Kd")) {
				return "/assets/" + tokens[1];
			}
		}
		
		return "";
	}
	
	public String getName() {
		return this.mtlName;
	}

	@Override
	public void close() {
		this.file.close();
	}
	
}

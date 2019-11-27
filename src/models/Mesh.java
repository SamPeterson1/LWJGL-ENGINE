package models;

public interface Mesh {
	
	public static final int TEXT = 0;
	public static final int TEXTURED = 1;
	public static final int UNTEXTURED = 2;
	
	public abstract int getType();
	public abstract Model getModel();
	
}

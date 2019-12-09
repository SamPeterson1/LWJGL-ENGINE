package math;

import java.util.Arrays;

public class Matrix {
	
	private float[][] vals;
	private int height;
	private int width;
	
	public Matrix(int width, float... vals) {
		
		this.width = width;
		this.height = vals.length/width;
		
		this.assignVals(width, vals);
	}
	
	public void transpose3x3(Matrix other) {
		float[][] clone = other.getVals();
		this.vals[0][0] = clone[0][0];
		this.vals[0][1] = clone[1][0];
		this.vals[0][2] = clone[2][0];
		this.vals[1][0] = clone[0][1];
		this.vals[1][1] = clone[1][1];
		this.vals[1][2] = clone[2][1];
		this.vals[2][0] = clone[0][2];
		this.vals[2][1] = clone[1][2];
		this.vals[2][2] = clone[2][2];
	}
	
	private void assignVals(int width, float[] vals) {
		this.vals = new float[height][width];
		
		for(int i = 0; i < vals.length; i ++) {
			this.vals[i/width][i%width] = vals[i];
		}
	}
	
	public float[][] getVals() {
		return this.vals;
	}
	
	public static Matrix getIdentity(int width, int height) {
		
		float[] vals = new float[width*height];
		
		int step = width + 1;
		for(int i = 0; i < width*height; i += step) {
			vals[i] = 1;
		}
		
		return new Matrix(width, vals);
		
	}
	
	public Matrix multiply(Matrix other) {
		
		float[] newMatrix = new float[this.height*other.width];
		int ct = 0;
		
		for(int row = 0; row < this.height; row ++){
			for(int col = 0; col < other.width; col ++) {
				float[] vec1 = other.getRow(row);
				float[] vec2 = this.getCol(col);
				newMatrix[ct] = this.dotProduct(vec1, vec2);
				ct ++;
			}
		}
		
		this.width = other.width;
		this.assignVals(other.width, newMatrix);
		
		return new Matrix(other.width, newMatrix);
	}
	
	public void multiplyScalar(float val) {
		for(int i = 0; i < this.width; i ++) {
			for(int j = 0; j < this.height; j ++) {
				this.vals[i][j] *= val;
			}
		}
	}
	
	private float dotProduct(float[] vec1, float[] vec2) {
		float sum = 0;
		for(int i = 0; i < vec1.length; i ++) {
			sum += vec1[i] * vec2[i];
		}
		
		return sum;
	}
	
	public float[] getFloats() {
		float[] retVal = new float[this.width*this.height];
		int ct = 0;
		for(int row = 0; row < this.height; row ++) {
			for(float val: this.vals[row]) {
				retVal[ct] = val;
				ct ++;
			}
		}
		
		return retVal;
	}
	
	public float[] getRow(int index) {
		return this.vals[index];
	}
	
	public void print() {
		for(int i = 0; i < this.height; i ++) {
			for(int ii = 0; ii < this.width; ii ++) {
				System.out.print(vals[i][ii]);
			}
			System.out.println("");
		}
	}
	
	public float[] getCol(int index) {
		
		float[] retVal = new float[this.height];
		
		for(int i = 0; i < this.height; i ++) {
			retVal[i] = this.vals[i][index];
		}
		
		return retVal;
		
	}
			
}

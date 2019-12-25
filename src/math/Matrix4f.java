package math;

import camera.CameraSpecs;

public class Matrix4f {
	
	private float[][] vals;

	public Matrix4f(float... vals) {
		this.assignVals(vals);
	}
	
	public static Vector4f transform(Matrix4f mat, Vector4f vec) {
		float a = vec.dotProduct(new Vector4f(mat.getRow(0)));
		float b = vec.dotProduct(new Vector4f(mat.getRow(1)));
		float c = vec.dotProduct(new Vector4f(mat.getRow(2)));
		float d = vec.dotProduct(new Vector4f(mat.getRow(3)));
		return new Vector4f(a, b, c, d);
	}
	
	public static Matrix4f viewMatrix(Transform transform) {
		return new Matrix4f(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		)
		.multiply(transform.xRotation(1))
		.multiply(transform.yRotation(1))
		.multiply(transform.zRotation(1))
		.multiply(transform.calculateTranslation(true).multiply(transform.calculateScale()));
	}
	
	public static Matrix4f perspective(CameraSpecs camSpecs) {
		
		float f = (float) (1f / Math.tan(Math.toRadians(camSpecs.getFov()) / 2f));

        float a = f / camSpecs.getAspect();
        float b = f;
        float c = camSpecs.getSumZ()/camSpecs.getDiffZ();
        float d = (2f * camSpecs.getzFar() * camSpecs.getzNear()) / camSpecs.getDiffZ();

        return new Matrix4f( 
        		a, 0, 0, 0,
        		0, b, 0, 0,
        		0, 0, c, -1,
        		0, 0, d, 0			
        );
		
	}
	
	public static Matrix4f orthographic(float width, float height, float length) {
		return new Matrix4f( 
				2f/width, 0,         0,          0,
				0, 		  2f/height, 0,          0,
				0,        0,         -2f/length, 0,
				0,        0,         0,          1
		);
	}
	
	
	public int storeData(float[] data, int pointer) {
		
		data[pointer++] = this.vals[0][0];
		data[pointer++] = this.vals[0][1];
		data[pointer++] = this.vals[0][2];
		data[pointer++] = this.vals[0][3];
		data[pointer++] = this.vals[1][0];
		data[pointer++] = this.vals[1][1];
		data[pointer++] = this.vals[1][2];
		data[pointer++] = this.vals[1][3];
		data[pointer++] = this.vals[2][0];
		data[pointer++] = this.vals[2][1];
		data[pointer++] = this.vals[2][2];
		data[pointer++] = this.vals[2][3];
		data[pointer++] = this.vals[3][0];
		data[pointer++] = this.vals[3][1];
		data[pointer++] = this.vals[3][2];
		data[pointer++] = this.vals[3][3];
		
		return pointer;
		
	}
	
	public void transpose3x3(Matrix4f other) {
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
	
	private void assignVals(float[] vals) {
		this.vals = new float[4][4];
		
		for(int i = 0; i < 16; i ++) {
			this.vals[i/4][i%4] = vals[i];
		}
	}
	
	public float[][] getVals() {
		return this.vals;
	}
	
	public static Matrix4f getIdentity() {
		return new Matrix4f(1, 0, 0, 0,
							0, 1, 0, 0,
							0, 0, 1, 0,
							0, 0, 0, 1);
	}

	
	public Matrix4f multiply(Matrix4f other) {
		
		float[] newMatrix = new float[16];
		int ct = 0;
		
		for(int row = 0; row < 4; row ++){
			for(int col = 0; col < 4; col ++) {
				float[] vec1 = other.getRow(row);
				float[] vec2 = this.getCol(col);
				newMatrix[ct] = this.dotProduct(vec1, vec2);
				ct ++;
			}
		}
		
		this.assignVals(newMatrix);
		
		return new Matrix4f(newMatrix);
	}
	
	public void multiplyScalar(float val) {
		for(int i = 0; i < 4; i ++) {
			for(int j = 0; j < 4; j ++) {
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
		float[] retVal = new float[16];
		int ct = 0;
		for(int row = 0; row < 4; row ++) {
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
		for(int i = 0; i < 4; i ++) {
			for(int ii = 0; ii < 4; ii ++) {
				System.out.print(vals[i][ii] + ",");
			}
			System.out.println("");
		}
	}
	
	public float[] getCol(int index) {
		
		float[] retVal = new float[4];
		
		for(int i = 0; i < 4; i ++) {
			retVal[i] = this.vals[i][index];
		}
		
		return retVal;
		
	}
	
	public Matrix4f getInverse() {
		float inv[] = new float[16];
		float[] invOut = new float[16];
		float det;
	    int i;

	    float[] m = this.getFloats();
	    
	    inv[0] = m[5]  * m[10] * m[15] - 
	             m[5]  * m[11] * m[14] - 
	             m[9]  * m[6]  * m[15] + 
	             m[9]  * m[7]  * m[14] +
	             m[13] * m[6]  * m[11] - 
	             m[13] * m[7]  * m[10];

	    inv[4] = -m[4]  * m[10] * m[15] + 
	              m[4]  * m[11] * m[14] + 
	              m[8]  * m[6]  * m[15] - 
	              m[8]  * m[7]  * m[14] - 
	              m[12] * m[6]  * m[11] + 
	              m[12] * m[7]  * m[10];

	    inv[8] = m[4]  * m[9] * m[15] - 
	             m[4]  * m[11] * m[13] - 
	             m[8]  * m[5] * m[15] + 
	             m[8]  * m[7] * m[13] + 
	             m[12] * m[5] * m[11] - 
	             m[12] * m[7] * m[9];

	    inv[12] = -m[4]  * m[9] * m[14] + 
	               m[4]  * m[10] * m[13] +
	               m[8]  * m[5] * m[14] - 
	               m[8]  * m[6] * m[13] - 
	               m[12] * m[5] * m[10] + 
	               m[12] * m[6] * m[9];

	    inv[1] = -m[1]  * m[10] * m[15] + 
	              m[1]  * m[11] * m[14] + 
	              m[9]  * m[2] * m[15] - 
	              m[9]  * m[3] * m[14] - 
	              m[13] * m[2] * m[11] + 
	              m[13] * m[3] * m[10];

	    inv[5] = m[0]  * m[10] * m[15] - 
	             m[0]  * m[11] * m[14] - 
	             m[8]  * m[2] * m[15] + 
	             m[8]  * m[3] * m[14] + 
	             m[12] * m[2] * m[11] - 
	             m[12] * m[3] * m[10];

	    inv[9] = -m[0]  * m[9] * m[15] + 
	              m[0]  * m[11] * m[13] + 
	              m[8]  * m[1] * m[15] - 
	              m[8]  * m[3] * m[13] - 
	              m[12] * m[1] * m[11] + 
	              m[12] * m[3] * m[9];

	    inv[13] = m[0]  * m[9] * m[14] - 
	              m[0]  * m[10] * m[13] - 
	              m[8]  * m[1] * m[14] + 
	              m[8]  * m[2] * m[13] + 
	              m[12] * m[1] * m[10] - 
	              m[12] * m[2] * m[9];

	    inv[2] = m[1]  * m[6] * m[15] - 
	             m[1]  * m[7] * m[14] - 
	             m[5]  * m[2] * m[15] + 
	             m[5]  * m[3] * m[14] + 
	             m[13] * m[2] * m[7] - 
	             m[13] * m[3] * m[6];

	    inv[6] = -m[0]  * m[6] * m[15] + 
	              m[0]  * m[7] * m[14] + 
	              m[4]  * m[2] * m[15] - 
	              m[4]  * m[3] * m[14] - 
	              m[12] * m[2] * m[7] + 
	              m[12] * m[3] * m[6];

	    inv[10] = m[0]  * m[5] * m[15] - 
	              m[0]  * m[7] * m[13] - 
	              m[4]  * m[1] * m[15] + 
	              m[4]  * m[3] * m[13] + 
	              m[12] * m[1] * m[7] - 
	              m[12] * m[3] * m[5];

	    inv[14] = -m[0]  * m[5] * m[14] + 
	               m[0]  * m[6] * m[13] + 
	               m[4]  * m[1] * m[14] - 
	               m[4]  * m[2] * m[13] - 
	               m[12] * m[1] * m[6] + 
	               m[12] * m[2] * m[5];

	    inv[3] = -m[1] * m[6] * m[11] + 
	              m[1] * m[7] * m[10] + 
	              m[5] * m[2] * m[11] - 
	              m[5] * m[3] * m[10] - 
	              m[9] * m[2] * m[7] + 
	              m[9] * m[3] * m[6];

	    inv[7] = m[0] * m[6] * m[11] - 
	             m[0] * m[7] * m[10] - 
	             m[4] * m[2] * m[11] + 
	             m[4] * m[3] * m[10] + 
	             m[8] * m[2] * m[7] - 
	             m[8] * m[3] * m[6];

	    inv[11] = -m[0] * m[5] * m[11] + 
	               m[0] * m[7] * m[9] + 
	               m[4] * m[1] * m[11] - 
	               m[4] * m[3] * m[9] - 
	               m[8] * m[1] * m[7] + 
	               m[8] * m[3] * m[5];

	    inv[15] = m[0] * m[5] * m[10] - 
	              m[0] * m[6] * m[9] - 
	              m[4] * m[1] * m[10] + 
	              m[4] * m[2] * m[9] + 
	              m[8] * m[1] * m[6] - 
	              m[8] * m[2] * m[5];

	    det = m[0] * inv[0] + m[1] * inv[4] + m[2] * inv[8] + m[3] * inv[12];

	    if (det == 0)
	        return Matrix4f.getIdentity();

	    det = 1f / det;

	    for (i = 0; i < 16; i++)
	        invOut[i] = inv[i] * det;

	    return new Matrix4f(invOut);
	}
	
}
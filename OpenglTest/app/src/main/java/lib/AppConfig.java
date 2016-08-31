package lib;

public class AppConfig {
	/**
	 * 投影矩阵
	 */
	public Matrix4f gMatProject = new Matrix4f();
	/**
	 * 视图矩阵
	 */
	public Matrix4f gMatView = new Matrix4f();
	/**
	 * 视口参数
	 */
	public int[] gpViewport = new int[4];
	/**
	 * 当前系统的投影矩阵，列序填充
	 */
	public float[] gpMatrixProjectArray = new float[16];
	/**
	 * 当前系统的视图矩阵，列序填充
	 */
	public float[] gpMatrixViewArray = new float[16];

	public float gScreenX, gScreenY;

	public void setTouchPosition(float x, float y) {
		gScreenX = x;
		gScreenY = y;
	}

}
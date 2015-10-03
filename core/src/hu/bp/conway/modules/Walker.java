package hu.bp.conway.modules;

public class Walker implements Runnable {

	public final Universe out;
	public final Universe in;
	public final int row, col, rows, cols; 

	public Walker(Universe in, Universe out, int topLeftRow, int topLeftCol, int rows, int cols) {
		this.out = out;
		this.in = in;
		row = topLeftRow;
		col = topLeftCol;
		this.rows = rows;
		this.cols = cols;
	}

	@Override
	public void run() {
		for (int r = row; r < row + rows; r++) {
			for (int c = col; c < col + cols; c++) {
				if ((r < in.n) && (c < in.n) && (r >= 0) && (c >= 0)) {
					out.set(r, c, Rules.apply(in, r, c));
				}
			}
		}
	}


}

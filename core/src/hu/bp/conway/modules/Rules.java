package hu.bp.conway.modules;

public class Rules {

	public static int liveNeighbours(Universe u, int row, int col) {
		int num = 0;
		for (int r = row - 1; r < row + 2; r++) {
			for (int c = col - 1; c < col + 2; c++) {
				if ( r == row && c == col) {
					continue;
				}
				num += u.isAlive(r, c) ? 1 : 0;
			}
		}
		return num;
	}

	public static boolean apply(Universe u, int row, int col) {
		boolean live = false;

		boolean isAlive = u.isAlive(row, col);

		int num = liveNeighbours(u, row, col);
		//Any live cell with fewer than two live neighbours dies,
		// as if caused by under-population.
		if (isAlive && num < 2) {
			live = false;
		}
		//Any live cell with two or three live neighbours
		// lives on to the next generation.
		else if (isAlive && (num == 2 || num == 3)) {
			live = true;
		}
		//Any live cell with more than three live neighbours dies,
		// as if by overcrowding.
		else if (isAlive && num > 3) {
			live = false;
		}
		//Any dead cell with exactly three live neighbours
		// becomes a live cell, as if by reproduction.
		else if (!isAlive && num == 3) {
			live = true;
		}

		return live;
	}

}

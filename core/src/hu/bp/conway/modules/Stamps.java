package hu.bp.conway.modules;

public class Stamps {
	public static Universe getHorizontalBlinker() {
		String s[] = {
			"     ",
			"     ",
			" *** ",
			"     ",
			"     "};

		return new Universe(s);
	}

	public static Universe getVerticalBlinker() {
		String s[] = {
			"     ",
			"  *  ",
			"  *  ",
			"  *  ",
			"     "};

		return new Universe(s);
	}

}

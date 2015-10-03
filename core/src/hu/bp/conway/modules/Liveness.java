package hu.bp.conway.modules;

public enum Liveness {
	DEAD(0, "-"),
	LIVE(1, "*");

	public final int n;
	public final String c;

	Liveness(int n, String c) {
		this.n = n;
		this.c = c;
	}

	public static Liveness get(boolean live){
		return (live) ? LIVE : DEAD;
	}

	public static Liveness get(String s){
		return (LIVE.c.equals(s)) ? LIVE : DEAD;
	}

}

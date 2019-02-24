package cpaintus.models;

public class FlipTransform {
	
	private Flip flipType;
	private Point pivot;

	public FlipTransform(Flip flipType, Point pivot) {
		this.flipType = flipType;
		this.pivot = pivot;
	}
	
	public Flip getType() {
		return flipType;
	}
	
	public Point getPivot() {
		return pivot;
	}
}

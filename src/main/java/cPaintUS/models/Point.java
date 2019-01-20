package cPaintUS.models;

public class Point {
	private double _x;
	private double _y;
	
	public Point() {
		_x = 0;
		_y = 0;
	}
	
	public Point(double x, double y) {
		_x = x;
		_y = y;
	}
	
	public double getX() {
		return _x;
	}
	
	public void setX(double x) {
		_x = x;
	}
	
	public double getY() {
		return _y;
	}
	
	public void setY(double y) {
		_y = y;
	}
}

package cPaintUS.controllers.drawers;

import cPaintUS.models.shapes.Heart;
import cPaintUS.models.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HeartDrawerStrategy implements IDrawerStrategy {

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		drawFillHeart(gc, (Heart) shape);
		drawStrokeHeart(gc, shape);
	}

	private void calculHeartShape(double x, double y, double w, double h, double[] xAxis, double[] yTop, double[] yBottom, int size) {
		int j = 0;
		for(double i = -w/2 ; i <= 0; i+=0.5 ) {
			yTop[j] = -h * Math.sqrt(1 - ((Math.abs(4*i/w)-1)*(Math.abs(4*i/w)-1))) /4;
			yBottom[j] = h * 3 * Math.sqrt(1- (Math.sqrt(Math.abs(4*i/w)) / Math.sqrt(2)) ) / 4;
			yTop[j] = yTop[j] + y + h/4;
			yBottom[j] = yBottom[j] + y + h/4;
			xAxis[j] = i+w/2 + x;
			if(i != 0) {
				yTop[size-1-j] = yTop[j];
				yBottom[size-1-j] = yBottom[j];
				xAxis[size-1-j] = w-(i+(w/2)) + x;
			}
			j++;
		}
	}
	
	private void drawStrokeHeart(GraphicsContext gc, Shape heart) {
		int size = 2*(int)(heart.getWidth()- heart.getLineWidth())+1;
		if(size < 0)
			size = 0;
		double[] yTop = new double[size];
		double[] yBottom = new double[size];
		double[] xAxis = new double[size];
		calculHeartShape(heart.getLineWidth()/2, heart.getLineWidth()/2, heart.getWidth() - heart.getLineWidth(), heart.getHeight()- heart.getLineWidth(), xAxis, yTop, yBottom, size);
		gc.setStroke(Color.web(heart.getStrokeColor()));
		gc.setLineWidth(heart.getLineWidth());
		
		double[] xAxisLeft = new double[size/2];
		double[] xAxisRight = new double[size%2==1 ? size/2+1 : size/2];
		System.arraycopy(xAxis, 0, xAxisLeft, 0, size/2);
		System.arraycopy(xAxis, size/2, xAxisRight, 0, size%2==1 ? size/2+1 : size/2);
		
		double[] yTopLeft = new double[size/2];
		double[] yTopRight = new double[size%2==1 ? size/2+1 : size/2];
		System.arraycopy(yTop, 0, yTopLeft, 0, size/2);
		System.arraycopy(yTop, size/2, yTopRight, 0, size%2==1 ? size/2+1 : size/2);
		
		double[] yBottomLeft = new double[size/2];
		double[] yBottomRight = new double[size%2==1 ? size/2+1 : size/2];
		System.arraycopy(yBottom, 0, yBottomLeft, 0, size/2);
		System.arraycopy(yBottom, size/2, yBottomRight, 0, size%2==1 ? size/2+1 : size/2);
		
		gc.strokePolyline(xAxisLeft, yTopLeft, size/2);
		gc.strokePolyline(xAxisLeft, yBottomLeft, size/2);
		gc.strokePolyline(xAxisRight, yTopRight, size%2==1 ? size/2+1 : size/2);
		gc.strokePolyline(xAxisRight, yBottomRight, size%2==1 ? size/2+1 : size/2);
	}
	
	private void drawFillHeart(GraphicsContext gc, Heart heart) {

		int size = 2*(int)heart.getWidth()+1;
		double[] yTop = new double[size];
		double[] yBottom = new double[size];
		double[] xAxis = new double[size];
		calculHeartShape(heart.getLineWidth()/2, heart.getLineWidth()/2, heart.getWidth() - heart.getLineWidth(), heart.getHeight() - heart.getLineWidth(), xAxis, yTop, yBottom, size);
		for(int i = 0 ; i<size; i++) {
			gc.setStroke(Color.web(heart.getFillColor()));
			gc.setLineWidth(1);
			gc.strokeLine(xAxis[i], yTop[i], xAxis[i], yBottom[i]);
		}
	}

}

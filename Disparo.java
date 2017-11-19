import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

public class Disparo {
	protected double x;
	protected double y;
	protected double angulo;
	private double velocidade;
	
	public Disparo(double x, double y, double angulo) {
		this.x = x;
		this.y = y;
		this.angulo = angulo;
		velocidade = 3;
	}
	
	public void mover(){
		x += Math.sin(Math.toRadians(angulo)) * velocidade;
		y -= Math.cos(Math.toRadians(angulo)) * velocidade;
		x = (double)Math.round(x * 1000d) / 1000d;
		y = (double)Math.round(y * 1000d) / 1000d;
	}
	
	public boolean remover() {
		return x > Arena.largura || x < 0 || y > Arena.altura || y < 0;
	}
	
	public boolean acertou(double x, double y) {
		if(Math.hypot(this.x-x, this.y-y) < 30)
			return true;
		return false;
	}
	
	public void draw(Graphics2D g2d){
		//Armazenamos o sistema de coordenadas original.
		AffineTransform antes = g2d.getTransform();
		//Criamos um sistema de coordenadas para o disparo.
		AffineTransform depois = new AffineTransform();
		depois.translate(x, y);
		depois.rotate(Math.toRadians(angulo));
		//Aplicamos o sistema de coordenadas.
		g2d.transform(depois);
		//Desenhamos o disparo
		g2d.setColor(Color.RED);
		g2d.fillOval(-3, -3, 6, 6);
		//Aplicamos o sistema de coordenadas
		g2d.setTransform(antes);
	}
}

package tanque.tanque;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

public class Tanque implements Serializable{
	protected double x,y;
	protected double angulo;
	protected int anguloc,cont=0;
	protected double velocidade;
	protected Color cor;
	protected boolean estaAtivo;
	private long tempo;
	private int id;
	
	public Tanque(double x, double y, double a, Color cor, int id){
		this.x = x; this.y = y; this.angulo = a;
		this.cor = cor; velocidade = 1;
		this.estaAtivo = false;
		this.id = id;
	}
	public void calculaTempo(){
		if(! estaAtivo){
			if(System.currentTimeMillis() - tempo > 5000){
				if(velocidade > 0) 
					velocidade = 2;
				else
					velocidade = -2;
			}
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getTempo() {
		return tempo;
	}
	public void setTempo(long agora){
		this.tempo = agora; 
	}
	public void aumentarVelocidade(){
		
		if(velocidade < 5) velocidade++;
	}
	public void diminuirVelocidade(){
		
		if(velocidade > 0) velocidade--;
	}
	public void girarHorario(int a){
		angulo += a;
		if(angulo >= 360)
			angulo = angulo - 360;
	}
	public void girarAntiHorario(int a){
		angulo -= a;
		if(angulo <= 0)
			angulo = 360-a;
	}
	
	public void mover(){

		x = x + Math.sin(Math.toRadians(angulo)) * velocidade;
		y = y - Math.cos(Math.toRadians(angulo)) * velocidade;
		
		if (x<=30 ){
			if(angulo >= 270 && angulo < 360) angulo = 360 - angulo;
			if(angulo > 180 && angulo <= 270) angulo = 360 - angulo;
			if(velocidade < 0){
				velocidade *= -1;
				girarHorario(5);
			}
			
		}
		if (y<=30){
			if(angulo > 270 && angulo <= 360) angulo = 360 - angulo + 180;
			if(angulo >= 0 && angulo < 90) angulo = 360 - angulo - 180;
			if(velocidade < 0){
				velocidade *= -1;
			}
		}
		if (y>=450){
			if(angulo > 90 && angulo < 180) angulo = 360 - angulo - 180;
			if(angulo >= 180 && angulo < 270) angulo = 360 - angulo + 180;
			if(velocidade < 0){
				velocidade *= -1;
				girarAntiHorario(5);
			}
			
		}
		if (x>=610){
			if(angulo > 0 && angulo <= 90) angulo= 360 - angulo;
			if(angulo >= 90 && angulo < 180) angulo= 360 - angulo;
			if(velocidade < 0){
				velocidade *= -1;
			}
		}
		
	}
	
	public void setEstaAtivo(boolean estaAtivo){
		this.estaAtivo = estaAtivo;
	}
	public void draw(Graphics2D g2d){
		//Armazenamos o sistema de coordenadas original.
		AffineTransform antes = g2d.getTransform();
		//Criamos um sistema de coordenadas para o tanque.
		AffineTransform depois = new AffineTransform();
		depois.translate(x, y);
		depois.rotate(Math.toRadians(angulo));
		//Aplicamos o sistema de coordenadas.
		g2d.transform(depois);
		//Desenhamos o tanque. Primeiro o corpo
		g2d.setColor(cor);
		g2d.fillRect(-10, -12, 20, 24);
		//Agora as esteiras
		for(int i = -12; i <= 8; i += 4){
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(-15, i, 5, 4);
			g2d.fillRect(10, i, 5, 4);
			g2d.setColor(Color.BLACK);
			g2d.fillRect(-15, i, 5, 4);
			g2d.fillRect(10, i, 5, 4);
		}
		//O canh�o
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(-3, -25, 6, 25);
		g2d.setColor(cor);
		g2d.drawRect(-3, -25, 6, 25);
		//Se o tanque estiver ativo
		//Desenhamos uma margem
		if(estaAtivo){
			g2d.setColor(new Color(120,120,120));
			Stroke linha = g2d.getStroke();
			g2d.setStroke(new BasicStroke(1f,BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND,0,
					new float[]{8},0));
			g2d.drawRect(-24, -32, 48, 55);
			g2d.setStroke(linha);
		}
		//Aplicamos o sistema de coordenadas
		g2d.setTransform(antes);
	}
	
	public Shape getRectEnvolvente(){
		AffineTransform at = new AffineTransform();
		at.translate(x,y);
		at.rotate(Math.toRadians(angulo));
		Rectangle rect = new Rectangle(-24,-32,48,55);
		return at.createTransformedShape(rect);
	}
	
}
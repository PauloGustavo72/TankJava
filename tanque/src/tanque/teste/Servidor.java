package tanque.teste;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import tanque.tanque.Arena;
import tanque.tanque.Tanque;

public class Servidor {


	
	private int porta;
	private List<PrintStream> clientes;

	public Servidor(int porta) {
		this.porta = porta;
		this.clientes = new ArrayList<PrintStream>();
	}

	public void executa() throws IOException {
		ServerSocket servidor = new ServerSocket(this.porta);
		System.out.println("Porta 12345 aberta!");
		Arena arena = new Arena(640,480);
		arena.adicionaTanque(new Tanque(400,50,180,Color.BLUE,1));
		arena.adicionaTanque(new Tanque(400,200,0,Color.RED,2));
		
		
		JFrame janela = new JFrame("JTank");
		janela.getContentPane().add(arena);
		janela.setResizable(false);
		janela.pack();
		janela.setVisible(true);
		janela.setDefaultCloseOperation(3);

		while (true) {
			
			
			Socket cliente = servidor.accept();
	    ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
		    
		    try {
				Tanque teste = (Tanque) entrada.readObject();
				arena.adicionaTanque(teste);
			} catch (ClassNotFoundException e) {
				System.out.println("não leu o tanque");
				e.printStackTrace();
			}
		    
			//entrada.close();
			
			
			
			
			System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
			// adiciona saida do cliente à lista
			PrintStream ps = new PrintStream(cliente.getOutputStream());
			this.clientes.add(ps);

			// cria tratador de cliente numa nova thread
			TrataCliente tc = new TrataCliente(cliente.getInputStream(), this);
			new Thread(tc).start();
		}

	}

	public void distribuiMensagem(String msg) {
		// envia msg para todo mundo
		for (PrintStream cliente : this.clientes) {
			cliente.println(msg);
		}
	}
}

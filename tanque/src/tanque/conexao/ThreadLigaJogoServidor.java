package tanque.conexao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import tanque.tanque.Arena;
import tanque.tanque.Tanque;

public class ThreadLigaJogoServidor implements Runnable {
	
	Socket cliente;
	Arena arena;
	JFrame janela;
	
	public ThreadLigaJogoServidor(Socket cliente, Arena arena, JFrame janela) {
		this.cliente = cliente;
		this.arena = arena;
		this.janela = janela;
	
	}
	
	@Override
	public void run() {
		 	ObjectInputStream entrada = null;
			try {
				entrada = new ObjectInputStream(cliente.getInputStream());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		 	ObjectOutputStream saidaTeste = null;
			try {
				
				Tanque teste = (Tanque) entrada.readObject();
				arena.adicionaTanque(teste);
			} 
			 catch (IOException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			    
			    try {
					saidaTeste.writeObject(janela);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
	}

}

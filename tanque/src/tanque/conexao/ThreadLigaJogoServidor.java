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
	
	ObjectInputStream entrada;
	ObjectOutputStream saidaTeste;
	
	public ThreadLigaJogoServidor(Socket cliente, Arena arena, JFrame janela, ObjectInputStream entrada, ObjectOutputStream saidaTeste) {
		this.cliente = cliente;
		this.arena = arena;
		this.janela = janela;
		this.entrada = entrada;
		this.saidaTeste = saidaTeste;
	}
	
	@Override
	public void run() {
		 	
			try {
				entrada = new ObjectInputStream(cliente.getInputStream());
				
				Tanque teste = (Tanque) entrada.readObject();
				arena.adicionaTanque(teste);
				saidaTeste.writeObject(janela);
			} catch (IOException | ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			    
	}

}

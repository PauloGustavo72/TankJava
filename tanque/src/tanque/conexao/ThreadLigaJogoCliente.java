package tanque.conexao;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import tanque.tanque.Tanque;

public class ThreadLigaJogoCliente implements Runnable {

	ObjectOutputStream saidaTeste;
	ObjectInputStream entrada;
	int cont = 0;

	public ThreadLigaJogoCliente(ObjectOutputStream saidaTeste, ObjectInputStream entrada ) {
		this.saidaTeste = saidaTeste;
		this.entrada = entrada;
	}

	@Override
	public void run() {

		
		while (true) {

			try {
				
				
				if (cont == 0) {
					saidaTeste.writeObject(new Tanque(400, 200, 0, Color.RED, 2));
					cont = 1;
				}
				JFrame janela = (JFrame) entrada.readObject();
				janela.setVisible(true);
				System.out.println("entrou no arena cliente");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}

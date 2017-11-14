package tanque.conexao;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import tanque.tanque.Tanque;

public class ThreadLigaJogoCliente implements Runnable {

	Socket cliente;
	int cont = 0;

	public ThreadLigaJogoCliente(Socket cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {

		ObjectOutputStream saidaTeste;
		ObjectInputStream entrada = null;

		while (true) {
			try {
				saidaTeste = new ObjectOutputStream(cliente.getOutputStream());
				entrada = new ObjectInputStream(cliente.getInputStream());

				if (cont == 0) {
					saidaTeste.writeObject(new Tanque(400, 200, 0, Color.RED, 2));
					cont = 1;
				}

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				JFrame janela = (JFrame) entrada.readObject();
				janela.setVisible(true);
				System.out.println("entrou no arena cliente");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

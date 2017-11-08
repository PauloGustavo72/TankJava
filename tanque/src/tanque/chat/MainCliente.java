package tanque.chat;

import java.io.IOException;

public class MainCliente {

	public static void main(String[] args) throws IOException {
		Cliente app = new Cliente();
		   app.conectar();
		   app.escutar();
	}

}

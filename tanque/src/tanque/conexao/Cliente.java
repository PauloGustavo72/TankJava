package tanque.conexao;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import tanque.tanque.Arena;
import tanque.tanque.Tanque;

public class Cliente {
	   public static void main(String[] args) 
	         throws UnknownHostException, IOException {
	     // dispara cliente
	     new Cliente("127.0.0.1", 12345).executa();
	   }
	   
	   private String host;
	   private int porta;
	   
	   public Cliente (String host, int porta) {
	     this.host = host;
	     this.porta = porta;
	   }
	   
	   public void executa() throws UnknownHostException, IOException {
	     Socket cliente = new Socket(this.host, this.porta);
	     System.out.println("O cliente se conectou ao servidor!");
	    
	     
	     ObjectOutputStream saidaTeste = new ObjectOutputStream(cliente.getOutputStream());
	     ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
	     saidaTeste.writeObject(new Tanque(400,200,0,Color.RED,2));
	     
	     try {
			Arena arena = (Arena) entrada.readObject();
			arena.setVisible(true);
			System.out.println("entrou no arena cliente");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	     
//	     saidaTeste.close();
	     
	     
	     // thread para receber mensagens do servidor
	     Recebedor r = new Recebedor(cliente.getInputStream());
	     new Thread(r).start();
	     
	     // lê msgs do teclado e manda pro servidor
	     Scanner teclado = new Scanner(System.in);
	     PrintStream saida = new PrintStream(cliente.getOutputStream());
	     while (teclado.hasNextLine()) {
	       saida.println(teclado.nextLine());
	     }
	     
	     saida.close();
	     teclado.close();
	     cliente.close();    
	   }
	 }
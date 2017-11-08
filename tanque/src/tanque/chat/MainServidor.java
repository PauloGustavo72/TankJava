package tanque.chat;

import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MainServidor {

	public static void main(String[] args) {
		 try{
			    //Cria os objetos necessário para instânciar o servidor
			    JLabel lblMessage = new JLabel("Porta do Servidor:");
			    JTextField txtPorta = new JTextField("12345");
			    Object[] texts = {lblMessage, txtPorta };  
			    JOptionPane.showMessageDialog(null, texts);
			    ServerSocket server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
			    ArrayList<BufferedWriter> clientes = new ArrayList<BufferedWriter>();
			    JOptionPane.showMessageDialog(null,"Servidor ativo na porta: "+         
			    txtPorta.getText());
			   
			     while(true){
			       System.out.println("Aguardando conexão...");
			       Socket con = server.accept();
			       System.out.println("Cliente conectado...");
			       Thread t = new Servidor(con);
			        t.start();   
			    }
			                             
			  }catch (Exception e) {
			   
			    e.printStackTrace();
			  }                       
			 }
	}



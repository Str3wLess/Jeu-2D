package main;

import javax.swing.JFrame;

public class Main 
{
	public static void main(String[] args)
	{
		JFrame window = new JFrame();
		// ferme la fenêtre quand on appuie sur X
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		window.setResizable(false);
		window.setTitle("2D Adventure");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		// Permet à la fenètre de s'adapter à la taille préféré 
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
		
	}
}

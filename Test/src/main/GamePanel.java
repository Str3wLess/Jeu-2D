package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

// cette class hérite de JPanel  
public class GamePanel extends JPanel implements Runnable{
	
	// PARAMETRE DE L'ECRAN
	final int originalTileSize = 16; //16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;//768 pixels
	public final int screenHeight = tileSize * maxScreenRow;//576 pixels
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public Player player = new Player(keyH,this);
	 
	public GamePanel()
	{
		// Met la taille de la classe Jpanel
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		//Si true, tous les dessins du composant vont être fait dans un buffer
		//en arrière plan, ce qui va améliorer les performences
		this.setDoubleBuffered(true); 
		//Permet à GamePanel de reconnaître l'appuie d'une touche
		this.addKeyListener(keyH);
		//Permet à GamePanel d'être "concentrer" pour récupérer les touches
		this.setFocusable(true);
	}
	
	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}

	//méthode "sleep"
	@Override
	/*public void run()
	{
		//permet de "créer 60 fps
		double drawInterval = 1000000000/FPS; // 0.01666 secondes
		//interval de 0,01666 entre les écrans
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		
		
		//Tant que gameThread existe, la boucle sera répété 
		while(gameThread != null)
		{
			//Retourne la valeur actuel de Java Virtual Machine source de temps
			//On utilise nano car c'est plus précis
			//long currentTime = System.nanoTime();
			//System.out.println("Temps actuel:" + currentTime);
			
			
			// 1 met à jour les informations comme la position du personnage
			update();
			
			// 2 dessiner l'écran avec des informations mise à jour 
			repaint();
			
			
			
			try
			{
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				// si update et repain prend plus de temps que drawInterval, alors
				//Thread n'a pas besoin de "sleep"
				if(remainingTime < 0)
				{
					remainingTime = 0;
				}
				//sleep utilise des milisecondes donc il faut convertir les nano en mil
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}*/
	
	//méthode "delta"
	public void run()
	{
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null)
		{
			
			currentTime = System.nanoTime();
			
			//chaques boucles, on ajoute le temps passé diié a drawInterval à delta
			//quand delta atteint l'interval, on update et repaint puis on reset le delta
			delta += (currentTime - lastTime) / drawInterval;
			
			//timer est utiliser pour compter le nombre de FPS
			timer += (currentTime - lastTime);
			
			lastTime = currentTime;
			
			if (delta >= 1)
			{
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			//timer est utiliser pour compter le nombre de FPS
			if (timer >= 1000000000)
			{
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update()
	{
		player.update();
	}
	
	public void paintComponent(Graphics g)
	{
		// à chaque fois qu'on utilise paintComponent sur JPanel, il faut écrire ça
		super.paintComponent(g);
		
		//Permet de convertir Graphics g en la classe Graphics2D g
		//Graphics2D g à plus de fonctions que Graphics g
		Graphics2D g2 = (Graphics2D)g;
		
		//tile est avant player car c'est un layer (si tile est apprès alors il cachera player)
		tileM.draw(g2);
		
		player.draw(g2);
		
		g2.dispose();
	}
 
}

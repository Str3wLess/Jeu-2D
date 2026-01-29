package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[] [];
	
	
	public TileManager(GamePanel gp)
	{
		this.gp = gp;
		
		tile = new Tile[10]; // nombre de tiles qui seront créer (peut être modifier)
		mapTileNum = new int[gp.maxScreenCol] [gp.maxScreenRow];
		
		getTileImage();
		loadMap("/maps/map01.txt");
	}
	
	public void getTileImage()
	{
		try {
			tile[0] =  new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass1.png"));
			
			tile[1] =  new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Rock1.png"));
			
			tile[2] =  new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Water1.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath)
	{
		try {
			
			//Importe le txt
			InputStream is = getClass().getResourceAsStream(filePath);
			
			//lit le contenu du txt
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
				
				//lit la ligne et la stock dans line
				String line = br.readLine();
				
				while (col < gp.maxScreenCol)
				{
					//split sépare une ligne afin d'avoir des numéros de tiles un par un 
					String numbers[] = line.split(" ");
					
					//converti de string en integer afin de pouvoir les utiliser en temps que nombre
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col] [row] = num;
					col++;
				}
				
				//permet de changer de ligne une fois la bordure de la carte atteinte
				if(col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
				
			}
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < gp.maxScreenCol && row < gp.maxScreenRow)
		{
			//mapTileNum contient les données de la cartes(le .txt)
			int tileNum = mapTileNum[col] [row];
			
			//en fonction du numéro de tileNum, dessine une tile différente
			g2.drawImage(tile[tileNum].image,x ,y , gp.tileSize, gp.tileSize, null );
			col++;
			x += gp.tileSize;
			
			if (col == gp.maxScreenCol)
			{
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}
		
		
	}

}

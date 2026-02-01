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
	public Tile[] tile;
	public int mapTileNum[] [];
	
	
	public TileManager(GamePanel gp)
	{
		this.gp = gp;
		
		tile = new Tile[10]; // nombre de tiles qui seront créer (peut être modifier)
		mapTileNum = new int[gp.maxWorldCol] [gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/map01.txt");
	}
	
	public void getTileImage()
	{
		try {
			tile[0] =  new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass1.png"));
			
			tile[1] =  new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rock1.png"));
			tile[1].collision = true;
			
			tile[2] =  new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water1.png"));
			tile[1].collision = true;
			
			
			tile[3] =  new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth1.png"));
			
			tile[4] =  new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree1.png"));
			
			tile[5] =  new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand1.png"));
			
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
			
			while (col < gp.maxWorldCol && row < gp.maxScreenRow) {
				
				//lit la ligne et la stock dans line
				String line = br.readLine();
				
				while (col < gp.maxWorldCol)
				{
					//split sépare une ligne afin d'avoir des numéros de tiles un par un 
					String numbers[] = line.split(" ");
					
					//converti de string en integer afin de pouvoir les utiliser en temps que nombre
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col] [row] = num;
					col++;
				}
				
				//permet de changer de ligne une fois la bordure de la carte atteinte
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
				
			}
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;

		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow)
		{
			//mapTileNum contient les données de la cartes(le .txt)
			int tileNum = mapTileNum[worldCol] [worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			//permet d'afficher seulement les tiles qui sont sur l'écran
			if(worldX +gp.tileSize > gp.player.worldX - gp.player.screenX &&
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize > gp.player.worldX - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldX + gp.player.screenY) {
				
			}
			
			//en fonction du numéro de tileNum, dessine une tile différente
			g2.drawImage(tile[tileNum].image,screenX ,screenY , gp.tileSize, gp.tileSize, null );
			worldCol++;
			
			if (worldCol == gp.maxWorldCol)
			{
				worldCol = 0;
				worldRow++;
			}
		}
		
		
	}

}

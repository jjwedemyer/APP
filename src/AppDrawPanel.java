package app.draw;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

class AppDrawPanel extends JPanel {
	BufferedImage    img, grey;
	boolean[][][] cells;
	static final int ORG     = 0;
	static final int GREY    = 1;
	static final int PATTERN = 2;
	static final int THREAD = 3;
	protected int    mode;
	public Dimension getPreferredSize()
	{
		return new Dimension(1400, 900);
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (mode == ORG)
		{
			int rgb = 0;
			for (int x = 0; x < img.getWidth(); x++)
			{
				for (int y = 0; y < img.getHeight(); y++)
				{
					g.setColor(new Color(img.getRGB(x, y)));
					g.fillRect(10 + x * 2, 10 + y * 2, 2, 2);
				}
			}
		}
		else if (mode == GREY)
		{
			grey = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
			paintGrey(img, grey);
			int rgb = 0;
			for (int x = 0; x < grey.getWidth(); x++)
			{
				for (int y = 0; y < grey.getHeight(); y++)
				{
					g.setColor(new Color(grey.getRGB(x, y)));
					g.fillRect(10 + x * 2, 10 + y * 2, 2, 2);
				}
			}
		}
		else if (mode == PATTERN)
		{
			int   value;
			Color colors[] = new Color[4];
			//value for pattern matching, color[] represents pattern [up left,
			//up right, down left, down right];
			for (int x = 0; x < img.getWidth(); x++)
			{
				for (int y = 0; y < img.getHeight(); y++)
				{
					value  = (new Color(img.getRGB(x, y))).getRed();
					value /= 52;
					switch (value)
					{
					case 4:
						colors[0] = Color.WHITE;
						colors[1] = Color.WHITE;
						colors[2] = Color.WHITE;
						colors[3] = Color.WHITE;
						break;

					case 3:
						colors[0] = Color.BLACK;
						colors[1] = Color.WHITE;
						colors[2] = Color.WHITE;
						colors[3] = Color.WHITE;
						break;

					case 2:
						colors[0] = Color.BLACK;
						colors[1] = Color.WHITE;
						colors[2] = Color.WHITE;
						colors[3] = Color.BLACK;
						break;

					case 1:
						colors[0] = Color.WHITE;
						colors[1] = Color.BLACK;
						colors[2] = Color.BLACK;
						colors[3] = Color.BLACK;
						break;

					case 0:
						colors[0] = Color.BLACK;
						colors[1] = Color.BLACK;
						colors[2] = Color.BLACK;
						colors[3] = Color.BLACK;
						break;
					}


					g.setColor(colors[0]);
					                                                                                                                                                                                                                                                                      //up left
					g.setColor(colors[1]);
					g.fillRect(10 + 2 * x + 1, 10 + 2 * y, 1, 1);                                                                                                                                                                                                                                                                  //up right
					g.setColor(colors[2]);
					g.fillRect(10 + 2 * x, 10 + 2 * y + 1, 1, 1);                                                                                                                                                                                                                                                                  //down left
					g.setColor(colors[3]);
					g.fillRect(10 + 2 * x + 1, 10 + 2 * y + 1, 1, 1);                                                                                                                                                                                                                                                              //down right
				}
			}
		}
		else if (mode == THREAD) {

			for (int x=0; x < cells.length ;x++ ) {
				for (int y=0; y < cells[x].length ;y++ ) {
					if(cells[x][y][0]) g.setColor(Color.BLACK);
					else {g.setColor(Color.WHITE);}
					g.fillRect(10 + x, 10 + y, 1, 1);
				}
			}

		}
	}

	protected void paintGrey(BufferedImage orig, BufferedImage grey)
	{
		int   r, g, b, mid;
		Color colores, org_color;

		for (int x = 0; x < grey.getWidth(); x++)                                                                                                                                     //iterate over both of the dimensions of the image
		{
			for (int y = 0; y < grey.getHeight(); y++)
			{
				org_color = new Color(orig.getRGB(x, y));                                                                                                                                                                                                                                                                 //get the colorobj to get the colors
				r         = org_color.getRed();                                                                                                                                                                                                                                                                           // Calculate the mids
				g         = org_color.getGreen();
				b         = org_color.getBlue();
				mid       = (r + g + b) / 3;
				colores   = new Color(mid, mid, mid);                                                                                                                                                                                                                                                                        // this way it's easier to encode the rgb integer
				grey.setRGB(x, y, colores.getRGB());                                                                                                                                                                                                                                                                         //set new Color to pixel
			}
		}
	}

	public void setImg(BufferedImage bi)
	{
		this.img = bi;
	}
	public void setCells(boolean[][][] input){
		cells = input;
	}

	public void setMode(int mode)
	{
		this.mode = mode;
	}
}

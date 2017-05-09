package app.draw;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.*;
import java.security.*;
import app.draw.*;


class Automatron extends Thread {
	private int           runs;
	private boolean[][][] cells;

	public Automatron(boolean[][][] cells) {
	 	this.cells = cells;
		start();
	}

	public void setRuns(int runs)
	{
		this.runs = runs;
	}

	public void setCells(boolean[][][] img)
	{
		cells = img;
	}

	protected int getAlive(int x, int y)
	{
		if (cells[x][y][0]) { return 1; }
		return 0;
	}


	/** Overwrites run() inherited from Thread */
	synchronized public void run()
	{
		int w = cells.length;
		int h = cells[0].length;
		System.out.println("Thread Started");
		while (true)
		{
			while (runs > 0)
			{
				for (int x = 0; x < w; x++)
					for (int y = 0; y < h; y++) {
						//get neighbor's states
						boolean b[] = new boolean[8];
						if (x == 0) {
							b[3]=cells[w-1][y%h][0];
							b[5]=cells[w-1][(y+1)%h][0];
						} else {
							b[3]=cells[(x-1)%w][y%h][0];
							b[5]=cells[(x-1)%w][(y+1)%h][0];
						}
						if (y == 0) {
							b[1]=cells[x][h-1][0];
							b[2]=cells[(x+1)%w][h-1][0];
						} else {
							b[1]=cells[x][(y-1)%h][0];
							b[2]=cells[(x+1)%w][(y-1)%h][0];
						}
						if (x == 0 && y == 0)
							b[0]=cells[w-1][h-1][0];
						else if (x == 0)
							b[0]=cells[w-1][(y-1)%h][0];
						else if (y == 0)
							b[0]=cells[(x-1)%w][h-1][0];
						else
							b[0]=cells[(x-1)%w][(y-1)%h][0];

						b[4]=cells[(x+1)%w][y%h][0];
						b[6]=cells[x][(y+1)%h][0];
						b[7]=cells[(x+1)%w][(y+1)%h][0];

						//Set next state
						int alive = 0;
						for (int i = 0; i < b.length; i++)
							if (b[i])
								alive++;
						//odd number of alive neighbors -> alive, even -> dead
						//write the states into the sec dim
						System.out.println("Alive % 2: " + alive % 2);
						if (alive % 2 == 0) { cells[x][y][1] = false; }
						else cells[x][y][1] = true;

				}

				// update the states
				for (int x = 0; x < cells.length; x++)
				{
					for (int y = 0; y < cells[x].length; y++)
					{
						cells[x][y][0] = cells[x][y][1];
					}
				}
				AppDrawAutomatron.update();
				runs--;
			}
			System.out.println("Thread Run end, waiting");
			try {
				this.wait();
			}
			catch (InterruptedException e) {
				System.out.println("EXCEPTION: " + e + "\nWorker thread was interrupted"
				                   + " while trying to wait()");
			}
		}
	}

}

/**
 * Main App Class
 * Shall print a window with the buttons and pictures in it
 */
public class AppDrawAutomatron
{
	private JFrame        frame;
	private JPanel        panel, butans, piss, brexit;
	public JButton        start, exit;
	private JLabel        label;
	private JTextField    input;
	public static AppDrawPanel   draw;
	public static boolean starter;
	private static String startbeschriftung;
	public Automatron     worker;

	/**
	 * Default constructor for this application
	 * creates a new window with butans and a printed picture. haz 3 different modes
	 * for pictures to be printed
	 *
	 * @param width the width of the window frame
	 * @param height the height of the window frame
	 * @param img the image to be printed...
	 *
	 */
	public AppDrawAutomatron(int width, int height, boolean[][][] cells, Automatron worker)
	{
		this.worker = worker;
		prepareGUI(width, height);
		draw.setCells(cells);
		draw.repaint();
	}

	private void prepareGUI(int width, int height)
	{
		frame = new AppFrame("Automatron");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(width + 20, height + 20);
		panel = new JPanel();
		frame.add(panel);                                                                                                                                                  //// ground panel

		/**
		 * Panels
		 */
		butans = new JPanel();
		piss   = new JPanel();
		brexit = new JPanel();                                                                                                                                                  //meant for layouting
		panel.setLayout(new BorderLayout());
		panel.add(butans, BorderLayout.NORTH);
		panel.add(piss, BorderLayout.CENTER);
		panel.add(brexit, BorderLayout.SOUTH);
		brexit.setLayout(new FlowLayout(FlowLayout.RIGHT));
		draw = new AppDrawPanel();
		draw.setMode(AppDrawPanel.THREAD);
		piss.add(draw);


		/**
		 * Buttons
		 */
		startbeschriftung = "Start";
		label             = new JLabel("Rounds");
		input             = new JTextField(6);
		start             = new JButton(startbeschriftung);
		exit = new JButton("BREXIT!");

		butans.add(label);
		butans.add(input);
		butans.add(start);

		brexit.add(exit);

		start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int runs;
			  try {
					runs = Integer.parseInt(input.getText());
				} catch (NumberFormatException ex) {
					System.out.println("Exception: "+ex);
					System.out.println("No valid input in textfield (Integer)!");
					//JOptionPane.showMessageDialog(frame, "No valid input in textfield (Integer)!", JOptionPane.ERROR_MESSAGE);
					return;
				}

				//start sequence
				synchronized(worker) {
					worker.setRuns(runs);
					worker.notify();
				}
			  System.out.println("runs: " + runs);
			  worker.setRuns(runs);
			  worker.start();
			}
		});
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) { System.exit(0); }
		});

		frame.pack();
	}

	public static void update()
	{
		((AppDrawPanel)draw).repaint();
	}

	private static BufferedImage getBImg(String path)                                                                                                                                                             //importing jpg into a BufferedImagee
	{
		File pic = new File(path);

		if (!pic.canRead()) { throw new AccessControlException("Cannot read image file. Take a look at the permissions."); }
		BufferedImage img = null;
		try{
			img = ImageIO.read(pic);
		}
		catch (IOException e) {
			System.err.println("Error during reading of file. It may be corrupted. " + e);
		}
		System.out.println(img.toString());
		return img;
	}

	private static boolean[][][] getCells(BufferedImage img)
	{
		int     value;
		boolean cells[][][] = new boolean[4 * img.getWidth()][4 * img.getHeight()][2];                         //
		boolean colors[]    = new boolean[4];

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
					colors[0] = false;
					colors[1] = false;
					colors[2] = false;
					colors[3] = false;
					break;

				case 3:
					colors[0] = true;
					colors[1] = false;
					colors[2] = false;
					colors[3] = false;
					break;

				case 2:
					colors[0] = true;
					colors[1] = false;
					colors[2] = false;
					colors[3] = true;
					break;

				case 1:
					colors[0] = false;
					colors[1] = true;
					colors[2] = true;
					colors[3] = true;
					break;

				case 0:
					colors[0] = true;
					colors[1] = true;
					colors[2] = true;
					colors[3] = true;
					break;
				}



				cells[(cells.length / 2) - img.getWidth() + (2 *x)] [cells[x].length / 2 - img.getHeight() + 2 * y][0]        = colors[0];                                                                                                                                                                                                                                                                                                                                                                             //up left
				cells[(cells.length / 2) - img.getWidth() + (2 *x )+ 1][cells[x].length / 2 - img.getHeight() + 2 * y][0]     = colors[1];                                                                                                                                                                                                                                                                                                                                                                             //up right
				cells[(cells.length / 2) - img.getWidth() +( 2 *x)][cells[x].length / 2 - img.getHeight() + 2 * y + 1][0]     = colors[2];                                                                                                                                                                                                                                                                                                                                                                             //down left
				cells[(cells.length / 2) - img.getWidth() + (2 *x) + 1][cells[x].length / 2 - img.getHeight() + 2 * y + 1][0] = colors[3];                                                                                                                                                                                                                                                                                                                                                                             //down right
			}
		}
		return cells;
	}

	public static void main(String[] args)
	{
		BufferedImage img = getBImg(args[0]);
		boolean[][][] cells = getCells(img);
		Automatron worker = new Automatron(cells);
		AppDrawAutomatron everythang = new AppDrawAutomatron(img.getWidth(), img.getHeight(), cells, worker);
		((AppDrawPanel)everythang.draw).setCells(cells);
		((AppDrawPanel)everythang.draw).repaint();
		everythang.frame.setVisible(true);
	}
}

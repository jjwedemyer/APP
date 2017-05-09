package app.draw;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.security.*;

/**
 * Main App Class
 * Shall print a window with the buttons and pictures in it
 */
public class AppDrawEvent
{
	private JFrame      frame;
	private JPanel      panel, butans, piss, brexit;
	private JButton     but_org, but_grey, but_pat, exit;
	public AppDrawPanel draw;

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
	public AppDrawEvent(int width, int height, BufferedImage img)
	{
		prepareGUI(width, height);
		draw.setImg(img);
		draw.repaint();
	}

	private void prepareGUI(int width, int height)
	{
		frame = new AppFrame("Allgemeines Programmierpraktikum");
		frame.setSize(width + 20, height + 20);
		panel = new JPanel();
		frame.add(panel);                                                             //// ground panel

		/**
		 * Panels
		 */
		butans = new JPanel();
		piss   = new JPanel();
		brexit = new JPanel();                                                                                             //meant for layouting
		panel.setLayout(new BorderLayout());
		panel.add(butans, BorderLayout.NORTH);
		panel.add(piss, BorderLayout.CENTER);
		panel.add(brexit, BorderLayout.SOUTH);
		brexit.setLayout(new FlowLayout(FlowLayout.RIGHT));
		draw = new AppDrawPanel();
		piss.add(draw);


		/**
		 * Buttons
		 */

		JButton but_org = new JButton("ORIG"), but_grey = new JButton("Grayscale"), but_pat = new JButton("Pattern(Matched)"), exit = new JButton("BREXIT!");

		butans.add(but_org);
		butans.add(but_grey);
		butans.add(but_pat);
		brexit.add(exit);

		but_org.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { draw.setMode(AppDrawPanel.ORG); draw.repaint(); }
														 });
		but_grey.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { draw.setMode(AppDrawPanel.GREY); draw.repaint(); }
															});
		but_pat.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { draw.setMode(AppDrawPanel.PATTERN); draw.repaint(); }
														 });
		exit.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { System.exit(0); }
													});

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args)
	{
		BufferedImage img        = getBImg(args[0]);
		AppDrawEvent  everythang = new AppDrawEvent(img.getWidth(), img.getHeight(), img);
    ((AppDrawPanel)everythang.draw).repaint();
	}

	private static BufferedImage getBImg(String path)                                                                                     //importing jpg into a BufferedImagee
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
}

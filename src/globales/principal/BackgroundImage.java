package globales.principal;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.Border;

public class BackgroundImage implements Border{
	public BufferedImage back;

	public BackgroundImage(){
		try
		{
			URL imagePath = new URL(getClass().getResource("/png/logo.png").toString());
			this.back = ImageIO.read(imagePath);
		}
		catch (Exception localException) {
		}
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		g.drawImage(this.back, x + (width - this.back.getWidth()) - 50, y + (height - this.back.getHeight()) - 50, null);
	}

	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}

	public boolean isBorderOpaque() {
		return false;
	}
}
import java.awt.BorderLayout;

import javax.swing.JFrame;

import processing.core.PApplet;
public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6616512946694559013L;

	public MainFrame(PApplet embed) {
        super("Magic Cube");
        setLayout(new BorderLayout());
        add(embed, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // important to call this whenever embedding a PApplet.
        // It ensures that the animation thread is started and
        // that other internal variables are properly set.
        embed.init();
	}
}

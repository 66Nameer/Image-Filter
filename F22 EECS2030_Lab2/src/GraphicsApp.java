import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * @author Andriy Pavlovych
 * The class is meant to illustrate a couple of image-processing algorithms:
 * Gaussian blurring and edge detection
 * Java Swing is used to implement the GUI of the application
 * Limitations: image sizes are limited by computer screen resolution (no scaling is implemented) 
 */
@SuppressWarnings("serial")
public class GraphicsApp extends JFrame implements ActionListener{
	private BufferedImage image;
	private JButton mergeButton, edgesButton;
	private JLabel sourceImageLabel, resultImageLabel;
	private JTextField secondFileNameField;
	private JPanel sourcePanel, middlePanel, resultPanel, mergeTwoFilesPanel, edgeDetPanel;
	double imageScale;

	/**
	 * @param fileName name of the image file to process
	 * loads the image with the filename provided
	 */
	public GraphicsApp(String fileName) {
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) { }
		initUI();
	}

	private void initUI() {
		final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		imageScale = 0.35 * SCREEN_WIDTH / image.getWidth();
		if (0.75 * SCREEN_HEIGHT / image.getHeight() < imageScale) 
			imageScale = 0.33 * SCREEN_HEIGHT / image.getHeight();
		
		sourceImageLabel = new JLabel(new ImageIcon(image.getScaledInstance((int)(image.getWidth() * imageScale), 
				(int)(image.getHeight() * imageScale), Image.SCALE_SMOOTH)));
		resultImageLabel = new JLabel(new ImageIcon(image.getScaledInstance((int)(image.getWidth() * imageScale), 
				(int)(image.getHeight() * imageScale), Image.SCALE_SMOOTH)));
		

		secondFileNameField = new JTextField(10);
		secondFileNameField.setText("Trees.png");
		mergeButton = new JButton("Merge with");
		mergeButton.setPreferredSize(new Dimension(84,24));
		mergeButton.addActionListener(this);
		edgesButton = new JButton("Edges");
		edgesButton.setPreferredSize(new Dimension(84,24));
		edgesButton.addActionListener(this);

		getContentPane().setLayout(new BorderLayout());
		sourcePanel = new JPanel();
		middlePanel = new JPanel();
		resultPanel = new JPanel();       
		sourcePanel.setLayout(new BoxLayout(sourcePanel, BoxLayout.Y_AXIS));
		sourcePanel.add (new JLabel("Source"));
		sourcePanel.add (sourceImageLabel);
		add(sourcePanel,BorderLayout.WEST);

		middlePanel.setLayout(new BorderLayout(10,10));

		mergeTwoFilesPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		mergeTwoFilesPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		c.fill = GridBagConstraints.HORIZONTAL;
		mergeTwoFilesPanel.add(mergeButton, c);
		c.gridy = 1;
		c.insets = new Insets(5, 0, 0, 0);  //top padding
		mergeTwoFilesPanel.add(secondFileNameField, c);

		edgeDetPanel = new JPanel();
		edgeDetPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,5,5));
		edgeDetPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		edgeDetPanel.add(edgesButton);
		middlePanel.add(new JLabel(" "), BorderLayout.NORTH);
		middlePanel.add(mergeTwoFilesPanel, BorderLayout.CENTER);
		middlePanel.add(edgeDetPanel, BorderLayout.SOUTH);
		add(middlePanel,BorderLayout.CENTER);

		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
		resultPanel.add (new JLabel("Result"));
		resultPanel.add (resultImageLabel);
		add(resultPanel,BorderLayout.EAST);

		pack();
		setTitle("ImageFilter");
		setLocationRelativeTo(null); //place in the centre of the screen
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		GraphicsApp ex = new GraphicsApp("flower.png");

		ex.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mergeButton){
			int []rgbData1 = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

			BufferedImage image2 = null;
			try {
				image2 = ImageIO.read(new File(secondFileNameField.getText()));
			} catch (IOException e1) {}
			int []rgbData2 = image2.getRGB(0, 0, image2.getWidth(), image2.getHeight(), null, 0, image2.getWidth());
			
			ImageFilter.mergeImages(rgbData1, rgbData2, image.getWidth());
			image.setRGB(0, 0, image.getWidth(), image.getHeight(), rgbData1, 0, image.getWidth());
			resultImageLabel.setIcon(new ImageIcon(image.getScaledInstance((int)(image.getWidth() * imageScale), 
					(int)(image.getHeight() * imageScale), Image.SCALE_SMOOTH)));

		}
		else 
			if (e.getSource() == edgesButton){
				int []rgbData = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
				ImageFilter.edgeDetection(rgbData, image.getWidth());
				image.setRGB(0, 0, image.getWidth(), image.getHeight(), rgbData, 0, image.getWidth());
				resultImageLabel.setIcon(new ImageIcon(image.getScaledInstance((int)(image.getWidth() * imageScale), 
						(int)(image.getHeight() * imageScale), Image.SCALE_SMOOTH)));
			}
	}


}

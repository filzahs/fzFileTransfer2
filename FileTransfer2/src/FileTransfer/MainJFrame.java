package FileTransfer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainJFrame extends JFrame {
	private JPanel pan, labelsPanel;
	private JScrollPane scrollPane;
	private JButton connectButton, closeConnectButton;
	private JLabel isConnected, hasRobot, fileTransfer;
	private JTextArea textArea;
	SwingWorker<String, String> worker;

	public MainJFrame() {
		super("File Transfer");

		System.out.println("in main frame");
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setSize(300, 300);
		// setMinimumSize(new DimensionUIResource(500, 400));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// create main container to hold all the components
		pan = new JPanel();
		pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(pan, BorderLayout.CENTER);


		// create components
		connectButton = new JButton("Connect To Server");
		
		//create labels
		isConnected = new JLabel("Not Connected");
		hasRobot = new JLabel("No Robot Connected");
		fileTransfer = new JLabel("File Transfer");
		//panel to hold labels
		labelsPanel = new JPanel();
		labelsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
		labelsPanel.add(isConnected);
		labelsPanel.add(hasRobot);
		labelsPanel.add(fileTransfer);
		
		textArea = new JTextArea("print some message here");
		textArea.setColumns(20);
        textArea.setLineWrap(true);
        textArea.setRows(10);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
		//panel to hold textarea
		scrollPane = new JScrollPane(textArea); 
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// position components in main container
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		pan.add(connectButton);
		pan.add(labelsPanel);
		pan.add(scrollPane);
		
		// action listeners
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worker.execute();
			}
		});

		worker = new SwingWorker<String, String>() {

			@Override
			protected String doInBackground() throws Exception {
				try {
					Main.startClient();
//					for(int i = 0; i <10; i++) {
//						i++;
//					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "\nConnected";

			}

			@Override
			protected void done() {
				super.done();
				try {
					textArea.append(get()); // Set the textArea the text given from the long running task
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

	}

}

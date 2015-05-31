import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class gWindow {
	
	public Instance instanz;
		
	private JFrame frame;

	private JButton btnPoints;
	private JTextField txtPoints;
	public JCheckBox chckbxAntia;
	private JButton btnReset;
	public JComboBox<Integer> comboBox_linie;
	public JComboBox<Integer> comboBox_punkt;
	public JComboBox<String> comboBox;
	private JLabel lblLinie;
	private JButton btnNext;
	private JTextPane txtDebug;
	private JCheckBox chckbxDebug;
	private JPanel panel_1;
	private JLabel lblPunkt;
	public JCheckBox chckbxNummern;
	private JCheckBox chckbxGeschlossen;
	private JButton btnAuflsen;
	private JPanel panelLogo;
	private JLabel lblLogo;
	private JLabel lblVersion;
	private JLabel lblFh;
	private JLabel lblFhKln;
	private JCheckBox chckbxAutoupdate;
	private JList<String> list;
	private JScrollPane sp_knots;
	private JComboBox<String> comboBoxMode;
	private SquareCanvas canvas;
	private DefaultListModel<String> blank;
	private JPanel panel_3;
	private JLabel lblFarbe;
	public JLabel lblMouseX;
	public JLabel lblMouseY;
	private JButton btnEdit;
	private JButton btnRemove;
	
	private int frameHeightPx;
	private int frameHeightDebugPx;
	private int canvasWidth;
	private int canvasHeight;
	private int canvasPxWidth;
	private int canvasPxHeight;
	private int canvasSpacing;
	private  int canvasBorder;
	

	/**
	 * Launch the application. // Generiert durch WindowManager
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gWindow window = new gWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public gWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. //Generiert durch WindowManager
	 */
	private void initialize() {
		try
		{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exp) { /* Fallback auf Metal LAF */ }
		
		//Forms-Komponenten von Oben nach Unten
		
		//   Frame
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1215, 804);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frameHeightPx = frame.getHeight();
		frameHeightDebugPx = frame.getHeight() + 140;

		//   Top Panel
		panel_1 = new TopBar("subbar.png");
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setVgap(9);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_1.setBounds(0, 50, 1209, 44);
		frame.getContentPane().add(panel_1);
		
		//   Linie Label
		lblLinie = new JLabel("Linienst�rke: ");
		panel_1.add(lblLinie);
		
		//   Linie ComboBox mit Items 1 bis 4
		comboBox_linie = new JComboBox<Integer>();
		comboBox_linie.setPreferredSize(new Dimension(40, 25));
		for (int i = 1; i <= 4; i++) comboBox_linie.addItem(i);
		comboBox_linie.addItemListener(new OptionListener());
		panel_1.add(comboBox_linie);
		
		//   Punkt Label
		lblPunkt = new JLabel("Punktradius: ");
		panel_1.add(lblPunkt);
		
		//   Punkt ComboBox
		comboBox_punkt = new JComboBox<Integer>();
		comboBox_punkt.setPreferredSize(new Dimension(40, 25));
		for (int i = 0; i <= 10; i++) comboBox_punkt.addItem(i);
		comboBox_punkt.setSelectedItem(comboBox_punkt.getItemAt(4));
		comboBox_punkt.addItemListener(new OptionListener());
		panel_1.add(comboBox_punkt);
		
		//   Color ComboBox mit Items aus Colors enum
		comboBox = new JComboBox<String>();
		comboBox.setPreferredSize(new Dimension(80, 25));
		for (Colors.ColorNames c : Colors.ColorNames.values()) comboBox.addItem(c.toString());
		comboBox.setSelectedItem(comboBox.getItemAt(12));
		comboBox.addItemListener(new OptionListener());
		
		lblFarbe = new JLabel("Farbe:");
		panel_1.add(lblFarbe);
		panel_1.add(comboBox);
		
		chckbxAutoupdate = new JCheckBox("AutoUpdate");
		chckbxAutoupdate.setSelected(true);
		chckbxAutoupdate.setOpaque(false);
		panel_1.add(chckbxAutoupdate);
		
		
		//   AntiAliasing Checkbox
		chckbxAntia = new JCheckBox("AntiAliasing");
		chckbxAntia.setOpaque(false);
		panel_1.add(chckbxAntia);
		chckbxAntia.setSelected(true);
		
		chckbxNummern = new JCheckBox("Nummern");
		chckbxNummern.setOpaque(false);
		chckbxNummern.setSelected(true);
		chckbxNummern.addChangeListener(new OptionListener());
		panel_1.add(chckbxNummern);
		chckbxAntia.addChangeListener(new OptionListener());
		
 
		
	
		//   Debug Textpane
		txtDebug = new JTextPane();
		txtDebug.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtDebug.setBounds(168, 500, 258, 72);

		//   Debug Scrollbar
		JScrollPane sp = new JScrollPane(txtDebug,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    sp.setBounds(0, 742, 1209, 141);
	    frame.getContentPane().add(sp);
		
		list = new JList<String>();
		list.addListSelectionListener(new SelectionListener());
			
		
		list.setFont(new Font("Tahoma", Font.PLAIN, 15));
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setBackground(Color.WHITE);
		list.setBounds(243, 240, 59, 81);
		
		blank = new DefaultListModel<String>();
		blank.addElement("�ber 'Neue Instanz'");
		blank.addElement("Knoten hinzuf�gen.");
		list.setModel(blank);
		
		sp_knots = new JScrollPane(list);
		sp_knots.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp_knots.setBounds(0, 96, 190, 584);
		frame.getContentPane().add(sp_knots);
		
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panel.setBounds(191, 96, 1001, 600);
		frame.getContentPane().add(panel);
		
		canvasPxWidth = 1000;
		canvasPxHeight = 600;
		canvasSpacing = 6;
		canvasBorder = 3;
		canvasWidth = canvasPxWidth + 2*(canvasSpacing + canvasBorder);
		canvasHeight = canvasPxHeight + 2*(canvasSpacing + canvasBorder);
		
		canvas = new SquareCanvas(canvasPxWidth, canvasPxHeight, canvasSpacing, canvasBorder, this);
	
		canvas.setPreferredSize(new Dimension(canvasWidth,canvasHeight));
		panel.add(canvas);
		panel.setBounds(panel.getX(), panel.getY(), canvasWidth, canvasHeight);
		canvas.setBorder(BorderFactory.createLineBorder(Color.gray, canvasBorder));
		
		JPanel toolbar = new TopBar("topbar.png");
		FlowLayout flowLayout_5 = (FlowLayout) toolbar.getLayout();
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		toolbar.setBounds(0, 0, 1209, 50);
		frame.getContentPane().add(toolbar);
		
		panelLogo = new JPanel();
		toolbar.add(panelLogo);
		panelLogo.setOpaque(false);
		panelLogo.setPreferredSize(new Dimension(200, 40));
		FlowLayout flowLayout_2 = (FlowLayout) panelLogo.getLayout();
		flowLayout_2.setAlignOnBaseline(true);
		flowLayout_2.setVgap(2);
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		
		lblLogo = new JLabel("TSPSim  ");
		panelLogo.add(lblLogo);
		lblLogo.setForeground(new Color(128, 0, 0));
		lblLogo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		
		lblVersion = new JLabel("v0.5");
		lblVersion.setForeground(Color.GRAY);
		lblVersion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVersion.setVerticalAlignment(SwingConstants.BOTTOM);
		panelLogo.add(lblVersion);
		
		lblFh = new JLabel("MA2 Projekt SoSe '15");
		lblFh.setForeground(Color.DARK_GRAY);
		panelLogo.add(lblFh);
		lblFh.setVerticalAlignment(SwingConstants.BOTTOM);
		lblFh.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		lblFhKln = new JLabel("FH K�ln");
		lblFhKln.setForeground(Color.DARK_GRAY);
		lblFhKln.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelLogo.add(lblFhKln);
		
		//   Punkte Button
		btnPoints = new JButton("Neue Instanz");
		btnPoints.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPoints.setPreferredSize(new Dimension(140, 35));
		toolbar.add(btnPoints);
		
		//   Punkte Anzahl
		txtPoints = new JTextField("10");
		toolbar.add(txtPoints);
		txtPoints.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPoints.setPreferredSize(new Dimension(35, 30));
		
		panel_3 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_3.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		panel_3.setPreferredSize(new Dimension(780, 38));
		panel_3.setOpaque(false);
		toolbar.add(panel_3);
		
		JLabel lblVerfahren = new JLabel("Verfahren:");
		panel_3.add(lblVerfahren);
		
		comboBoxMode = new JComboBox<String>();
		panel_3.add(comboBoxMode);
		
		comboBoxMode.setPreferredSize(new Dimension(156, 27));
		comboBoxMode.addItem("BruteForce");
		comboBoxMode.addItem("NearestNeighbour");
		comboBoxMode.addItem("Best NearestNeighbour");
		comboBoxMode.addItem("MST-Transform");
		comboBoxMode.setSelectedIndex(1);
		
		//   N�chster NN Button
		btnNext = new JButton("N\u00E4chster");
		panel_3.add(btnNext);
		btnNext.setPreferredSize(new Dimension(85, 27));
		btnNext.setEnabled(false);
		
		//   Aufl�sen Button
		 btnAuflsen = new JButton("L\u00F6sen");
		 panel_3.add(btnAuflsen);
		 btnAuflsen.setPreferredSize(new Dimension(70, 27));
		 btnAuflsen.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		switch (comboBoxMode.getSelectedIndex()) {
		 		case 0:
		 			bruteForce(instanz);
		 			break;
		 		case 1:
		 			NN_Solve(instanz);
		 			break;
		 		case 2:
		 			bestNN(instanz);
		 		case 3:
		 			
		 		}
		 		
		 	}
		 });
		 btnAuflsen.setEnabled(false);
		 
		 //   Reset Button
		 btnReset = new JButton("Reset");
		 panel_3.add(btnReset);
		 btnReset.setPreferredSize(new Dimension(70, 27));
		 
		 //   geschlossen Checkbox
		  chckbxGeschlossen = new JCheckBox("geschlossen");
		  panel_3.add(chckbxGeschlossen);
		  chckbxGeschlossen.setOpaque(false);
		  chckbxGeschlossen.setSelected(true);
		  
		//   Debug Checkbox mit Changehandler, �ndert Frameh�he
		chckbxDebug = new JCheckBox("Stats");
		panel_3.add(chckbxDebug);
		chckbxDebug.setOpaque(false);
		
		JPanel panel_2 = new TopBar("subbar.png");
		
		FlowLayout flowLayout_4 = (FlowLayout) panel_2.getLayout();
		flowLayout_4.setVgap(3);
		flowLayout_4.setHgap(8);
		flowLayout_4.setAlignment(FlowLayout.RIGHT);
		panel_2.setBounds(0, 715, 1209, 25);
		frame.getContentPane().add(panel_2);
		
		lblMouseX = new JLabel("");
		lblMouseX.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblMouseX);
		
		lblMouseY = new JLabel("");
		lblMouseY.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblMouseY);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panel_4.getLayout();
		flowLayout_6.setAlignment(FlowLayout.LEFT);
		flowLayout_6.setVgap(0);
		flowLayout_6.setHgap(-1);
		panel_4.setBounds(0, 680, 191, 35);
		frame.getContentPane().add(panel_4);
		
		JButton btnAddknot = new JButton("");
		btnAddknot.setToolTipText("Knoten hinzuf\u00FCgen");
		btnAddknot.setIcon(new ImageIcon(gWindow.class.getResource("/Icons/add_24.png")));
		panel_4.add(btnAddknot);
		btnAddknot.setPreferredSize(new Dimension(64, 35));
		
		btnEdit = new JButton("");
		btnEdit.setEnabled(false);
		btnEdit.setToolTipText("Knoten bearbeiten");
		btnEdit.setIcon(new ImageIcon(gWindow.class.getResource("/Icons/edit_24.png")));
		btnEdit.setPreferredSize(new Dimension(65, 35));
		panel_4.add(btnEdit);
		
		btnRemove = new JButton("");
		btnRemove.setEnabled(false);
		btnRemove.setToolTipText("Knoten l\u00F6schen");
		btnRemove.setIcon(new ImageIcon(gWindow.class.getResource("/Icons/delete_24.png")));
		btnRemove.setPreferredSize(new Dimension(65, 35));
//		button_1.setIcon(arg0);
		panel_4.add(btnRemove);
		
		chckbxDebug.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxDebug.isSelected()) frame.setBounds(frame.getX(), frame.getY(), frame.getWidth(), frameHeightDebugPx);
				else frame.setBounds(frame.getX(), frame.getY(), frame.getWidth(), frameHeightPx);
			}
		});
		 btnReset.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		Reset();
		 	}
		 });
		btnNext.addActionListener(new ActionListener() {
		   	public void actionPerformed(ActionEvent e) {
		   		switch (comboBoxMode.getSelectedIndex()) {
		 		case 1:
		 			NN_Next(instanz);
		 			break;
		 		case 3: 
		 			MST(instanz);
		 			break;
		   		}
		   	}
		   });
		
		// Verfahren ComboBox Listeners
		comboBoxMode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				switch (comboBoxMode.getSelectedIndex()) {
				case 0: 
					btnNext.setEnabled(false);
					btnNext.setText("N�chster");
					break;
				case 1: 
					btnNext.setEnabled(true);
					btnNext.setText("N�chster");
					break;
				case 2:
					btnNext.setEnabled(false);
					btnNext.setText("N�chster");
					break;
				case 3:
					btnNext.setText("MST");
					btnNext.setEnabled(true);
					btnAuflsen.setEnabled(false);
				
				}
			}
		});
		
		
		btnPoints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instanz = createInstance();
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);
		
		JMenu mnInstanz = new JMenu("Instanz");
		menuBar.add(mnInstanz);

	}
	
	class OptionListener implements ItemListener, ChangeListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if (chckbxAutoupdate.isSelected()) canvas.redraw();
			else canvas.updateGraphics();
		}
		@Override
		public void stateChanged(ChangeEvent e) {

			if (chckbxAutoupdate.isSelected()) canvas.redraw();
			else canvas.updateGraphics();
		}
	}
	
	
	class SelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			
			if (list.getSelectedIndex() == -1) {
				btnEdit.setEnabled(false);
				btnRemove.setEnabled(false);
			} else {
				btnEdit.setEnabled(true);
				btnRemove.setEnabled(true);
			}
		}
		
	}
	
	/**
	 * Setzt sowohl die Instanz als auch das Canvas zur�ck:
	 * Knotenliste wird �berschrieben, Canvas wird mit wei�em Rechteck �bermalt (flushCanvas()),
	 * Steuerelemente werden zur�ckgesetzt: Knotenliste wieder in voller H�he, L�sen-Buttons deaktiviert,
	 * Weiderholen-Button und Ergebnis-Label als unsichtbar gesetzt
	 */
	private void Reset() {
		
		list.setModel(blank);
		
		canvas.flushGraphics(true);
		btnNext.setEnabled(false);
		btnAuflsen.setEnabled(false);
		
	}

	/**
	 * Erzeugt ein neues Objekt der Klasse Instance und f�gt diesem dei festgelegte Anzahl der Knoten hinzu.<br>
	 * Jeder Knoten wird �ber das DefaultListModel in die JList aufgenommen und mit {@link  drawPoint} auf das 
	 * BufferedImage, das zu g geh�rt gezeichnet. Das BufferedImage wird anschlie�end dem CanvasLabel zugeordnet.
	 * 
	 * @return den Verweis auf die erstellte Instanz
	 */
	private Instance createInstance() {
		
		Reset();
		Instance inst = new Instance();
		int count = Integer.parseInt(txtPoints.getText());
		DefaultListModel<String> knotenlist = new DefaultListModel<String>();

		for (int i = 0; i<count; i++) {
			Knot n = new Knot(i);
			inst.addKnot(n);
			knotenlist.addElement(n.toString()); //TODO Setter von Instance-Klasse aus w�re sch�ner, dann erstellen der Instanz �ber new Instance(count)
			canvas.drawKnot(n);
		}
		
		canvas.repaint();
		list.setModel(knotenlist);
		btnNext.setEnabled(true);
		btnAuflsen.setEnabled(true);
		
		return inst;
	}
	
	private void bruteForce(Instance inst) {
		
		inst.bruteForceSolve(canvas);
		canvas.repaint();
		logResult(inst);
	}

	/**
	 * Zeigt den n�chstgelenen Knoten zum ausgew�hlten in der JList an.
	 * �ber nearestNeighbour wird dieser auf das Graphics-Objekt g gezeichnet, 
	 * welches anschlie�end durch drawToCanvas auf die Zeichenfl�che angewandt wird
	 * <br>Bedingunen:<br>
	 * Ein Knoten ist ausgew�hlt und die Instanz ist bereit, also es wurde noch kein Neighbour der Liste entfernt.
	 * @see isReady
	 * @see nearestNeighbour
	 * 
	 * @param inst der Verweis auf die zu l�sende Instanz
	 */
	private void NN_Next(Instance inst) {     //TODO NN Next und Solve zusammenf�hren, �ber Parameter step steuern
		
		if (list.getSelectedIndex() == -1) JOptionPane.showMessageDialog(null, "Zuerst einen Startknoten in der Liste ausw�hlen");
		else {
			boolean closed = chckbxGeschlossen.isSelected();
			if (inst.isReady()) inst.setStart(list.getSelectedIndex());

			Knot nearest = inst.nearestNeighbour(inst.getKnot(list.getSelectedIndex()), closed, true, true, canvas, txtDebug);
			if (nearest != null) list.setSelectedIndex((nearest.getId()));
			
			canvas.repaint();
		}
		if (inst.isFinished()) {
			btnNext.setEnabled(false);
			logResult(inst);
		}
	}
	
	private void NN_Solve(Instance inst) {
		
		if (list.getSelectedIndex() == -1) JOptionPane.showMessageDialog(null, "Zuerst einen Startknoten in der Liste ausw�hlen");
		else {
			boolean closed = chckbxGeschlossen.isSelected();
			if (inst.isReady()) inst.setStart(list.getSelectedIndex());
			
			Knot knot = inst.getKnot(list.getSelectedIndex());
			while (!inst.isFinished()) knot = inst.nearestNeighbour(knot, closed, false, true, canvas, null);

			
			canvas.repaint();
			logResult(inst);
		}
	
	}
	
	private void bestNN(Instance inst) {
		
		Knot thisknot;
		double thisLenghth = 0;
		Knot bestStart = null;
		boolean closed = chckbxGeschlossen.isSelected();
		
		for (int i = 0; i<inst.getCount();i++) {
			
			if (inst.isReady()) inst.setStart(i);
			thisknot = inst.getKnot(i);
			
			while (!inst.isFinished()) thisknot = inst.nearestNeighbour(thisknot, closed, false, false, null, null);
			if ((i==0) || (inst.getWaylength() < thisLenghth)) {
				thisLenghth = inst.getWaylength();
				bestStart = thisknot;
			}
			inst.resetInstance();
		}
		
		if (inst.isReady()) inst.setStart(bestStart.getId());
		list.setSelectedIndex(bestStart.getId());
		while (!inst.isFinished()) bestStart = inst.nearestNeighbour(bestStart, closed, false, true, canvas, null);
		
		
		canvas.repaint();
		logResult(inst);
	}
	
	private void MST(Instance inst) {
		
		inst.makeMST(canvas);
		canvas.repaint();
		logResult(inst);
	}
	
	
	public void logResult(Instance inst) {
		int mode = comboBoxMode.getSelectedIndex();
		String result = inst.getResult(mode);
		
		txtDebug.setText(txtDebug.getText() + result + "\n\n");
	}
}

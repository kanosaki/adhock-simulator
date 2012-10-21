package adsim.form.view;

import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class SimController extends JPanel {

	/**
	 * Create the panel.
	 */
	public SimController() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnStart = new JButton("Start");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(0, 0, 5, 0);
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 0;
		add(btnStart, gbc_btnStart);
		
		JButton btnPause = new JButton("Pause");
		GridBagConstraints gbc_btnPause = new GridBagConstraints();
		gbc_btnPause.gridx = 0;
		gbc_btnPause.gridy = 1;
		add(btnPause, gbc_btnPause);

	}

}

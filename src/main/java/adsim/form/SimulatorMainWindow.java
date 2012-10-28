package adsim.form;

import lombok.*;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import adsim.core.ISimulator;
import adsim.form.view.SimView;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

@Slf4j
public class SimulatorMainWindow extends JFrame {

	private JPanel _contentPane;

	/**
	 * Create the frame.
	 */
	public SimulatorMainWindow(ISimulator model) {
		log.info("Initializing Simulator window.");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mnFile.add(mntmOpen);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		_contentPane.setLayout(new BorderLayout(0, 0));
		
		SimView simView = new SimView(model);
		_contentPane.add(simView, BorderLayout.CENTER);
		log.debug("SimulatorMainWindow initialized.");
	}

}

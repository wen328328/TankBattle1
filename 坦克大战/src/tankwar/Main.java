 package tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import lightButton.LightButton;
import mapediter.MapEdit;


public class Main implements ActionListener{
		private JFrame f;
		private PanelX p;
		private LightButton butStart;
		private LightButton butEdit;
		private LightButton butHelp;
		private int width;
		private int height;
		private LightButton butExit;
		private JDialog set;
		private Help help=new Help(); 
		private String map;
		private int max;
		protected int style;
		public Main() {
			setF(new JFrame("TankWar"));
			p=new PanelX();
			p.setLayout(null);
			butStart=new LightButton(330,210,140,50,"Start");
			butEdit=new LightButton(330,263,140,50,"Edit Map");
			butHelp=new LightButton(330,316,140,50,"Help");
			butExit=new LightButton(330,422,140,50,"Exit");
			butStart.addActionListener(this);
			butEdit.addActionListener(this);
			butHelp.addActionListener(this);
			butExit.addActionListener(this);
			p.add(butStart);
			p.add(butEdit);
			p.add(butHelp);
			p.add(butExit);
			getF().add(p);
			getF().setSize(0,0);
			width=800;height=830;
			getF().setDefaultCloseOperation(3);
			getF().setResizable(false);
			getF().setVisible(true);
			for (int i = 0; i < width; i+=20) {
					getF().setSize(i,i*(height/width));
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {}
				
			}
		}
	public static void main(String[] args) {
		new Main();
	}

	private class PanelX extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 5954299196924652990L;
		private ImageIcon backgrond;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			backgrond=new ImageIcon(Main.class.getResource("/pic/start2.jpg"));
			g.drawImage(backgrond.getImage(), 0, 0, 800, 830, 0, 0, backgrond.getIconWidth(), backgrond.getIconHeight(), null);
		}
	}
	public void over()
	{
		for (int i = width; i >=0; i-=20) {
			getF().setSize(i,i*(height/width));
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
				}
		getF().setVisible(false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==butExit)
		{
			over();
		}
		else if(e.getSource()==butEdit)
		{
			new MapEdit(this);
		}
		else if(e.getSource()==butStart)
		{
			dialog();
			try {
				new TankWar(map,max,f,style);
			} catch (Exception e1) {
			}
		}
		else if(e.getSource()==butHelp)
		{
			help.setVisible(true);
		}
		
		
	}
	private void dialog() {
		set = new JDialog(f,true);
		set.setVisible(false);
		set.setTitle("游戏设置");
		set.setBounds(200,100, 400, 600);
		ButtonGroup tankGroup=new ButtonGroup();
		final JRadioButton tank1=new JRadioButton("闯关模式");
		final JRadioButton tank2=new JRadioButton("无尽模式");
		tank1.setSelected(true);
		tankGroup.add(tank1);
		tankGroup.add(tank2);
		Font tankFont = new Font("黑体",3,22);
		tank1.setFont(tankFont);
		tank2.setFont(tankFont);
		tank1.setBounds(0, 0, 150, 120);
		tank2.setBounds(0, 80, 150, 120);
		JPanel dp = new JPanel(null);
		File dir = new File("map");
		String mapNames[]=dir.list();
		final JComboBox list = new JComboBox();
		for (int i = 0; i < mapNames.length; i++) {
			list.addItem(mapNames[i]);			
		}
		list.setBounds(100, 220, 250, 30);
		JLabel labMap = new JLabel("地图：");
		labMap.setFont(new Font("宋体", 1, 20));
		labMap.setBounds(20, 220, 80, 30);
		JLabel labMax=new JLabel("敌军数量：");
		labMax.setFont(new Font("宋体", 1, 16));
		labMax.setBounds(20, 280, 120, 30);
		final JSlider slider = new JSlider(10, 50);
		slider.setBounds(150, 280, 230, 50);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(2);
		final TextField showMax = new TextField(String.valueOf(slider.getValue()));
		showMax.setFont(new Font("", 0, 20));
		showMax.setBackground(Color.WHITE);
		showMax.setEditable(false);
		showMax.setBounds(100, 280, 30, 30);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				showMax.setText(String.valueOf(slider.getValue()));
			}
		});
		JButton butOk = new JButton("开始轰炸之旅");
		butOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				max=slider.getValue();
				map=(String)list.getSelectedItem();
				set.setVisible(false);
				if(tank1.isSelected())	style=1;	
				else if(tank2.isSelected()) style=2;
				over();
			}
		});
		butOk.setBounds(120, 360, 140, 30);
		dp.add(tank1);
		dp.add(tank2);
		dp.add(butOk);
		dp.add(showMax);
		dp.add(labMap);
		dp.add(list);
		dp.add(labMax);
		dp.add(slider);
		set.add(dp);
		set.setVisible(true);		
	}
	public void setF(JFrame f) {
		this.f = f;
	}
	public JFrame getF() {
		return f;
	}
}

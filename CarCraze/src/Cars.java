import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowStateListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class Cars extends JApplet implements ActionListener {

	private RaceCar[] cars = new RaceCar[4];
	private JButton start1, start2, start3, start4, stop1, stop2, stop3, stop4,
			speed1, speed2, speed3, speed4, slow1, slow2, slow3, slow4;

	public void init() {

		setSize(1000, 650);

		JPanel panel = new JPanel(new GridLayout(4, 1));
		for (int i = 0; i < cars.length; i++) {
			cars[i] = new RaceCar(0, 45, 1000, 13);
			cars[i].setBorder(new LineBorder(Color.BLACK, 2));
			panel.add(cars[i]);
		}
		// add car 1 to panel2
		JPanel panel2 = new JPanel(new GridLayout(4, 1));
		panel2.add(new JLabel("Car 1", JLabel.CENTER));
		panel2.add(start1 = new JButton("Start"));
		panel2.add(stop1 = new JButton("Stop"));
		panel2.add(speed1 = new JButton("Speed Up"));
		panel2.add(slow1 = new JButton("Slow Down"));

		// add car 2 to panel2
		panel2.add(new JLabel("Car 2", JLabel.CENTER));
		panel2.add(start2 = new JButton("Start"));
		panel2.add(stop2 = new JButton("Stop"));
		panel2.add(speed2 = new JButton("Speed Up"));
		panel2.add(slow2 = new JButton("Slow Down"));

		// add car 3 to panel2
		panel2.add(new JLabel("Car 3", JLabel.CENTER));
		panel2.add(start3 = new JButton("Start"));
		panel2.add(stop3 = new JButton("Stop"));
		panel2.add(speed3 = new JButton("Speed Up"));
		panel2.add(slow3 = new JButton("Slow Down"));

		// add car 4 to panel2
		panel2.add(new JLabel("Car 4", JLabel.CENTER));
		panel2.add(start4 = new JButton("Start"));
		panel2.add(stop4 = new JButton("Stop"));
		panel2.add(speed4 = new JButton("Speed Up"));
		panel2.add(slow4 = new JButton("Slow Down"));

		start1.addActionListener(this);
		start2.addActionListener(this);
		start3.addActionListener(this);
		start4.addActionListener(this);

		stop1.addActionListener(this);
		stop2.addActionListener(this);
		stop3.addActionListener(this);
		stop4.addActionListener(this);

		speed1.addActionListener(this);
		speed2.addActionListener(this);
		speed3.addActionListener(this);
		speed4.addActionListener(this);

		slow1.addActionListener(this);
		slow2.addActionListener(this);
		slow3.addActionListener(this);
		slow4.addActionListener(this);

		add(panel, BorderLayout.CENTER);
		add(panel2, BorderLayout.SOUTH);

	}

	private final class RaceCar extends JPanel {

		private int xPosition;
		private int yPosition;
		private double speed;
		private int MAX_WIDTH;
		private int delay = 200;
		private int delay2 = 80;
		private Wheels wheel;

		private Timer timer = new Timer(delay, new Animation());; // timer for
																	// moving
		public Timer timer2 = new Timer(delay2, new Animation2());// timer for
																	// turning
																	// the wheel

		private boolean reverse = false;
		private boolean moveRight = true;

		public RaceCar(int x, int y, int maxWidth, double speed) {
			this.xPosition = x;
			this.yPosition = y;
			this.MAX_WIDTH = maxWidth;
			this.speed = speed;
			wheel = new Wheels();

		}

		private final void move() {

			if (xPosition + 100 > MAX_WIDTH)
				moveRight = false;

			if (xPosition < 0)
				moveRight = true;

			if (moveRight)
				xPosition += speed;
			if (!moveRight)
				xPosition -= speed;

		}

		protected final void paintComponent(Graphics g) {
			super.paintComponent(g);

			// car hood
			g.setColor(Color.BLACK);
			g.drawLine(xPosition + 40, yPosition - 25, xPosition + 60,
					yPosition - 25);// top line
			// left slanted line
			g.drawLine(xPosition + 20, yPosition, xPosition + 40,
					yPosition - 25);
			// right slanted line
			g.drawLine(xPosition + 80, yPosition, xPosition + 60,
					yPosition - 25);
			// vertical line
			g.drawLine(xPosition + 60, yPosition - 25, xPosition + 60,
					yPosition);
			g.drawLine(xPosition + 40, yPosition - 25, xPosition + 40,
					yPosition);

			// car's body
			g.setColor(Color.RED);
			g.fillRect(xPosition, yPosition, 100, 45);

			// car wheels
			wheel.paintComponent(g);

		}

		/** stop the car */
		protected final void stop() {
			timer.stop();
			timer2.stop();
		}

		/** start the car */
		protected final void start() {
			timer.start();
			timer2.start();
		}

		/** speed up the car */
		protected final void speedUp() {
			if(delay==0 || delay2==0)
				return;
			if (delay >=10)
				delay -= 10;
			if (delay2 >= 15)
				delay2 -= 15;
			timer.setDelay(delay);
			timer2.setDelay(delay2);
		}

		/** slow down the car */
		protected final void slowDown() {
			if (delay >= 0)
				delay += 10;
			if (delay2 >= 0)
				delay += 15;
			timer.setDelay(delay);
			timer2.setDelay(delay2);
		}

		/** action listener for moving the car */
		private final class Animation implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				move();
				wheel.move();
				repaint();
				wheel.repaint();

			}
		}

		/** action listener for turning the wheel */
		private final class Animation2 implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

				wheel.turn();
				repaint();

			}

		}

		/** inner class for the wheels */
		private final class Wheels extends JPanel {

			private int turn = 0;
			private int degreeChange = -30;

			public void move() {

				if (moveRight)
					xPosition += speed;

				if (!moveRight)
					xPosition -= speed;

				if (xPosition + 100 > MAX_WIDTH) {
					reverse = true;
					reverse();
				}

				if (xPosition < 0) {
					reverse = true;
					reverse();
				}

			}

			public void turn() {

				turn += 1;
			}

			private void reverse() {
				if (reverse) {
					degreeChange = -1 * degreeChange;
					reverse = false;
				}
			}

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				// Draw wheels
				g.setColor(Color.BLACK);
				g.drawOval(xPosition + 15, yPosition + 45, 20, 20);// left wheel
				for (int i = 0; i <= 3; i++)
					g.fillArc(xPosition + 15, yPosition + 45, 20, 20, 90 * i
							+ degreeChange * turn, 30);
				g.drawOval(xPosition + 55, yPosition + 45, 20, 20);// right
																	// wheel
				for (int i = 0; i <= 3; i++)
					g.fillArc(xPosition + 55, yPosition + 45, 20, 20, 90 * i
							+ degreeChange * turn, 30);

			}

		}

	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		if (e.getSource() == start1)
			cars[0].start();
		if (e.getSource() == start2)
			cars[1].start();
		if (e.getSource() == start3)
			cars[2].start();
		if (e.getSource() == start4)
			cars[3].start();
		if (e.getSource() == stop1)
			cars[0].stop();
		if (e.getSource() == stop2)
			cars[1].stop();
		if (e.getSource() == stop3)
			cars[2].stop();
		if (e.getSource() == stop4)
			cars[3].stop();
		if (e.getSource() == speed1)
			cars[0].speedUp();
		if (e.getSource() == speed2)
			cars[1].speedUp();
		if (e.getSource() == speed3)
			cars[2].speedUp();
		if (e.getSource() == speed4)
			cars[3].speedUp();
		if (e.getSource() == slow1)
			cars[0].slowDown();
		if (e.getSource() == slow2)
			cars[1].slowDown();
		if (e.getSource() == slow3)
			cars[2].slowDown();
		if (e.getSource() == slow4)
			cars[3].slowDown();

	}

}

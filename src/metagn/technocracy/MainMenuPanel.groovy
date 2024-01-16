package metagn.technocracy

import groovy.transform.CompileStatic

import javax.swing.JPanel
import javax.swing.event.MouseInputAdapter
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.awt.font.FontRenderContext
import java.awt.geom.Rectangle2D

@CompileStatic
class MainMenuPanel extends JPanel {
	boolean hoveringPlay = false
	Rectangle2D playBounds

	MainMenuPanel() {
		def mi = new Ugoogogogog()
		addMouseListener(mi)
		addMouseMotionListener(mi)
	}

	class Ugoogogogog extends MouseInputAdapter {
		void mouseMoved(MouseEvent e) {
			hoveringPlay = playBounds?.contains(e.x, e.y)
			repaint()
		}

		void mouseClicked(MouseEvent e) {
			if (!hoveringPlay) return
			def game = new GamePanel(camera: new Camera(universe: Main.universe))
			def frame = e.component.parent
			frame.remove(MainMenuPanel.this)
			frame.add(game)
			frame.revalidate()
			frame.repaint()
			game.grabFocus()
		}
	}

	void paintComponent(Graphics g) {
		g = (Graphics2D) g
		g.color = new Color(0)
		g.fillRect(0, 0, (int) bounds.width, (int) bounds.height)
		g.color = new Color(0xFFFFFF)
		g.font = new Font('Arial', Font.PLAIN, (int) (bounds.height / 10))
		g.drawString('technocracy', 5, (int) (bounds.height / 30 + bounds.height / 20))
		g.color = new Color(hoveringPlay ? 0x00FF00 : 0x769176)
		g.font = new Font('Arial', Font.PLAIN, (int) (bounds.height / 15))
		g.drawString('play', 5, (int) (bounds.height / 7 + bounds.height / 40))
		def fontboun = g.font.getStringBounds('play', new FontRenderContext(null, true, true))
		playBounds = new Rectangle2D.Double(5, bounds.height / 7 - bounds.height / 40, fontboun.width, fontboun.height)
	}
}

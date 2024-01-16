package metagn.technocracy

import groovy.transform.CompileStatic

import javax.swing.JPanel
import java.awt.Graphics
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

@CompileStatic
class GamePanel extends JPanel {
	Camera camera
	Timer timer = new Timer()

	GamePanel() {
		camera = new Camera()
		focusable = true
		addKeyListener(new KeyAdapter() {
			@Override
			@CompileStatic
			void keyPressed(KeyEvent e) {
				switch (e.keyCode) {
					case KeyEvent.VK_X:
						camera.zoom /= 1.5; break
					case KeyEvent.VK_Z:
						camera.zoom *= 1.5; break
					case KeyEvent.VK_LEFT:
						camera.xPos -= 10; break
					case KeyEvent.VK_RIGHT:
						camera.xPos += 10; break
					case KeyEvent.VK_UP:
						camera.yPos -= 10; break
					case KeyEvent.VK_DOWN:
						camera.yPos += 10; break
				}
			}
		})
		timer.scheduleAtFixedRate(
			new AnonymousClassesArentStaticallyCompiledNorAreMethodClosuresOrClassesCreatedAtCoercionTime(), 0, 16)
	}

	class AnonymousClassesArentStaticallyCompiledNorAreMethodClosuresOrClassesCreatedAtCoercionTime extends TimerTask {
		void run() {
			repaint()
		}
	}

	void paintComponent(Graphics g) {
		super.paintComponent(g)
		camera.width = width
		camera.height = height
		g.drawImage(camera.draw(), 0, 0, null)

		/*def newChunks = new HashSet<Int2>()
		final BigDecimal realWidth, realHeight
		if (camera.zoom == 0) realWidth = realHeight = 0.0
		else {
			realWidth = width / camera.zoom
			realHeight = height / camera.zoom
		}
		final x1 = camera.xPos - (int) (realWidth / 2).round(FLOOR),
		      y1 = camera.yPos - (int) (realHeight / 2).round(FLOOR)
		final x2 = x1 + realWidth, y2 = y1 + realHeight
		final cx1 = (int) (x1 / areaXSize).round(FLOOR), cy1 = (int) (y1 / areaYSize).round(FLOOR)
		final cx2 = (int) (x2 / areaXSize).round(CEIL), cy2 = (int) (y2 / areaYSize).round(CEIL)
		for (int cx = cx1; cx <= cx2; ++cx) {
			for (int cy = cy1; cy <= cy2; ++cy) {
				final pair = new Int2(cx, cy)
				if (chunks.add(pair)) newChunks.add(pair)
			}
		}
		final xBound = Math.ceil(((camera.zoom == 0 ? 0.0 : width / camera.zoom) + camera.xPos) / areaXSize)
		final yBound = Math.ceil(((camera.zoom == 0 ? 0.0 : height / camera.zoom) + camera.yPos) / areaYSize)
		final a = xBound < 0, b = yBound < 0
		for (int x = 0; a ? x > xBound : x < xBound; ++x) {
			for (int y = 0; b ? y > yBound : y < yBound; ++y) {
				final pair = new Tuple2<Integer, Integer>(a ? -x : x, b ? -y : y)
				if (chunks.add(pair)) newChunks.add(pair)
			}
		}
		for (p in newChunks) {
			final x = p.x, y = p.y
			camera.universe.generateArea(x * areaXSize, y * areaYSize, (x + 1) * areaXSize, (y + 1) * areaYSize)
		}*/
	}
}

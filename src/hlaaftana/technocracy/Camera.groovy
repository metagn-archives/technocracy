package hlaaftana.technocracy

import groovy.transform.CompileStatic

import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.math.MathContext
import java.math.RoundingMode

@CompileStatic
class Camera {
	int width
	int height
	int xPos
	int yPos
	BigDecimal zoom = 0.25
	Universe universe

	static final MathContext ROUND_DOWN = new MathContext(0, RoundingMode.DOWN)
	static final MathContext ROUND_UP = new MathContext(0, RoundingMode.UP)

	Iterator<Int2> visibleChunks() {
		final int w, h
		final int x, y
		if (zoom == 0) x = y = w = h = 0
		else {
			w = (int) (width / zoom)
			h = (int) (height / zoom)
			x = (int) (xPos / zoom)
			y = (int) (yPos / zoom)
		}
		final cx1 = (int) (x / universe.chunkXSize).round(ROUND_DOWN),
		      cy1 = (int) (y / universe.chunkYSize).round(ROUND_DOWN)
		final cx2 = (int) ((x + w) / universe.chunkXSize).round(ROUND_UP),
		      cy2 = (int) ((y + h) / universe.chunkYSize).round(ROUND_UP)
		new Iterator<Int2>() {
			public int cx = cx1, cy = cy1

			public boolean more = true
			boolean hasNext() { more }

			Int2 next() {
				final result = new Int2(cx, cy)
				++cy
				if (cy > cy2) {
					cy = cy1
					++cx
				}
				if (cx > cx2) more = false
				result
			}
		}
	}

	BufferedImage draw() {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		Graphics2D g = img.createGraphics()
		g.color = Color.BLACK
		g.fillRect(0, 0, width, height)
		final vc = visibleChunks()
		while (vc.hasNext()) {
			final chunk = vc.next()
			g.color = new Color(0xFF, 0, 0, 0x33)
			g.fillRect(chunk.x * universe.chunkXSize, chunk.y * universe.chunkYSize, universe.chunkXSize, universe.chunkYSize)
			def stars = universe.chunks[chunk]
			if (null == stars) {
				universe.generateChunk(chunk)
				stars = universe.chunks[chunk]
			}
			for (s in stars) {
				int c = -xPos + (int) ((s.x - s.energy) * zoom)
				int d = -yPos + (int) ((s.y - s.energy) * zoom)
				if (c > 0 && c < width && d > 0 && d < height) {
					g.paint = g.color = s.color
					g.fillOval(c, d, (int) s.energy * 2 * zoom, (int) s.energy * 2 * zoom)
					for (p in s.planets) {
						int e = c - (int) (p.distanceFromStar * zoom)
						int f = d - (int) (p.distanceFromStar * zoom)
						g.paint = p.orbitColor
						def orbitDiameter = (p.distanceFromStar * 2 + s.energy * 2) * zoom
						g.drawOval(e, f, (int) orbitDiameter, (int) orbitDiameter)
						def planetAngle = orbitDiameter == 0 ? 0.0 : (p.orbitPosition / orbitDiameter) * Math.PI
						def planetOriginX = c + s.energy * zoom + (orbitDiameter / 2) * Math.cos(planetAngle)
						def planetOriginY = d + s.energy * zoom + (orbitDiameter / 2) * Math.sin(planetAngle)
						int sum = (int) p.layers.values().sum()
						for (en in p.layers) {
							def m = en.key, amount = en.value
							g.color = m.color
							g.fillOval((int) (planetOriginX - sum * zoom), (int) (planetOriginY - sum * zoom),
									(int) (sum * zoom * 2), (int) (sum * zoom * 2))
							sum -= amount
						}
					}
				}
			}
		}
		g.dispose()
		img
	}
}

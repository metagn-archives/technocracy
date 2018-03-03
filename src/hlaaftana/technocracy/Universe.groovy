package hlaaftana.technocracy

import groovy.transform.CompileStatic

import java.awt.Color

@CompileStatic
class Universe {
	Generator gen = new Generator()
	int chunkXSize
	int chunkYSize
	Map<Int2, List<Star>> chunks = new HashMap<>()

	Star generateStar(int x, int y) {
		def star = gen.makeStar(x, y)
		final size = gen.starTotalEnergy(star)
		star.maxEnergy = star.energy = size
		star.baseEnergy = gen.starBaseEnergy(star)
		final planetAmount = gen.planetAmount(star)
		Planet[] planets = new Planet[planetAmount]
		for (int i = 0; i < planetAmount; ++i) {
			def planet = new Planet(orbitPosition: 0)
			def materials = Material.values() as ArrayList<Material>
			gen.shuffleMaterials(star, planet, materials)
			final layerAmount = gen.planetLayerAmount(star, planet)
			def layers = new HashMap<Material, Integer>(layerAmount, 1)
			for (int j = 0; j < layerAmount; ++j) {
				final material = materials[j]
				layers.put(material, gen.planetLayerSize(star, planet, material))
			}
			planet.layers = layers
			planet.distanceFromStar = gen.planetDistance(star, planet)
			planet.orbitSpeed = gen.planetOrbitSpeed(star, planet)
			planet.orbitColor = gen.planetOrbitColor(star, planet)
			planets[i] = planet
		}
		star.planets = Arrays.<Planet>asList(planets)
		star.color = gen.starColor(star)
		star
	}

	List<Star> generateArea(int x1, int y1, int x2, int y2) {
		final amount = gen.starAmount(x1, y1, x2, y2)
		def xIterator = gen.xIterator(amount, x1, y1, x2, y2)
		def yIterator = gen.yIterator(amount, x1, y1, x2, y2)
		println "given area ($x1, $y1) to ($x2, $y2)"
		def result = new Star[amount]
		for (int a = 0; xIterator.hasNext(); ++a) {
			final x = xIterator.next(), y = yIterator.next()
			result[a] = generateStar(x, y)
		}
		Arrays.asList(result)
	}

	void generateChunk(Int2 chunk) {
		final x = chunk.x, y = chunk.y, ax = chunkXSize, ay = chunkYSize
		chunks.put(chunk, generateArea(x*ax, y*ay, (x+1)*ax, (y+1)*ay))
	}

	static class Generator { // OPEN CLASS
		Random random = new Random()
		// density = stars / px^2
		BigDecimal starSmallerDensity = 0.00000017
		BigDecimal starHigherDensity = 0.00000032
		int planetDensity = 10, starBaseEnergy = 50, baseDistance = 20,
				starTotalEnergy = 200, distance = 300, orbitSpeed = 50,
				layers = 8, layerSize = 10

		int starAmount(int x1, int y1, int x2, int y2) {
			final difference = starHigherDensity - starSmallerDensity
			final density = starSmallerDensity + difference * Math.random()
			final result = ((Math.abs(x2 - x1) * Math.abs(y2 - y1)) * density)
					.setScale(0, BigDecimal.ROUND_HALF_UP).intValue()
			println "calculated amount $result"
			result
		}

		Star makeStar(int x, int y) { new Star(x: x, y: y) }
		int starTotalEnergy(Star star) { random.nextInt(starTotalEnergy) + 1 }
		int starBaseEnergy(Star star) { random.nextInt(starBaseEnergy) + 1 }
		Color starColor(Star star) { new Color((0xFF << 16) | random.nextInt(0x10000)) }
		int planetAmount(Star star) { random.nextInt(planetDensity) }
		Color planetOrbitColor(Star star, Planet planet) { new Color(random.nextInt(0x1000000)) }
		int planetDistance(Star star, Planet planet) { random.nextInt(distance) + baseDistance + 1 }
		int planetLayerAmount(Star star, Planet planet) { random.nextInt(layers) + 1 }
		BigDecimal planetOrbitSpeed(Star star, Planet planet) { (random.nextInt(orbitSpeed) + 1) / 20 }
		void shuffleMaterials(Star star, Planet planet, List<Material> list) { Collections.shuffle(list, random) }
		int planetOrbitPosition(Star star, Planet planet) { 0 }
		int planetLayerSize(Star star, Planet planet, Material material) { random.nextInt(layerSize) + 1 }
		Iterator<Integer> xIterator(int amount, int x1, int y1, int x2, int y2) { random.ints(amount, x1, x2 + 1).iterator() }
		Iterator<Integer> yIterator(int amount, int x1, int y1, int x2, int y2) { random.ints(amount, y1, y2 + 1).iterator() }
	}
}

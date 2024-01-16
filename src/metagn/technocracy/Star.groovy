package metagn.technocracy

import groovy.transform.CompileStatic

import java.awt.Color

@CompileStatic
class Star {
	int baseEnergy, maxEnergy, energy
	int x, y
	Color color
	List<Planet> planets
}

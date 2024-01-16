package metagn.technocracy

import groovy.transform.CompileStatic

import java.awt.Color

@CompileStatic
class Planet {
	int distanceFromStar
	BigDecimal orbitSpeed
	int orbitPosition
	List<Material.IntPair> layers
	Color orbitColor
}

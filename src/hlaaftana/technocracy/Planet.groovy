package hlaaftana.technocracy

import groovy.transform.CompileStatic

import java.awt.Color

@CompileStatic
class Planet {
	int distanceFromStar
	BigDecimal orbitSpeed
	int orbitPosition
	Map<Material, Integer> layers
	Color orbitColor
}

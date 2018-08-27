package hlaaftana.technocracy

import groovy.transform.CompileStatic

import java.awt.Color

@CompileStatic
enum Material {
	KHOLIUM(0xEE564F), ZANIGRITE(0x1D728F), THITIUM(0x585CC4), PINITE(0x96A3CB),
	SHINAMENE(0x84B72), POLIN(0xAEB4BF), DIYATT(0x681561), GOJANWO(0x2742F)

	Color color
	Material(int clr){ color = new Color(clr) }

	static class IntPair {
		Material material
		int value

		IntPair(Material material, int value) {
			this.material = material
			this.value = value
		}
	}
}
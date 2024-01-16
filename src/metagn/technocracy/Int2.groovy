package metagn.technocracy

import groovy.transform.CompileStatic

@CompileStatic
class Int2 {
	long inner

	Int2(int x, int y) {
		set x, y
	}

	int getX() { (int) (inner >> 32) }
	int getY() { (int) inner }

	void set(int nx, int ny) {
		inner = (((long) nx) << 32) | (ny & 0xFFFFFFFFL)
	}

	void setY(int y) {
		set x, y
	}

	void setX(int x) {
		set x, y
	}

	int hashCode() { Long.hashCode(inner) }
	boolean equals(other) { other instanceof Int2 && inner == ((Int2) other).inner }
	boolean equals(int x, int y) { equals(new Int2(x, y)) }
	String toString() { "(" + x + ", " + y + ")" }
}

package metagn.technocracy

import groovy.transform.CompileStatic

import javax.swing.JFrame

@CompileStatic
class Main {
	static Universe universe = new Universe(chunkXSize: 2048, chunkYSize: 2048)

	static main(args){
		JFrame frame = new JFrame('Technocracy')
		frame.add(new MainMenuPanel())
		frame.setSize(400, 400)
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
		frame.setVisible(true)
	}
}
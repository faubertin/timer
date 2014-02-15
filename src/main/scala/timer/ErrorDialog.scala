package timer

import javax.swing.JOptionPane
import javax.swing.SwingUtilities

object ErrorDialog {
    
    def showErrorDialog(e: Exception) {
        SwingUtilities.invokeLater(new Runnable() {
            override def run() {
                JOptionPane.showMessageDialog(null, e.getMessage, "Error", JOptionPane.ERROR_MESSAGE)                
            }
        })
    }
    
}
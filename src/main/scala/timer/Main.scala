package timer

import scala.swing.SimpleSwingApplication
import scala.swing.MainFrame
import scala.swing.Button
import scala.swing.GridPanel
import scala.swing.Label
import scala.swing.TextField
import java.awt.Dimension
import org.quartz.impl.StdSchedulerFactory
import scala.swing.event.ButtonClicked
import org.quartz.impl.JobDetailImpl
import org.quartz.JobBuilder
import javax.swing.SwingUtilities
import javax.swing.JDialog
import javax.swing.JOptionPane
import javax.swing.JFrame
import javax.swing.JButton
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.JPanel
import javax.swing.GroupLayout
import java.awt.Container
import javax.swing.JLabel
import javax.swing.JTextField
import java.text.SimpleDateFormat
import javax.swing.JFileChooser

object Main {

    def main(args: Array[String]) {
        SwingUtilities.invokeLater(new Runnable() {
            override def run() {
                try {
                    val frame = new JFrame("Timer");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
 
                    frame.getContentPane().add(new TimerForm())
                    
                    frame.pack
                    frame.setVisible(true)
                } catch {
                    case e: Exception => ErrorDialog.showErrorDialog(e)
                }
            }
        })
    }
    
}

class TimerForm() extends JPanel {
    
    private val timerService = new TimerService
    
    private val cronExpressionLabel: JLabel = new JLabel("Cron expression")
    private val cronExpressionField: JTextField = new JTextField("40 1/2 * * * ?")
    private val soundFileLabel: JLabel = new JLabel("Sound file")
    private val soundFileField: JTextField = new JTextField("sound.wav")
    private val startButton: JButton = new JButton("Start")
    private val stopButton: JButton = new JButton("Stop")

    // Listeners
    startButton.addActionListener(new ActionListener() {
        override def actionPerformed(e: ActionEvent) {
            try {
                val soundFile = timerService.getSoundFile(soundFileField.getText())
                val nextTrigger = timerService.startTimer(
                        cronExpressionField.getText(),
                        soundFile)
                JOptionPane.showMessageDialog(TimerForm.this,
                        "Timer started, next trigger is " +
                        new SimpleDateFormat("HH:mm:ss").format(nextTrigger))
            } catch {
                case e: Exception => ErrorDialog.showErrorDialog(e)
            }
        }
    })
    stopButton.addActionListener(new ActionListener() {
        override def actionPerformed(e: ActionEvent) {
            try {
                timerService.stopTimer()
                JOptionPane.showMessageDialog(TimerForm.this, "Timer stopped")
            } catch {
                case e: Exception => ErrorDialog.showErrorDialog(e)
            }
        }
    })

    val groupLayout = new GroupLayout(this)
    groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
        .addGap(20)
        .addGroup(groupLayout.createParallelGroup()
            .addGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup()
                    .addComponent(cronExpressionLabel)
                    .addComponent(soundFileLabel)
                )
                .addGap(5)
                .addGroup(groupLayout.createParallelGroup()
                    .addComponent(cronExpressionField, 200, 300, 600)
                    .addComponent(soundFileField)
                )
            )
            .addGroup(groupLayout.createSequentialGroup()
                .addGap(15, 50, 1000)
                .addComponent(startButton, 100, 100, 100)
                .addGap(30)
                .addComponent(stopButton, 100, 100, 100)
                .addGap(15, 50, 1000)
            )
        )
        .addGap(20))
    groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
        .addGap(20)
        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(cronExpressionLabel)
            .addComponent(cronExpressionField)
        )
        .addGap(10)
        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(soundFileLabel)
            .addComponent(soundFileField)
        )
        .addGap(10)
        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(startButton)
            .addComponent(stopButton)
        )
        .addGap(20))
    setLayout(groupLayout)
    
    
}

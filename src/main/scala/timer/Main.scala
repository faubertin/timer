package timer

import scala.swing.SimpleSwingApplication
import scala.swing.MainFrame
import scala.swing.Button
import scala.swing.GridPanel
import scala.swing.Label
import scala.swing.TextField
import java.awt.Dimension

object Main extends SimpleSwingApplication {

    def top = new MainFrame {
        title = "Timer"
        contents = new GridPanel(6, 4) {
            vGap = 15
            hGap = 15
            contents ++= List(
                    new Filler(), new Filler(), new Filler(), new Filler(),
                    new Filler(), new Label("Cron expression"), new TextField(), new Filler(),
                    new Filler(), new Label("Sound file"), new TextField(), new Filler(),
                    new Filler(), new Label("Status"), new Label(), new Filler(),
                    new Filler(), new Button("START"), new Button("STOP"), new Filler(),
                    new Filler(), new Filler(), new Filler(), new Filler())
        }
    }

}

class Filler extends Label {

}

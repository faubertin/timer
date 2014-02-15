package timer

import org.quartz.impl.StdSchedulerFactory
import org.quartz.JobBuilder
import org.quartz.TriggerBuilder
import org.quartz.ScheduleBuilder
import org.quartz.CronScheduleBuilder
import org.quartz.JobKey
import org.quartz.Job
import org.quartz.JobExecutionContext
import java.util.Date
import java.io.File
import javax.sound.sampled.AudioSystem

object TimerService {
    
    val SOUND_FILE_KEY = "sound-file"
    
}

class TimerService {
    
    private val scheduler = StdSchedulerFactory.getDefaultScheduler()
    private val jobKey = new JobKey("timer-job")
    
    scheduler.start()
    Runtime.getRuntime().addShutdownHook(new Thread() {
        override def run() {
            scheduler.shutdown()
        }
    })
    
    def getSoundFile(path: String): File = {
        if (path == null || path.trim.isEmpty()) {
            throw new IllegalArgumentException("Please specify a sound file")
        }
        val file = new File(path)
        if (!file.exists) {
            throw new IllegalArgumentException("The sound file " + path + " does not exist")
        }
        file
    }
    
    def startTimer(
            cronExpression: String,
            soundFile: File): Date = {
        val job = JobBuilder.newJob(classOf[TimerJob])
                .withIdentity(jobKey)
                .usingJobData(TimerService.SOUND_FILE_KEY, soundFile.getAbsolutePath())
                .build()
        val trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build()
        scheduler.scheduleJob(job, trigger)
    }
    
    def stopTimer() {
        scheduler.deleteJob(jobKey)
    }
    
}

class TimerJob extends Job {
    
    override def execute(context: JobExecutionContext) {
        val soundFile = new File(
                context.getJobDetail()
                    .getJobDataMap()
                    .getString(TimerService.SOUND_FILE_KEY))
        val clip = AudioSystem.getClip()
        val audioInputStream = AudioSystem.getAudioInputStream(soundFile)
        clip.open(audioInputStream)
        clip.start()
        do  {
            Thread.sleep(400)            
        } while (clip.isRunning)
    }    
    
}
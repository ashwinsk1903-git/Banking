import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AuditLogger implements Runnable {
    private static final String STOP_SIGNAL = "<<STOP>>";
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final String logFilePath;
    private volatile boolean running = true;

    public AuditLogger(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public void log(String event) {
        if (!running) {
            return;
        }
        String message = formatEvent(event);
        queue.offer(message);
    }

    private String formatEvent(String event) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return timestamp + " - " + event;
    }

    public void stop() {
        running = false;
        queue.offer(STOP_SIGNAL);
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            while (true) {
                String event = queue.take();
                if (!running && STOP_SIGNAL.equals(event)) {
                    break;
                }
                writer.write(event);
                writer.newLine();
                writer.flush();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            System.err.println("Failed to write audit log: " + e.getMessage());
        }
    }
}

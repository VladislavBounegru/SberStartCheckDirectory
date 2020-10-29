package check_directory_api.Hander;

import check_directory_api.Watcher.Watcher;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class DeleteFileHandler implements Handler {

    private final Path path;

    public DeleteFileHandler(Path path) {
        this.path = path;
    }

    private static Logger LOGGER;

    static {
        try (FileInputStream ins = new FileInputStream("Log.config")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Watcher.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void action() {
        long start = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date startDate = new Date(start);
        try {
            Files.delete(path);
            long end = System.currentTimeMillis();
            LOGGER.log(Level.INFO, "File or directory " + path.getFileName()
                    + " deleted. Start time - " + sdf.format(startDate) + ", total processing time - " +
                    +(end - start) + " milliseconds");
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

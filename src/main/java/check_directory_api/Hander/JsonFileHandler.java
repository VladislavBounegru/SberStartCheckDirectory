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

public class JsonFileHandler implements Handler {

    private static Logger LOGGER;
    private Path path;

    public JsonFileHandler(Path path) {
        this.path = path;
    }

    static {
        try (FileInputStream ins = new FileInputStream("Log.config")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Watcher.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    @Override
    public void action() {
        long start = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date startDate = new Date(start);
        try {
            long lineCount = Files.lines(path).count();
            long end = System.currentTimeMillis();
            LOGGER.log(Level.INFO, "Added " + path.getFileName() + " file. Lines at file - " + lineCount + ". Start time - " + sdf.format(startDate) + ", total processing time - " +
                    +(end - start) + " milliseconds");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

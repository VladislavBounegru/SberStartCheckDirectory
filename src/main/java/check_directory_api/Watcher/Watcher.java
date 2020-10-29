package check_directory_api.Watcher;

import check_directory_api.Controller.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Watcher {
    Path path;
    Controller controller;
    private static Logger LOGGER;

    static {
        try (FileInputStream ins = new FileInputStream("Log.config")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Watcher.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public Watcher(Path path) {
        this.path = path;
    }

    public void checkDirectory() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE
            );
            WatchKey watchKey;

            while (true) {
                watchKey = watchService.poll(10, TimeUnit.MINUTES);
                if (watchKey != null) {
                    watchKey.pollEvents().forEach(event -> {
                        Path newFilePath = Paths.get(path.toString() + "/" + event.context());
                        if (event.kind() == StandardWatchEventKinds.OVERFLOW)
                        {
                            LOGGER.log(Level.WARNING,"File listener recieved an overflow event.  You should probably check into this");
                            return;
                        }
                        if (!event.context().toString().equals(".DS_Store")
                                && event.kind() != StandardWatchEventKinds.ENTRY_DELETE) {
                            log(newFilePath);
                            controller = new Controller(newFilePath);
                            executorService.submit(controller);
                        }
                    });
                }
                assert watchKey != null;
                watchKey.reset();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public void log(Path path) {
        long start = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date startDate = new Date(start);
        LOGGER.log(Level.INFO, "Added " + path.getFileName() + " file ad - " + sdf.format(startDate));
    }

}

package check_directory_api.Controller;

import check_directory_api.Hander.DeleteFileHandler;
import check_directory_api.Hander.Handler;
import check_directory_api.Hander.JsonFileHandler;
import check_directory_api.Hander.XmlFileHandler;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements Runnable {
    private final Path path;
    private final Handler fp;


    public Controller(Path path) {
        this.path = path;
        fp = getHandler();
    }

    public Handler getHandler() {
        Handler handler;
        if (path.getFileName().toString().endsWith(".json")) {
            handler = new JsonFileHandler(path);
        } else if (path.getFileName().toString().endsWith(".xml")) {
            System.out.println(path.getFileName().toString());
            handler = new XmlFileHandler(path);
        } else {
            handler = new DeleteFileHandler(path);
        }

        return handler;
    }

    @Override
    public void run() {
        fp.action();

    }
}

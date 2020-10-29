package check_directory_api.Controller;

import check_directory_api.Hander.DeleteFileHandler;
import check_directory_api.Hander.Handler;
import check_directory_api.Hander.JsonFileHandler;
import check_directory_api.Hander.XmlFileHandler;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    private final Path path;
    private final Handler fp;
    private static final ExecutorService DELETE_SERVICE = Executors.newSingleThreadExecutor();


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

    public void action() {
        DELETE_SERVICE.submit(() -> {
            fp.action();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


}

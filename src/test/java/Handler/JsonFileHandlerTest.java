package Handler;

import check_directory_api.Hander.JsonFileHandler;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFileHandlerTest {//TODO find a way to make correct test

    @Test
    public void jsonHandlerTest() throws InterruptedException {
        Path path = Paths.get("src/test/resources/TestController/test.json");
        JsonFileHandler handler = new JsonFileHandler(path);
        handler.action();
    }
}

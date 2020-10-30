package Handler;

import check_directory_api.Hander.JsonFileHandler;
import check_directory_api.Hander.XmlFileHandler;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class XmlFileHandlerTest { //TODO find a way to make correct test
    @Test
    public void jsonHandlerTest() throws InterruptedException {
        Path path = Paths.get("src/test/resources/TestController/test.json");
        XmlFileHandler handler = new XmlFileHandler(path);
        handler.action();
    }

}

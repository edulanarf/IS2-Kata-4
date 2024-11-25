package ulpgc.es.control;


import ulpgc.es.model.Title;

import java.io.IOException;
import java.util.List;

public interface TitleReader {
    List<Title> read() throws IOException;
}

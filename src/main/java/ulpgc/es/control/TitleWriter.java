package ulpgc.es.control;

import ulpgc.es.model.Title;

import java.io.IOException;

public interface TitleWriter {
    void write(Title title) throws IOException;
}

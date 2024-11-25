package ulpgc.es.control;

import ulpgc.es.model.Title;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class TitleLoader {
    public void loadTitles(File source, File target) throws IOException {
        TitleReader reader = new TsvTitleReader(source);
        SQLiteTitleWriter dbWriter = new SQLiteTitleWriter(target);
        Iterator<Title> titles=reader.read();
        while(titles.hasNext()){
            dbWriter.write(titles.next());
        }
    }
}

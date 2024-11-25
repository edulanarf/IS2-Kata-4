package ulpgc.es.control;

import ulpgc.es.model.Title;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TsvTitleReader implements TitleReader{
    private final File source;
    private final TsvTitleDeserialize deserializer;

    public TsvTitleReader(File source, TsvTitleDeserialize deserializer) {
        this.source = source;
        this.deserializer = new TsvTitleDeserialize();
    }

    @Override
    public Iterator<Title> read() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            readHeaderWith(reader);
            return readTitlesWith(reader);
        }
    }

    private Iterator<Title> readTitlesWith(BufferedReader reader) throws IOException {
        return new Iterator<>(){
            String line = reader.readLine();
            @Override
            public boolean hasNext(){return line!=null;}

            @Override
            public Title next(){
                try{
                    Title title = line == null ? null : titleOf(line);
                    line = reader.readLine();
                    if(line==null) reader.close();
                    return title;
                } catch (IOException e){
                    return null;
                }
            }
        };

    }

    private static void readHeaderWith(BufferedReader reader) throws IOException {
        reader.readLine();
    }
    private Title titleOf(String l){
        return deserializer.deserialize(l);
    }
}
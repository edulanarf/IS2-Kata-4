package ulpgc.es.control;

import ulpgc.es.model.Title;

public interface TitleDeserializer {
    Title deserialize(String content);
}
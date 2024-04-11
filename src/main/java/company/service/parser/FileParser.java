package company.service.parser;

import java.util.List;

public interface FileParser<T> {
    List<T> parseFile(String fileName);
}

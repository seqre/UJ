package uj.jwzp2019.saver;

import uj.jwzp2019.model.Person;

import java.io.IOException;
import java.util.List;

public interface Saver {

    void saveList(List<Person> list, String fileName) throws IOException;
}

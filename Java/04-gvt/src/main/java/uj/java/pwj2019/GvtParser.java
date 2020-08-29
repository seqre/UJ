package uj.java.pwj2019;

import java.nio.file.Path;

public interface GvtParser {
    int init() throws Exception;

    int addFile(String path) throws Exception;

    int addFile(String path, String message) throws Exception;

    int detachFile(String path) throws Exception;

    int detachFile(String path, String message) throws Exception;

    int checkoutRepo(String version) throws Exception;

    int commitFile(String path) throws Exception;

    int commitFile(String path, String message) throws Exception;

    int history() throws Exception;

    int history(String amount) throws Exception;

    int version() throws Exception;

    int version(String version) throws Exception;

    Path checkPath(String path);

    long checkNumber(String number);

    boolean isInitialized() throws Exception;
}

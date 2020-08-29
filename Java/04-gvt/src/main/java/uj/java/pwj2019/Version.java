package uj.java.pwj2019;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.LinkedList;

public class Version implements Serializable {
    private long versionID;
    private LinkedList<String> commitMessage;
    private LinkedList<String> listOfFiles;

    public Version() {
        this.versionID = 0;
        this.commitMessage = new LinkedList<>();
        this.listOfFiles = new LinkedList<>();
    }

    public Version(long versionID) {
        this.versionID = versionID;
        this.commitMessage = new LinkedList<>();
        this.listOfFiles = new LinkedList<>();
    }

    public Version(Version other) {
        this.versionID = other.versionID + 1;
        this.commitMessage = new LinkedList<>();
        this.listOfFiles = new LinkedList<>(other.listOfFiles);
    }

    public void addCommitMessage(String commitMessage) {
        this.commitMessage.add(commitMessage);
    }

    public void addFile(Path path) {
        listOfFiles.add(path.getFileName().toString());
    }

    public void removeFile(Path path) {
        listOfFiles.remove(path.getFileName().toString());
    }

    public long getVersionID() {
        return versionID;
    }

    public LinkedList<String> getCommitMessage() {
        return commitMessage;
    }

    public LinkedList<String> getListOfFiles() {
        return listOfFiles;
    }

    public boolean doesFileExists(Path path) {
        return listOfFiles.contains(path.getFileName().toString());
    }
}

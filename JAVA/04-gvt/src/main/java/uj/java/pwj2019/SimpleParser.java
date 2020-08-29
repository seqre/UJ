package uj.java.pwj2019;

import java.io.*;
import java.nio.file.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

public class SimpleParser implements GvtParser {
    private final static String GVT = ".gvt";
    private final static String VERSIONS = "versions";
    private final static String FILES = "files";
    private final static String INFO = "info";
    private final static String SLASH = "/";
    private final static String PARENT_DIR = "..";

    @Override
    public int init() throws IOException, ClassNotFoundException {
        if (isInitialized()) {
            return 10;
        } else {
            Files.createDirectory(Path.of(GVT));
            Files.createDirectory(Path.of(GVT + SLASH + VERSIONS));
            Files.createDirectory(Path.of(GVT + SLASH + FILES));
            Files.createFile(Path.of(GVT + SLASH + INFO));

            Version version = new Version();
            version.addCommitMessage("GVT initialized.");
            saveToActualVersion(version);
            moveActualToSaved();

            saveToActualVersion(new Version(version));

            return 0;
        }
    }

    @Override
    public int addFile(String givenPath) throws IOException, ClassNotFoundException {
        return addFile(givenPath, null);
    }

    @Override
    public int addFile(String givenPath, String message) throws IOException, ClassNotFoundException {
        Path path = checkPath(givenPath);

        if (path == null) {
            return 22;
        }
        if (!Files.exists(path)) {
            return 21;
        }

        Version actualVersion = getActualVersion();

        if (actualVersion.doesFileExists(path)) {
            return 1;
        } else {
            actualVersion.addFile(path);

            actualVersion.addCommitMessage("Added file: " + path.getFileName().toString());
            if (message != null) {
                actualVersion.addCommitMessage(message);
            }

            saveToActualVersion(actualVersion);

            newVersion();
        }
        return 0;
    }

    @Override
    public int detachFile(String path) throws IOException, ClassNotFoundException {
        return detachFile(path, null);
    }

    @Override
    public int detachFile(String givenPath, String message) throws IOException, ClassNotFoundException {
        Path path = checkPath(givenPath);

        if (path == null) {
            return 31;
        }

        Version actualVersion = getActualVersion();

        if (!actualVersion.doesFileExists(path)) {
            return 1;
        } else {
            actualVersion.removeFile(path);

            actualVersion.addCommitMessage("Detached file: " + path.getFileName().toString());
            if (message != null) {
                actualVersion.addCommitMessage(message);
            }

            saveToActualVersion(actualVersion);

            newVersion();
        }
        return 0;
    }

    @Override
    public int checkoutRepo(String version) throws IOException, ClassNotFoundException {
        long versionNumber;

        if (version == null) {
            return 40;
        } else {
            versionNumber = Long.parseUnsignedLong(version);

            if (versionNumber > getActualVersion().getVersionID()) {
                return 40;
            }
        }

        Version prevVersion = getPreviousVersion(versionNumber);
        ListIterator<String> it = prevVersion.getListOfFiles().listIterator();
        Path temp;
        while (it.hasNext()) {
            temp = Path.of(GVT + SLASH + VERSIONS + SLASH + versionNumber + SLASH + FILES + SLASH + it.next());
            Files.copy(temp, Path.of(GVT + SLASH + PARENT_DIR + SLASH + temp.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }

        return 0;
    }

    @Override
    public int commitFile(String path) throws IOException, ClassNotFoundException {
        return commitFile(path, null);
    }

    @Override
    public int commitFile(String givenPath, String message) throws IOException, ClassNotFoundException {
        Path path = checkPath(givenPath);

        if (path == null) {
            return 50;
        }
        if (!Files.exists(path)) {
            return 21;
        }

        Version actualVersion = getActualVersion();

        if (!actualVersion.getListOfFiles().contains(path.getFileName().toString())) {
            actualVersion.addFile(path);
        }

        actualVersion.addCommitMessage("Committed file: " + path.getFileName().toString());
        if (message != null) {
            actualVersion.addCommitMessage(message);
        }

        saveToActualVersion(actualVersion);

        newVersion();

        return 0;
    }

    @Override
    public int history() throws IOException, ClassNotFoundException {
        return history(null);
    }

    @Override
    public int history(String amount) throws IOException, ClassNotFoundException {
        Version actualVersion = getActualVersion();

        long delimiter;
        long lastCommitedVersion = actualVersion.getVersionID() - 1;

        if (amount == null) {
            delimiter = -1;
        } else {
            delimiter = lastCommitedVersion - Long.parseUnsignedLong(amount);

            if (delimiter < 0) {
                delimiter = -1;
            }
        }

        LinkedList<String> result = new LinkedList<>();
        while (lastCommitedVersion > delimiter) {
            result.add(lastCommitedVersion + ": " + getPreviousVersion(lastCommitedVersion).getCommitMessage().get(0));
            --lastCommitedVersion;
        }
        Collections.reverse(result);
        result.forEach(System.out::println);

        return 0;
    }

    @Override
    public int version() throws IOException, ClassNotFoundException {
        return version(null);
    }

    @Override
    public int version(String version) throws IOException, ClassNotFoundException {
        long versionNumber;


        if (version == null) {
            versionNumber = getActualVersion().getVersionID() - 1;
        } else {
            versionNumber = Long.parseUnsignedLong(version);
        }

        Version actualVersion = getPreviousVersion(versionNumber);

        System.out.println("Version: " + versionNumber);
        ListIterator<String> it = actualVersion.getCommitMessage().listIterator();
        String temp;
        while (it.hasNext()) {
            temp = it.next();
            if (it.hasNext()) {
                System.out.println(temp);
            } else {
                System.out.print(temp);
            }
        }
        return 0;
    }

    @Override
    public Path checkPath(String path) {
        try {
            return Path.of(path);
        } catch (InvalidPathException e) {
            return null;
        }
    }

    @Override
    public long checkNumber(String number) {
        try {
            return Long.parseUnsignedLong(number);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public boolean isInitialized() throws SecurityException {
        return Files.exists(Path.of(GVT));
    }

    private Version getActualVersion() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(GVT + SLASH + INFO));
        Version result = (Version) in.readObject();
        in.close();
        return result;
    }

    private Version getPreviousVersion(long number) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(GVT + SLASH + VERSIONS + SLASH + number + SLASH + INFO));
        Version result = (Version) in.readObject();
        in.close();
        return result;
    }

    private void saveToActualVersion(Version version) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(GVT + SLASH + INFO));
        out.writeObject(version);
        out.close();
    }

    private void moveActualToSaved() throws IOException, ClassNotFoundException {
        Version actualVersion = getActualVersion();

        Files.createDirectory(Path.of(GVT + SLASH + VERSIONS + SLASH + actualVersion.getVersionID()));
        Files.createDirectory(Path.of(GVT + SLASH + VERSIONS + SLASH + actualVersion.getVersionID() + SLASH + FILES));

        Files.copy(Path.of(GVT + SLASH + INFO), Path.of(GVT + SLASH + VERSIONS + SLASH + actualVersion.getVersionID() + SLASH + INFO));

        ListIterator<String> it = actualVersion.getListOfFiles().listIterator();
        Path temp;
        while (it.hasNext()) {
            temp = Path.of(GVT + SLASH + PARENT_DIR + SLASH + it.next());
            Files.copy(temp, Path.of(GVT + SLASH + VERSIONS + SLASH + actualVersion.getVersionID() + SLASH + FILES + SLASH + temp.getFileName()));
        }
    }

    private void newVersion() throws IOException, ClassNotFoundException {
        Version version = getActualVersion();
        moveActualToSaved();
        saveToActualVersion(new Version(version));
    }
}

package uj.jwzp.hellodi.model;


public enum FileExt {
    XML("xml"),
    JSON("json"),
    YML("yml");

    final String ext;

    FileExt(String ext) {
        this.ext = ext;
    }

    public static FileExt getFromString(String ext) {
        return FileExt.valueOf(ext.toUpperCase());
    }

    public String getExt() {
        return ext;
    }
}

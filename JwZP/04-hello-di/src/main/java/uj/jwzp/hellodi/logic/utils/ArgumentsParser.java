package uj.jwzp.hellodi.logic.utils;

import uj.jwzp.hellodi.model.FileExt;

public class ArgumentsParser {
    public static String getFileName(String[] args) {
        return args.length == 0 ? "saved.xml" : args[0];
    }

    public static FileExt getFileExt(String[] args) {
        return FileExt.getFromString(args.length < 2 ? "xml" : args[1]);
    }
}

package uj.java.pwj2019;

public class Gvt {
    private static GvtParser parser = new SimpleParser();

    public static void main(String[] args) {
        if (args.length == 0) {
            throwError(1, "Please specify command.");
        }

        if (args[0].equals("init")) {
            init();
        } else {
            if (isInitialized()) {
                switch (args[0]) {
                    case "add":
                        add(args);
                        break;

                    case "detach":
                        detach(args);
                        break;

                    case "commit":
                        commit(args);
                        break;

                    case "checkout":
                        checkout(args);
                        break;

                    case "history":
                        history(args);
                        break;

                    case "version":
                        version(args);
                        break;

                    default:
                        throwError(1, "Unknown command {" + args[0] + "}.");
                }
            } else {
                throwError(2, "Current directory is not initialized. Please use \"init\" command to initialize.");
            }
        }
        System.exit(0);
    }

    private static void throwError(int errorCode, String errorMessage) {
        System.out.println(errorMessage);
        System.exit(errorCode);
    }

    private static void throwError(int errorCode, String errorMessage, Exception e) {
        System.out.println(errorMessage);
        e.printStackTrace(System.err);
        System.exit(errorCode);
    }

    private static boolean isInitialized() {
        try {
            return parser.isInitialized();
        } catch (Exception e) {
            throwError(3, "Underlying system problem. See ERR for details.", e);
        }
        return false;
    }

    private static void init() {
        try {
            int result = parser.init();

            if (result == 10) {
                throwError(10, "Current directory is already initialized.");
            } else {
                System.out.println("Current directory initialized successfully.");
            }
        } catch (Exception e) {
            throwError(3, "Underlying system problem. See ERR for details.", e);
        }
    }

    private static void add(String[] args) {
        String comment = getComment(args);
        int response = -1;

        try {
            if (args.length <= 1) {
                throwError(20, "Please specify file to add.");
            }

            if (comment == null) {
                response = parser.addFile(args[1]);
            } else {
                response = parser.addFile(args[1], comment);
            }
        } catch (Exception e) {
            throwError(22, "File " + args[1] + " cannot be added, see ERR for details.", e);
        }

        switch (response) {
            case 0:
                System.out.println("File " + args[1] + " added successfully.");
                break;

            case 21:
                throwError(21, "File " + args[1] + " not found.");
                break;

            case 1:
                System.out.println("File " + args[0] + " already added.");
                break;
        }
    }

    private static void detach(String[] args) {
        String comment = getComment(args);
        int response = -1;

        try {
            if (args.length <= 1) {
                throwError(30, "Please specify file to detach.");
            }

            if (comment == null) {
                response = parser.detachFile(args[1]);
            } else {
                response = parser.detachFile(args[1], comment);
            }
        } catch (Exception e) {
            throwError(31, "File " + args[1] + " cannot be detached, see ERR for details.", e);
        }

        switch (response) {
            case 0:
                System.out.println("File " + args[1] + " detached successfully.");
                break;

            case 1:
                System.out.println("File " + args[1] + " is not added to gvt.");
                break;
        }
    }

    private static void commit(String[] args) {
        String comment = getComment(args);
        int response = -1;

        try {
            if (args.length <= 1) {
                throwError(50, "Please specify file to commit.");
            }

            if (comment == null) {
                response = parser.commitFile(args[1]);
            } else {
                response = parser.commitFile(args[1], comment);
            }
        } catch (Exception e) {
            throwError(52, "File " + args[1] + " cannot be commited, see ERR for details.", e);
        }

        switch (response) {
            case 0:
                System.out.println("File " + args[1] + " committed successfully.");
                break;

            case 51:
                throwError(51, "File " + args[1] + " does not exist.");
                break;

            case 1:
                System.out.println("File " + args[0] + " is not added to gvt.");
                break;
        }
    }

    private static void checkout(String[] args) {
        int response = -1;

        try {
            response = parser.checkoutRepo(args[1]);
        } catch (Exception e) {
            throwError(3, "Underlying system problem. See ERR for details.", e);
        }

        switch (response) {
            case 0:
                System.out.println("Version " + args[1] + " checked out successfully.");
                break;

            case 40:
                throwError(40, "Invalid version number: " + args[1]);
                break;
        }
    }

    private static void history(String[] args) {
        String number;
        int index = 0;

        while (index < args.length && !args[index++].equals("-last")) {
        }

        if (index < args.length) {
            number = args[index];
        } else {
            number = null;
        }

        try {
            if (number == null) {
                parser.history();
            } else {
                parser.history(number);
            }
        } catch (Exception e) {
            throwError(3, "Underlying system problem. See ERR for details.", e);
        }
    }

    private static void version(String[] args) {
        int response = -1;

        try {
            if (args.length <= 1) {
                response = parser.version();
            } else {
                response = parser.version(args[1]);
            }
        } catch (Exception e) {
            throwError(3, "Underlying system problem. See ERR for details.", e);
        }

        if (response == 60) {
            throwError(60, "Invalid version number: " + args[1]);
        }
    }

    private static String getComment(String[] args) {
        int index = 0;

        while (index < args.length && !args[index++].equals("-m")) {
        }

        if (index < args.length) {
            return args[index];
        } else {
            return null;
        }
    }
}


package src.Exception;

public class FileError {
    public static class ErrorNoArgument extends Exception {
        public ErrorNoArgument() {
            super(" No arguments given to program");
        }
    }

    public static class ErrorTooManyArgument extends Exception {
        public ErrorTooManyArgument() {
            super(" Too many arguments given to program");
        }
    }

    public static class FileNotFound extends Exception {
        public FileNotFound(String fileName) {
            super(" Entry File not found " + fileName);
        }
    }

    public static class FileEmpty extends Exception {
        public FileEmpty(String fileName) {
            super(" Entry File empty " + fileName);
        }
    }
}
package edu.cornell.ics;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.File;
import java.io.IOException;


public class App implements Runnable {

    //
    // main program
    //

    public static void main(final String[] args) {
        new App(args).run();
    }


    //
    // private final fields
    //

    private static final String defaultFileEncoding = "UTF-8";

    private static final LongOpt[] longopts = new LongOpt[] {
        new LongOpt("encoding",  LongOpt.REQUIRED_ARGUMENT, new StringBuffer(), 'e'),
        new LongOpt("help",      LongOpt.NO_ARGUMENT,       null,               'h'),
        new LongOpt("verbose",   LongOpt.NO_ARGUMENT,       null,               'v'),
    };


    private final String[] args;

    //
    // public constructors
    //

    public App(final String[] args) {
        this.args = args;
    }


    @Override
    public void run() {
        final String cwd = System.getProperty("user.dir");
        String baseDir   = cwd;
        String encoding  = defaultFileEncoding;
        boolean verbose = false;

        final StringBuffer sb = new StringBuffer();
        final Getopt g = new Getopt("ics-snd app", this.args, "b:m:t:e:hv;", longopts);
        g.setOpterr(false); // We'll do our own error handling
        int c;
        while ((c = g.getopt()) != -1)
            switch (c) {
                case 'b':
                    baseDir = g.getOptarg();
                    break;
                case 'e':
                    encoding = g.getOptarg();
                    break;
                case 'h':
                    printUsage();
                    System.exit(0);
                    break;
                case 'v':
                    verbose = true;
                    break;
                case '?':
                default:
                    System.out.println("The option '" + (char) g.getOptopt() + "' is not valid");
                    printUsage();
                    System.exit(1);
                    break;
            }


        if (verbose) {
            System.err.println(String.format("encoding  = %s", encoding));
        }
    }


    private void printUsage() {
        System.err.println("Syntax: [-b <baseDir>] [-m <modelDir>] [-t <targetDir>] [-e <encoding>] [-h] [-v]");
        System.err.println("Options:");
        System.err.println("  -b, --baseDir     project home directory, e.g: /home/workspace/myapp");
        System.err.println("  -e, --encoding    default is: ".concat(defaultFileEncoding));
        System.err.println("  -h, --help        this help text");
        System.err.println("  -v, --verbose     verbose mode");
    }

    private String checkDir(final String basedir, final String dir, final boolean create, final boolean fail) throws IOException {
        final StringBuilder sb = new StringBuilder();
        sb.append(basedir).append('/').append(dir);
        final File f = new File(sb.toString()).getCanonicalFile();
        final String absolutePath = f.getAbsolutePath();
        if (create) {
            if (f.isDirectory()) return absolutePath;
            if (f.mkdirs()) return absolutePath;
        } else {
            if (f.isDirectory()) return absolutePath;
        }
        if (!fail) return null;
        throw new IOException(String.format("Failed to locate or create directory %s", absolutePath));
    }

    private String checkFile(final String basedir, final String file, final boolean fail) throws IOException {
        final StringBuilder sb = new StringBuilder();
        sb.append(basedir).append('/').append(file);
        final File f = new File(sb.toString()).getCanonicalFile();
        final String absolutePath = f.getAbsolutePath();
        if (f.isFile()) return absolutePath;
        if (!fail) return null;
        throw new IOException(String.format("Failed to find or locate directory %s", absolutePath));
    }

}
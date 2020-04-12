package yuanjun.chen.advanced.dp.assist;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class GraphViz {

    private final static String osName = System.getProperty("os.name").replaceAll("\\s", "");

    private final int[] dpiSizes = {46, 51, 57, 63, 70, 78, 86, 96, 106, 116, 128, 141, 155, 170, 187, 206, 226, 249};

    private int currentDpiPos = 7;

    public void increaseDpi() {
        if (this.currentDpiPos < (this.dpiSizes.length - 1)) {
            ++this.currentDpiPos;
        }
    }

    public void decreaseDpi() {
        if (this.currentDpiPos > 0) {
            --this.currentDpiPos;
        }
    }

    public int getImageDpi() {
        return this.dpiSizes[this.currentDpiPos];
    }

    private StringBuilder graph = new StringBuilder();

    private String tempDir;

    private String executable;

    public GraphViz() {
        if (GraphViz.osName.contains("Windows")) {
            this.tempDir = "d:/tmp";
            this.executable = "c:/Program Files (x86)/Graphviz2.38/bin/dot.exe";
        } else if (GraphViz.osName.equals("MacOSX")) {
            this.tempDir = "/tmp";
            this.executable = "/usr/local/bin/dot";
        } else if (GraphViz.osName.equals("Linux")) {
            this.tempDir = "/tmp";
            this.executable = "/usr/bin/dot";
        }
    }

    public GraphViz(String executable, String tempDir) {
        this.executable = executable;
        this.tempDir = tempDir;
    }

    public String getDotSource() {
        return this.graph.toString();
    }

    public void add(String line) {
        this.graph.append(line);
    }

    public void addln(String line) {
        this.graph.append(line + "\n");
    }

    public void addln() {
        this.graph.append('\n');
    }

    public void clearGraph() {
        this.graph = new StringBuilder();
    }

    public byte[] getGraph(String dot_source, String type, String representationType) {
        File dot;
        byte[] img_stream = null;
        try {
            dot = writeDotSourceToFile(dot_source);
            if (dot != null) {
                img_stream = get_img_stream(dot, type, representationType);
                if (!dot.delete()) {
                    System.err.println("Warning: " + dot.getAbsolutePath() + " could not be deleted!");
                }
                return img_stream;
            }
            return null;
        } catch (IOException ioe) {
            return null;
        }
    }

    public int writeGraphToFile(byte[] img, String file) {
        return writeGraphToFile(img, new File(file));
    }

    public int writeGraphToFile(byte[] img, File to) {
        try {
            FileOutputStream fos = new FileOutputStream(to);
            fos.write(img);
            fos.close();
        } catch (IOException ioe) {
            return -1;
        }
        return 1;
    }

    private byte[] get_img_stream(File dot, String type, String representationType) {
        File img;
        byte[] img_stream = null;
        try {
            img = File.createTempFile("graph_", "." + type, new File(this.tempDir));
            Runtime rt = Runtime.getRuntime();
            // patch by Mike Chenault
            // representation type with -K argument by Olivier Duplouy
            String[] args = {executable, "-T" + type, "-K" + representationType,
                    "-Gdpi=" + dpiSizes[this.currentDpiPos], dot.getAbsolutePath(), "-o", img.getAbsolutePath()};
            Process p = rt.exec(args);
            p.waitFor();
            FileInputStream in = new FileInputStream(img.getAbsolutePath());
            img_stream = new byte[in.available()];
            in.read(img_stream);
            // Close it if we need to
            if (in != null) {
                in.close();
            }
            if (!img.delete()) {
                System.err.println("Warning: " + img.getAbsolutePath() + " could not be deleted!");
            }
        } catch (IOException ioe) {
            System.err.println("Error:    in I/O processing of tempfile in dir " + tempDir + "\n");
            System.err.println("       or in calling external command");
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            System.err.println("Error: the execution of the external program was interrupted");
            ie.printStackTrace();
        }
        return img_stream;
    }

    private File writeDotSourceToFile(String str) throws java.io.IOException {
        File temp;
        try {
            File f = new File(tempDir);
            temp = File.createTempFile("graph_", ".dot.tmp", f);
            FileWriter fout = new FileWriter(temp);
            fout.write(str);
            fout.close();
        } catch (Exception e) {
            System.err.println("Error: I/O error while writing the dot source to temp file!");
            return null;
        }
        return temp;
    }

    public String start_graph() {
        return "digraph G {";
    }

    public String end_graph() {
        return "}";
    }

    public String start_subgraph(int clusterid) {
        return "subgraph cluster_" + clusterid + " {";
    }

    public String end_subgraph() {
        return "}";
    }

    public void readSource(String input) {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(input);
            DataInputStream dis = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            dis.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        this.graph = sb;
    }

} // end of class GraphViz

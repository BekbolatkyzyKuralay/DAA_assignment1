import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlotGenerator {
    public static void main(String[] args) {
        List<Result> results = readResults();
        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }
        File folder = new File("docs", "plots");

        if (!folder.exists()) {
            boolean created = folder.mkdirs();

            if (!created) {
                System.out.println("Could not create plots folder.");
                return;
            }
        }
        createTimePlot(results);
        createDepthPlot(results);
        System.out.println("Plot generation finished.");
    }

    private static List<Result> readResults() {
        List<Result> results = new ArrayList<>();
        try {
            BufferedReader reader =
                    new BufferedReader(
                            new FileReader("results/results.csv")
                    );
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 7) {
                    continue;
                }
                String algorithm = parts[0];
                String inputType = parts[1];
                int size = Integer.parseInt(parts[2]);
                long time = Long.parseLong(parts[3]);
                int depth = Integer.parseInt(parts[4]);
                if (inputType.equals("Random")) {
                    results.add(
                            new Result(
                                    algorithm,
                                    size,
                                    time,
                                    depth
                            )
                    );
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Could not read results.csv");
        }
        return results;
    }

    private static void createTimePlot(List<Result> results) {
        int width = 900;
        int height = 600;
        BufferedImage image =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_RGB
                );
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawString(
                "Execution Time vs Input Size",
                350,
                30
        );

        int left = 80;
        int bottom = 520;
        int graphWidth = 740;
        int graphHeight = 430;

        g.drawLine(left, bottom, left + graphWidth, bottom);
        g.drawLine(left, bottom, left, bottom - graphHeight);

        g.drawString("Input Size (n)", 390, 570);
        g.drawString("Time (ns)", 15, 70);

        long maxTime = 1;
        int maxSize = 1;

        for (Result result : results) {
            if (result.time > maxTime) {
                maxTime = result.time;
            }
            if (result.size > maxSize) {
                maxSize = result.size;
            }
        }

        String[] algorithms = {
                "MergeSort",
                "QuickSort",
                "DeterministicSelect",
                "ClosestPair"
        };

        Color[] colors = {
                Color.BLUE,
                Color.RED,
                Color.GREEN,
                Color.MAGENTA
        };

        for (int a = 0; a < algorithms.length; a++) {
            g.setColor(colors[a]);
            int previousX = -1;
            int previousY = -1;
            for (Result result : results) {
                if (!result.algorithm.equals(algorithms[a])) {
                    continue;
                }
                int x = left
                        + (int) (
                        (double) result.size
                                / maxSize
                                * graphWidth
                );
                int y = bottom
                        - (int) (
                        (double) result.time
                                / maxTime
                                * graphHeight
                );
                g.fillOval(x - 4, y - 4, 8, 8);
                if (previousX != -1) {
                    g.drawLine(
                            previousX,
                            previousY,
                            x,
                            y
                    );
                }
                previousX = x;
                previousY = y;
            }
        }

        drawLegend(g, algorithms, colors);
        g.dispose();
        try {
            ImageIO.write(
                    image,
                    "png",
                    new File("docs", "plots/time_vs_n.png")
            );
        } catch (IOException e) {
            System.out.println("Could not save time plot.");
        }
    }

    private static void createDepthPlot(List<Result> results) {
        int width = 900;
        int height = 600;
        BufferedImage image =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_RGB
                );

        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawString(
                "Recursion Depth vs Input Size",
                340,
                30
        );

        int left = 80;
        int bottom = 520;
        int graphWidth = 740;
        int graphHeight = 430;

        g.drawLine(left, bottom, left + graphWidth, bottom);
        g.drawLine(left, bottom, left, bottom - graphHeight);

        g.drawString("Input Size (n)", 390, 570);
        g.drawString("Depth", 20, 70);

        int maxDepth = 1;
        int maxSize = 1;

        for (Result result : results) {
            if (result.depth > maxDepth) {
                maxDepth = result.depth;
            }
            if (result.size > maxSize) {
                maxSize = result.size;
            }
        }

        String[] algorithms = {
                "MergeSort",
                "QuickSort",
                "DeterministicSelect",
                "ClosestPair"
        };

        Color[] colors = {
                Color.BLUE,
                Color.RED,
                Color.GREEN,
                Color.MAGENTA
        };

        for (int a = 0; a < algorithms.length; a++) {
            g.setColor(colors[a]);
            int previousX = -1;
            int previousY = -1;
            for (Result result : results) {
                if (!result.algorithm.equals(algorithms[a])) {
                    continue;
                }
                int x = left
                        + (int) (
                        (double) result.size
                                / maxSize
                                * graphWidth
                );
                int y = bottom
                        - (int) (
                        (double) result.depth
                                / maxDepth
                                * graphHeight
                );
                g.fillOval(x - 4, y - 4, 8, 8);
                if (previousX != -1) {
                    g.drawLine(
                            previousX,
                            previousY,
                            x,
                            y
                    );
                }
                previousX = x;
                previousY = y;
            }
        }

        drawLegend(g, algorithms, colors);
        g.dispose();
        try {
            ImageIO.write(
                    image,
                    "png",
                    new File("docs", "plots/depth_vs_n.png")
            );
        } catch (IOException e) {
            System.out.println("Could not save depth plot.");
        }
    }

    private static void drawLegend(
            Graphics2D g,
            String[] algorithms,
            Color[] colors
    ) {
        int x = 620;
        int y = 60;
        for (int i = 0; i < algorithms.length; i++) {
            g.setColor(colors[i]);
            g.fillRect(x, y + i * 20, 10, 10);
            g.setColor(Color.BLACK);
            g.drawString(
                    algorithms[i],
                    x + 15,
                    y + 10 + i * 20
            );
        }
    }

    private static class Result {
        String algorithm;
        int size;
        long time;
        int depth;
        Result(
                String algorithm,
                int size,
                long time,
                int depth
        ) {
            this.algorithm = algorithm;
            this.size = size;
            this.time = time;
            this.depth = depth;
        }
    }
}
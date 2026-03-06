package org.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

public class App extends Application {

    // Ticker symbol for the Dow Jones Industrial Average
    private static final String DJIA_TICKER = "^DJI";

    // Maximum number of entries in the queue
    private static final int MAX_QUEUE_SIZE = 100;

    // Poll interval in seconds
    private static final int POLL_INTERVAL_SECONDS = 30;

    // Queue to store stock price snapshots
    private static final Queue<StockEntry> stockQueue = new LinkedList<>();

    // Formatter for x-axis timestamps
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    // Chart data series
    private XYChart.Series<String, Number> series;

    // Status label
    private Text statusText;

    /**
     * Immutable record representing a single stock price snapshot.
     */
    static class StockEntry {
        private final BigDecimal price;
        private final LocalDateTime timestamp;

        public StockEntry(BigDecimal price, LocalDateTime timestamp) {
            this.price = price;
            this.timestamp = timestamp;
        }

        public BigDecimal getPrice() { return price; }
        public LocalDateTime getTimestamp() { return timestamp; }

        @Override
        public String toString() {
            return String.format("[%s]  DJIA = $%,.2f",
                    timestamp.format(FORMATTER), price);
        }
    }

    /**
     * Fetches the current DJIA price from Yahoo Finance,
     * stores it in the queue, and updates the chart.
     */
    private void fetchAndStore() {
        try {
            System.setProperty("http.agent", "Mozilla/5.0");
            Stock djia = YahooFinance.get(DJIA_TICKER);

            if (djia == null || djia.getQuote() == null) {
                Platform.runLater(() ->
                    statusText.setText("Status: Could not retrieve data."));
                return;
            }

            BigDecimal price = djia.getQuote().getPrice();
            LocalDateTime now = LocalDateTime.now();
            StockEntry entry = new StockEntry(price, now);

            // Evict oldest if at capacity
            if (stockQueue.size() >= MAX_QUEUE_SIZE) {
                stockQueue.poll();
            }
            stockQueue.add(entry);

            System.out.println("Stored: " + entry);

            // Update chart on JavaFX thread
            Platform.runLater(() -> updateChart());

        } catch (IOException e) {
            System.err.println("Error fetching stock data: " + e.getMessage());
            Platform.runLater(() ->
                statusText.setText("Status: Error — " + e.getMessage()));
        }
    }

    /**
     * Rebuilds the chart series from the current queue contents.
     */
    private void updateChart() {
        series.getData().clear();
        for (StockEntry entry : stockQueue) {
            String timeLabel = entry.getTimestamp().format(FORMATTER);
            series.getData().add(
                new XYChart.Data<>(timeLabel, entry.getPrice())
            );
        }

        // Update status text
        if (!stockQueue.isEmpty()) {
            StockEntry latest = ((LinkedList<StockEntry>) stockQueue).getLast();
            statusText.setText(
                String.format("Latest: $%,.2f  |  Queue: %d / %d entries  |  Last updated: %s",
                    latest.getPrice(),
                    stockQueue.size(),
                    MAX_QUEUE_SIZE,
                    latest.getTimestamp().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            );
        }
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Citi Stock Monitor — Dow Jones Industrial Average");

        // X axis — time labels
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Time");

        // Y axis — stock price
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("DJIA Price (USD)");
        yAxis.setForceZeroInRange(false);

        // Line chart
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Dow Jones Industrial Average — Live");
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(true);

        // Data series
        series = new XYChart.Series<>();
        series.setName("DJIA Price");
        lineChart.getData().add(series);

        // Status bar
        statusText = new Text("Status: Waiting for first data point...");
        statusText.setStyle("-fx-font-size: 13px;");

        // Layout
        VBox root = new VBox(10, lineChart, statusText);
        root.setStyle("-fx-padding: 15; -fx-background-color: #f4f6f9;");

        Scene scene = new Scene(root, 900, 550);
        stage.setScene(scene);
        stage.show();

        // Schedule fetch every POLL_INTERVAL_SECONDS using JavaFX Timeline
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, e -> fetchAndStore()),
            new KeyFrame(Duration.seconds(POLL_INTERVAL_SECONDS))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
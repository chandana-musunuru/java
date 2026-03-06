package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AppTest {
   /* 
    @Test
    void stockEntryStoresCorrectValues() {
        BigDecimal price = new BigDecimal("38500.75");
        LocalDateTime time = LocalDateTime.of(2024, 6, 1, 10, 30, 0);
        App.StockEntry entry = new App.StockEntry(price, time);

        assertEquals(price, entry.getPrice());
        assertEquals(time, entry.getTimestamp());
    }

    @Test
    void stockEntryToStringContainsDJIA() {
        App.StockEntry entry = new App.StockEntry(
                new BigDecimal("38500.75"),
                LocalDateTime.of(2024, 6, 1, 10, 30, 0)
        );
        assertTrue(entry.toString().contains("38,500.75"));
        assertTrue(entry.toString().contains("2024-06-01 10:30:00"));
    }
        */
       @Test
    void appClassExists() {
        App app = new App();
        assertNotNull(app);
    }
}
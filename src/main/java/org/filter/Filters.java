package org.filter;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;
import org.filter.interfaces.FilterRules;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Filters {

    private final Map<String, FilterRules> filters = new HashMap<>();

    public Filters() {
        // Вылет до текущего момента времени.
        filters.put("first_filter", (flights) -> flights.stream()
                .filter(e -> !e.getSegments().get(0).getDepartureDate().isBefore(LocalDateTime.now()))
                .toList()
        );

        // Вылеты без фильтрации
        filters.put("origin", (flights) -> flights);

        // Сегменты с датой прилёта раньше даты вылета
        filters.put("second_filter", (flights) -> flights.stream()
                .filter(e -> {
                    AtomicBoolean flag = new AtomicBoolean(true);
                    e.getSegments().forEach(seg -> {
                        if (seg.getArrivalDate().isBefore(seg.getDepartureDate())) flag.set(false);
                    });
                    return flag.get();
                })
                .toList()
        );

        // Сегменты с датой прилёта раньше даты вылета
        filters.put("third_filter", (flights) -> flights.stream()
                .filter(f -> {
                    List<Segment> seg = f.getSegments();
                    long diff = 0;
                    for (int i = 0; i < seg.size() - 1; i++)
                        diff += seg.get(i).getArrivalDate().until(seg.get(i + 1).getDepartureDate(), ChronoUnit.HOURS);
                    return !(diff > 2);
                }).toList()
        );
    }

    public List<Flight> filterFlights(String ruleName, List<Flight> originalListOfFlights) {
        if (filters.get(ruleName) == null) {
            throw new IllegalArgumentException("Фильтр " + ruleName + " не существует. Укажите корректное правило фильтрации");
        }
        return filters.get(ruleName).filter(originalListOfFlights);
    }
}

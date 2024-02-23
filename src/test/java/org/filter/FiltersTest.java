package org.filter;

import com.gridnine.testing.Flight;
import com.gridnine.testing.FlightBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltersTest {
    private final List<Flight> source = FlightBuilder.createFlights();
    private final Filters filters = new Filters();
    private final List<Flight> expected = new ArrayList<>(List.of(
            source.get(0),
            source.get(1)
    ));

    @Test
    @DisplayName("Вывод оригинального списка")
    void filterFlightsOriginTest() {
        // Тестируем вывод оригинального списка
        assertEquals(source, filters.filterFlights("origin", source));
    }

    @Test
    @DisplayName("Время вылета в прошлом")
    void filterFlightsFirstFilterTest() {
        // Тестируем перелеты с временем вылета до текущего момента времени
        List<Flight> incoming = new ArrayList<>(List.of(
                source.get(0),
                source.get(1),
                source.get(2)
        ));
        assertEquals(expected, filters.filterFlights("first_filter", incoming));
    }

    @Test
    @DisplayName("Прилёт раньше даты вылета")
    void filterFlightsSecondFilterTest() {
        // Тестируем фильтр полетов, где прилет раньше вылета
        List<Flight> incoming = new ArrayList<>(List.of(
                source.get(0),
                source.get(1),
                source.get(3)
        ));
        assertEquals(expected, filters.filterFlights("second_filter", incoming));
    }

    @Test
    @DisplayName("Промежутки больше 2 часов")
    void filterFlightsThirdFilterTest() {
        // Тестируем фильтр полетов, суммарное время между полетами больше 2 часов
        List<Flight> incoming = new ArrayList<>(List.of(
                source.get(0),
                source.get(1),
                source.get(4),
                source.get(5)
        ));
        assertEquals(expected, filters.filterFlights("third_filter", incoming));
    }

    @Test
    @DisplayName("Выбрасывается инсключение")
    void filterFlightsWrongFilterTest() {
        // Выбрасывается инсключение если задан не правильный фильтр
        assertThrows(IllegalArgumentException.class,
                () -> filters.filterFlights("wrong", source),
                "Фильтр wrong не существует. Укажите корректное правило фильтрации");
    }
}
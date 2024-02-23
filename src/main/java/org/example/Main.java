package org.example;

import com.gridnine.testing.Flight;
import com.gridnine.testing.FlightBuilder;
import org.filter.Filters;

import java.util.List;

public class Main {
    private static final Filters filters = new Filters();

    public static void main(String[] args) {

        System.out.println("Оригинальный список полетов");
        printFlights("origin", FlightBuilder.createFlights());

        System.out.println("\nУдалены перелеты с временем вылета до текущего момента времени.");
        printFlights("first_filter", FlightBuilder.createFlights());

        System.out.println("\nУдалены перелеты с датой прилёта раньше даты вылета");
        printFlights("second_filter", FlightBuilder.createFlights());

        System.out.println("\nУдалены перелеты где общее время, проведённое на земле, превышает два часа");
        printFlights("third_filter", FlightBuilder.createFlights());
    }

    private static void printFlights(String ruleName, List<Flight> flights) {
        filters.filterFlights(ruleName, flights)
                .forEach(e -> System.out.println(e.toString()));
    }
}
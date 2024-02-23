package org.filter.interfaces;

import com.gridnine.testing.Flight;

import java.util.List;

@FunctionalInterface
public interface FilterRules {
    List<Flight> filter(List<Flight> flights);
}

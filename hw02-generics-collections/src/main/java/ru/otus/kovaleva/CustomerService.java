package ru.otus.kovaleva;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

public class CustomerService {

    private final NavigableMap<Customer, String> customers;

    public CustomerService() {
        this.customers = new TreeMap<>(Comparator.comparingLong(c -> c.getScores()));
    }

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = customers.firstEntry();
        if (Objects.isNull(entry)) {
            return null;
        }
        Customer customer = new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores());
        return Map.entry(customer, entry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = customers.higherEntry(customer);
        if (Objects.isNull(entry)) {
            return null;
        }
        Customer cust = new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores());
        return Map.entry(cust, entry.getValue());
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}

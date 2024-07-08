/*
 * Copyright (C) 2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.grid;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.data.event.SortEvent;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.function.SerializableComparator;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

public class GridSortingTest {

    public static class Person {
        private int id;
        private String name;
        private int age;
        private Address address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof Person)) {
                return false;
            }
            Person other = (Person) obj;
            return id == other.id;
        }

        @Override
        public String toString() {
            return String.format("Person [name=%s, age=%s]", name, age);
        }
    }

    public static class Address {
        private String street;
        private int number;
        private String postalCode;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
    }

    private static class TestSortListener<T> implements
            ComponentEventListener<SortEvent<Grid<T>, GridSortOrder<T>>> {

        public List<SortEvent<Grid<T>, GridSortOrder<T>>> events = new ArrayList<>();

        @Override
        public void onComponentEvent(
                SortEvent<Grid<T>, GridSortOrder<T>> event) {
            events.add(event);
        }
    }

    private Grid<Person> grid;
    private Column<Person> nameColumn;
    private Column<Person> ageColumn;
    private Column<Person> templateColumn;

    private TestSortListener<Person> testSortListener;

    @Before
    public void init() {
        testSortListener = new TestSortListener<>();

        grid = new Grid<>();
        grid.setItems(new ArrayList<>());
        grid.addSortListener(testSortListener);

        nameColumn = grid.addColumn(Person::getName, "name").setHeader("Name");
        ageColumn = grid.addColumn(Person::getAge, "age").setHeader("Age");

        templateColumn = grid.addColumn(TemplateRenderer.<Person> of(
                "<div>[[item.street]], number [[item.number]]<br><small>[[item.postalCode]]</small></div>")
                .withProperty("street",
                        person -> person.getAddress().getStreet())
                .withProperty("number",
                        person -> person.getAddress().getNumber()),
                "street", "number").setHeader("Address");
    }

    @Test
    public void columnComparator_comaratorHandlesNullValues() {
        nameColumn.setComparator(Person::getName);

        Person person = new Person();
        person.setName("foo");

        SerializableComparator<Person> comparator = nameColumn
                .getComparator(SortDirection.ASCENDING);
        Assert.assertEquals(1, comparator.compare(new Person(), person));
    }

    @Test
    public void in_memory_sorting_applied_correctly() {
        setTestSorting();
        assertInMemorySorting(Comparator.comparing(Person::getName)
                .thenComparing(Comparator.comparing(Person::getAge).reversed())
                .thenComparing(person -> person.getAddress().getStreet())
                .thenComparing(person -> person.getAddress().getNumber()));
    }

    @Test
    public void backend_sorting_applied_correctly() {
        setTestSorting();
        assertSortOrdersEquals(
                QuerySortOrder.asc("name").thenDesc("age").thenAsc("street")
                        .thenAsc("number").build(),
                grid.getDataCommunicator().getBackEndSorting());
    }

    @Test
    public void sort_event_not_sent_for_same_data() {
        Assert.assertEquals("Sort event list should have been empty at start.",
                0, testSortListener.events.size());
        callSortersChanged(Json.createArray());
        Assert.assertEquals(
                "No sort event should have been fired for empty initial sort.",
                0, testSortListener.events.size());
        setTestSorting();
        Assert.assertEquals("Setting sort order should have fired event", 1,
                testSortListener.events.size());
        setTestSorting();
        Assert.assertEquals("No sort should have  fired for same sort order", 1,
                testSortListener.events.size());

    }

    @Test
    public void sort_event_correct() {
        Assert.assertEquals(0, testSortListener.events.size());
        setTestSorting();
        Assert.assertEquals(1, testSortListener.events.size());

        Assert.assertEquals(1, testSortListener.events.size());
        assertSortOrdersEquals(
                GridSortOrder.asc(nameColumn).thenDesc(ageColumn)
                        .thenAsc(templateColumn).build(),
                testSortListener.events.get(0).getSortOrder());
        Assert.assertTrue(testSortListener.events.get(0).isFromClient());
    }

    @Test
    public void changing_sorters() {
        setTestSorting();

        JsonArray secondSortersArray = Json.createArray();
        secondSortersArray.set(0,
                createSortObject(getColumnId(nameColumn), "desc"));
        callSortersChanged(secondSortersArray);

        Assert.assertEquals(2, testSortListener.events.size());

        assertSortOrdersEquals(GridSortOrder.desc(nameColumn).build(),
                testSortListener.events.get(1).getSortOrder());
    }

    @Test
    public void template_renderer_non_comparable_property() {
        Column<Person> column = grid.addColumn(TemplateRenderer.<Person> of("")
                .withProperty("address", Person::getAddress), "address");
        JsonArray sortersArray = Json.createArray();
        sortersArray.set(0, createSortObject(getColumnId(column), "asc"));
        callSortersChanged(sortersArray);

        // No in-memory sorting applied
        assertInMemorySorting((a, b) -> 0);
        // Backend sorting set correctly
        assertSortOrdersEquals(QuerySortOrder.asc("address").build(),
                grid.getDataCommunicator().getBackEndSorting());
    }

    private void setTestSorting() {
        JsonArray sortersArray = Json.createArray();
        sortersArray.set(0, createSortObject(getColumnId(nameColumn), "asc"));
        sortersArray.set(1, createSortObject(getColumnId(ageColumn), "desc"));
        sortersArray.set(2,
                createSortObject(getColumnId(templateColumn), "asc"));
        callSortersChanged(sortersArray);
    }

    private void callSortersChanged(JsonArray json) {
        try {
            Method method = Grid.class.getDeclaredMethod("sortersChanged",
                    JsonArray.class);
            method.setAccessible(true);
            method.invoke(grid, json);
        } catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            Assert.fail("Could not call Grid.sortersChanged");
        }
    }

    private String getColumnId(Column<?> column) {
        return column.getInternalId();
    }

    private JsonObject createSortObject(String columnId, String direction) {
        JsonObject json = Json.createObject();
        json.put("path", columnId);
        json.put("direction", direction);
        return json;
    }

    private <T, V extends SortOrder<T>> void assertSortOrdersEquals(List<V> o1,
            List<V> o2) {
        Assert.assertEquals(o1.size(), o2.size());
        for (int i = 0; i < o1.size(); ++i) {
            Assert.assertEquals(o1.get(i).getDirection(),
                    o2.get(i).getDirection());
            Assert.assertEquals(o1.get(i).getSorted(), o2.get(i).getSorted());
        }
    }

    private static List<Person> createItems() {
        Random random = new Random(0);
        return IntStream.range(1, 500)
                .mapToObj(index -> createPerson(index, random))
                .collect(Collectors.toList());
    }

    private static Person createPerson(int index, Random random) {
        Person person = new Person();
        person.setId(index);
        person.setName("Person " + index);
        person.setAge(13 + random.nextInt(50));

        Address address = new Address();
        address.setStreet("Street " + ((char) ('A' + random.nextInt(26))));
        address.setNumber(1 + random.nextInt(50));
        address.setPostalCode(String.valueOf(10000 + random.nextInt(8999)));
        person.setAddress(address);

        return person;
    }

    private void assertInMemorySorting(Comparator<Person> comparator) {
        List<Person> expectedOrder = createItems();
        List<Person> actualOrder = new ArrayList<>(expectedOrder);

        expectedOrder.sort(comparator);
        actualOrder.sort(grid.getDataCommunicator().getInMemorySorting());

        Assert.assertEquals(expectedOrder, actualOrder);
    }
}

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DirectoryOfCitiesTest {
    private final List<City> cityList = new ArrayList<>();
    private City city1;
    private City city2;
    private City city3;
    private City city4;
    private City city5;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setData(){
        city1 = new City("Адыгейск", "Адыгея", "Южный", 12248, "1973");
        city2 = new City("Майкоп", "Адыгея", "Южный", 144246, "1857");
        city3 = new City("Горно-Алтайск", "Алтай", "Сибирский", 56928, "1830");
        city4 = new City("Абаза", "Хакасия", "Сибирский", 17111, "1867");
        city5 = new City("Абакан", "Хакасия", "Сибирский", 165183, "1931");

        cityList.add(city1);
        cityList.add(city2);
        cityList.add(city3);
        cityList.add(city4);
        cityList.add(city5);
    }

    @Test
    public void readCities(){
        List<City> expected = DirectoryOfCities.readCities("testIn");
        Assert.assertEquals(expected, cityList);

    }

    @Test
    public void stringToObject() {
        City actual = new City("Абаза", "Хакасия", "Сибирский", 17111, "1867");
        City expected = DirectoryOfCities.stringToObject("4;Абаза;Хакасия;Сибирский;17111;1867");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void stringToObject_NULL(){
        City expected = DirectoryOfCities.stringToObject("4;Абаза;Хакасия;Сибирский;17111");

        Assert.assertNull(expected);
    }

    @Test
    public void sortForName() {
        List<City> actual = new ArrayList<>();
        actual.add(city4);
        actual.add(city5);
        actual.add(city1);
        actual.add(city3);
        actual.add(city2);

        DirectoryOfCities.sortForName(cityList);

        Assert.assertEquals(cityList, actual);
    }

    @Test
    public void sortForDistrict() {
        List<City> actual = new ArrayList<>();
        actual.add(city4);
        actual.add(city5);
        actual.add(city3);
        actual.add(city1);
        actual.add(city2);

        DirectoryOfCities.sortForDistrict(cityList);

        Assert.assertEquals(cityList, actual);
    }

    @Test
    public void maxPopulation() {
        int[] actual = new int[] {4, 165183};
        int[] expected = DirectoryOfCities.maxPopulation(cityList);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void numCityInRegion() {
        Map<String, Long> actual = new HashMap<>();
        actual.put("Адыгея", 2L);
        actual.put("Алтай", 1L);
        actual.put("Хакасия", 2L);
        Map<String, Long> expected = DirectoryOfCities.numCityInRegion(cityList);

        assertEquals(expected, actual);
    }
}
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DirectoryOfCities {

    public static void main(String[] args){

        String fileName = "in";
        List<City> cityList = readCities(fileName);

        System.out.println("Исходный список");
        printCity(cityList);

        System.out.println("Выберите действие:");
        System.out.println("1) Вывести список городов");
        System.out.println("2) Сортировка городов по наименованию в алфавитном порядке по убыванию без учета регистра");
        System.out.println("3) Сортировка списка городов по федеральному округу и наименованию города внутри каждого федерального округа в алфавитном порядке по убыванию с учетом регистра");
        System.out.println("4) Поиск города с наибольшим колисечтвом жителей");
        System.out.println("5) Определение количества городов в разрезе регионов");
        System.out.println("6) Выход");

        Scanner scanner1 = new Scanner(System.in);
        while (scanner1.hasNextLine()){
            String option = scanner1.nextLine();
            switch (option){
                case "1":
                    printCity(cityList);
                    break;
                case "2":
                    sortForName(cityList);
                    break;
                case "3":
                    sortForDistrict(cityList);
                    break;
                case "4":
                    maxPopulation(cityList);
                    break;
                case "5":
                    numCityInRegion(cityList);
                    break;
                case "6":
                    scanner1.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Данной операции не существует");
            }
        }

        scanner1.close();
    }

    public static List<City> readCities(String fileName) {
        List<City> result = new ArrayList<>();
        Path path = Paths.get(fileName);
        try(Scanner scanner = new Scanner(path)){
            while (scanner.hasNextLine()){
                City city = stringToObject(scanner.nextLine());
                if (city != null){
                    result.add(city);
                }
            }
            if(result.size() == 0){
                throw new Exception("Файл пуст!");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static City stringToObject(String inputString){
        String[] parsString = inputString.split(";");
        if(parsString.length == 6) {
            int population;
            try {
                population = Integer.parseInt(parsString[4]);
            }catch (NumberFormatException e){
                population = 0;
                System.out.println(e.getMessage());
            }
            City city = new City();
            city.setName(parsString[1]);
            city.setRegion(parsString[2]);
            city.setDistrict(parsString[3]);
            city.setPopulation(population);
            city.setFoundation(parsString[5]);
            return city;
        } else{
            return null;
        }
    }

    public static void printCity(List<City> cityList1){
        for(City city : cityList1){
            System.out.println(city);
        }
    }

    public static void sortForName(List<City> cityList1){
        Comparator<City> nameComparator = new NameComparator();
        cityList1.sort(nameComparator);
        System.out.println();
        System.out.println("Сортировка по названию");
        printCity(cityList1);
    }

    public static void sortForDistrict(List<City> cityList1){
        Comparator<City> districtComparator = new DistrictComparator();
        cityList1.sort(districtComparator);
        System.out.println();
        System.out.println("Сортировка по дистрикту и названию");
        printCity(cityList1);
    }

    public static int[] maxPopulation(List<City> cityList1){
        //преобразуем список в массив
        City[] cities = new City[cityList1.size()];

        for(int i = 0; i < cityList1.size(); i++){
            cities[i] = cityList1.get(i);
        }

        //находим максимум в массиве
        int maxPopulation = cities[0].getPopulation();
        int maxIndex = 0;
        for(int i = 1; i < cities.length; i++){
            if(maxPopulation < cities[i].getPopulation()){
                maxPopulation = cities[i].getPopulation();
                maxIndex = i;
            }
        }

        //выводим индекс и значение максимума
        String output = String.format("[%d] = %d", maxIndex, maxPopulation);
        System.out.println(output);
        return new int[]{maxIndex, maxPopulation};
    }

    public static Map<String, Long> numCityInRegion(List<City> cityList1){
        Map<String, Long> map = cityList1.stream()
                .collect(Collectors.groupingBy(City::getRegion, Collectors.counting()));

        for(Map.Entry<String, Long> entry : map.entrySet()){
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        return map;
    }
}
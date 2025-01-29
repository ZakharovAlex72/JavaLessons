package Task2;

import javax.swing.text.Position;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static class Person{
        enum Position{
            BOSS, WORKER, ENGINEER
        }
        private final String name;
        private final int age;
        private final Position position;

        public Person(String name, int age, Position position){
            this.name=name;
            this.age=age;
            this.position=position;
        }

        public int getAge() { return age; }
    }

    private static void streamEngineer(){
        List<Person> persons = new ArrayList<>(Arrays.asList(
                new Person("Name1",33, Person.Position.WORKER),
                new Person("Name2",44, Person.Position.BOSS),
                new Person("Name3",55, Person.Position.WORKER),
                new Person("Name4",43, Person.Position.ENGINEER),
                new Person("Name5",34, Person.Position.ENGINEER),
                new Person("Name6",35, Person.Position.ENGINEER),
                new Person("Name7",22, Person.Position.WORKER),
                new Person("Name8",42, Person.Position.ENGINEER)
        ));

        List<String> nameEngineer = persons.stream()
                .filter(p -> p.position == Person.Position.ENGINEER)
                .sorted(Comparator.comparing(Person::getAge).reversed())
                .limit(3)
                .map(p -> p.name)
                .toList();
        System.out.println(nameEngineer);

        Map<Person.Position, Double> avgAge = persons.stream()
                .filter(p -> p.position == Person.Position.ENGINEER)
                .collect(
                        Collectors.groupingBy(p->p.position, Collectors.averagingDouble(p-> p.age))
                )
                ;
        System.out.println(avgAge);



    }


    public static void main (String[] args){
        /*
        Stream.of( 5, 2, 10, 9, 4, 3, 10, 1, 13)
                .distinct()
                .forEach(System.out::println);

        System.out.println("--------------");
        Stream.of( 5, 2, 10, 9, 4, 3, 10, 1, 13)
                .sorted(Collections.reverseOrder())
                .skip(2)
                .limit(1)
                .forEach(System.out::println);

        System.out.println("--------------");
        Stream.of( 5, 2, 10, 9, 4, 3, 10, 1, 13)
                .distinct()
                .sorted(Collections.reverseOrder())
                .skip(2)
                .limit(1)
                .forEach(System.out::println);

        System.out.println("--------------");
*/
        streamEngineer();
    }
}

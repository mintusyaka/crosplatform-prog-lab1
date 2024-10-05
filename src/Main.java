import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Student student = new Student(
                "John",
                "Snake",
                "Hostel #3",
                318,
                800.00,
                18
        );

        List<Student> students = new LinkedList<Student>();
        {
            students.add(null);
            students.add(new Student(
                            "Peter",
                            "Snake",
                            "Hostel #1",
                            101,
                            850.00,
                            20
                    )
            );
            students.add(new Student(
                            "John",
                            "Snake",
                            "Hostel #3",
                            318,
                            800.00,
                            18
                    )
            );
            students.add(new Student(
                            "Olexander",
                            "Usyk",
                            "Hostel #7",
                            330,
                            800.00,
                            19
                    )
            );
            students.add(new Student(
                            "Vasyl",
                            "Baydak",
                            "Hostel #1",
                            421,
                            850.00,
                            17
                    )
            );
            students.add(new Student(
                            "Oleg",
                            "Klymenko",
                            "Hostel #1",
                            421,
                            850.00,
                            17
                    )
            );
        }

        // generate students

        students.get(2).addPrivilege("КВ1");
        students.get(1).addPrivilege("КВ1");
        students.get(1).addPrivilege("КВ2");

        System.out.println("Not Privileged Students:");

        for(Student s : StudentAccountingWithStream.whoAreNotPrivileged(students)) {
            if(s != null)
                System.out.println("\t\t" + s.getLastname() + " " + s.getFirstname());
        }
        System.out.println();

        System.out.println("Privileged Students:");
        for(Student s : StudentAccounting.whoArePrivileged(students)) {
            if(s != null)
                System.out.println("\t\t" + s.getLastname() + " " + s.getFirstname() + s.getPrivileges());
        }
        System.out.println();

        System.out.println("Students grouped by hostels:");
        Map<String, Set<Student>> mp = StudentAccounting.groupByHostel(students);
        for(Map.Entry<String, Set<Student>> hostel : mp.entrySet()) {
            System.out.println(hostel.getKey() + ": ");
            for(Student s : hostel.getValue()) {
                if(s != null)
                    System.out.println("\t\t" +s.getLastname() + " " + s.getFirstname());
            }
        }
        System.out.println();

        System.out.println("Count of students in the room:");
        for(Map.Entry<String, Set<Student>> hostel : mp.entrySet()) {
            System.out.println(hostel.getKey() + ": ");

            Map<Integer, Integer> mp_rooms = StudentAccounting.getRooms(students, hostel.getKey());
            for(Map.Entry<Integer, Integer> room : mp_rooms.entrySet()) {
                System.out.println("\t\t" + room.getKey() + " - " + room.getValue());
            }
        }
        System.out.println();

        System.out.println("Students sorted by age:");
        for(Student s : StudentAccountingWithStream.sortByAge(students)) {
            if(s != null)
                System.out.println("\t\t" + s.getLastname() + ": " + s.getAge() + " " + s.getPrivileges());
        }
        System.out.println();

        System.out.println("Students sorted by privileges (descending):");
        for(Student s : StudentAccountingWithStream.sortByPrivilegesCount(students)) {
            if(s != null)
                System.out.println("\t\t" + s.getLastname() + ": " + s.getAge() + " " + s.getPrivileges());
        }
        System.out.println();

        System.out.println("Unique rooms:");
        for(Integer room : StudentAccounting.getUniqueRooms(students)) {
            System.out.println(room);
        }
        System.out.println();

        System.out.print("Student who pays the most - ");
        Optional<Student> st = StudentAccounting.whoPaysTheMost(students);
        if(st.isPresent())
            System.out.println(st.get().getLastname() + ": " + st.get().getHostel() + " " + st.get().getRoom() + " " + st.get().getPrice());
        else
            System.out.println("No student found");

    }

    public static class StudentAccountingWithStream {
        public static List<Student> whoAreNotPrivileged(List<Student> students)
        {
            return Optional
                    .ofNullable(students)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(st -> !st.havePrivileges())
                    .collect(Collectors.toList());
        }

        public static List<Student> whoArePrivileged(List<Student> students)
        {
            return Optional
                    .ofNullable(students)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(st -> st.havePrivileges())
                    .collect(Collectors.toList());
        }

        public static Map<String, Set<Student>> groupByHostel(List<Student> students)
        {
            return Optional
                    .ofNullable(students)
                    .orElse(Collections.emptyList())
                    .stream()
                    .collect(Collectors.toMap(
                            st -> st.getHostel(),
                            st -> new HashSet<>(Collections.singleton(st)),
                            (existingSet, newSet) -> {
                                existingSet.addAll(newSet);
                                return existingSet;
                            }
                    ));
        }

        public static Map<Integer, Integer> getRooms(List<Student> students, String hostel)
        {
            return Optional
                    .ofNullable(students)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(st -> st.getHostel().equals(hostel))
                    .collect(Collectors.toMap(
                            st -> st.getRoom(),
                            st -> new Integer(1),
                            Integer::sum
                    ));
        }

        public static List<Student> sortByAge(List<Student> students)
        {
            return Optional
                    .ofNullable(students)
                    .orElse(Collections.emptyList())
                    .stream()
                    .sorted(Comparator.nullsFirst(Comparator.comparing(Student::getAge)))
                    .collect(Collectors.toList());
        }

        public static List<Student> sortByPrivilegesCount(List<Student> students)
        {
            return Optional
                    .ofNullable(students)
                    .orElse(Collections.emptyList())
                    .stream()
                    .sorted(Comparator.nullsFirst(Comparator.comparing(Student::howManyPrivileges)).reversed())
                    .collect(Collectors.toList());
        }

        public static List<Integer> getUniqueRooms(List<Student> students)
        {
            List<Integer> rooms = Optional
                    .ofNullable(students)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(Student::getRoom)
                    .collect(Collectors.toList());

            for(int i = 0; i < rooms.size(); i++)
            {
                final int idx = i;
                if(rooms.stream()
                        .filter(r -> r.equals(rooms.get(idx)))
                        .count() > 1
                )
                {
                    rooms.removeAll(rooms.stream()
                            .filter(r -> r.equals(rooms.get(idx)))
                            .collect(Collectors.toList())
                    );
                    --i;
                }
            }

            return rooms;
        }

        @org.jetbrains.annotations.NotNull
        public static Optional<Student> whoPaysTheMost(List<Student> students)
        {
            return Optional
                    .ofNullable(students)
                    .orElse(Collections.emptyList())
                    .stream()
                    .max(Comparator.comparing(st -> st.getPrice()));
        }
    }

    public static class StudentAccounting {
        public static List<Student> whoAreNotPrivileged(List<Student> students)
        {
            List<Student> resultList = new LinkedList<Student>();

            for(Student s : students)
            {
                if(s != null && !s.havePrivileges())
                    resultList.add(s);
            }
            return resultList;
        }

        public static List<Student> whoArePrivileged(List<Student> students)
        {
            List<Student> resultList = new LinkedList<Student>();

            for(Student s : students)
            {
                if(s != null && s.havePrivileges())
                    resultList.add(s);
            }
            return resultList;
        }

        public static Map<String, Set<Student>> groupByHostel(List<Student> students)
        {
            Map<String, Set<Student>> mp = new HashMap<>();
            for(Student s : students)
            {
                if(s != null) {
                    if(mp.containsKey(s.getHostel()))
                    {
                        mp.get(s.getHostel()).add(s);
                    }
                    else {
                        mp.put(s.getHostel(), new HashSet<>(Collections.singleton(s)));
                    }
                }
            }

            return mp;
        }

        public static Map<Integer, Integer> getRooms(List<Student> students, String hostel)
        {
            Map<Integer, Integer> rooms = new HashMap<>();

            for(Student s : students)
            {
                if(s!=null) {
                    if(s.getHostel().equals(hostel))
                    {
                        if(rooms.containsKey(s.getRoom()))
                        {
                            rooms.put(s.getRoom(), rooms.get(s.getRoom()) + 1);
                        }
                        else {
                            rooms.put(s.getRoom(), 1);
                        }
                    }
                }
            }

            return rooms;
        }

        public static List<Student> sortByAge(List<Student> students)
        {
            List<Student> resultList = new LinkedList<>(students);
            Collections.sort(resultList);
            return resultList;
        }

        public static List<Student> sortByPrivilegesCount(List<Student> students)
        {
            List<Student> resultList = new LinkedList<>(students);
            resultList.sort(Comparator.comparing(Student::howManyPrivileges).reversed());
            return resultList;
        }

        public static List<Integer> getUniqueRooms(List<Student> students)
        {
            List<Integer> resultList = new LinkedList<Integer>();

            for(Student s : students)
            {
                if(s != null) {
                    if (resultList.contains(s.getRoom())) {
                        resultList.remove((Integer) s.getRoom());
                    } else {
                        resultList.add(s.getRoom());
                    }
                }
            }

            return resultList;
        }

        @org.jetbrains.annotations.NotNull
        public static Optional<Student> whoPaysTheMost(List<Student> students)
        {
            if(students.isEmpty())
                return Optional.empty();

            Student maxSt = new Student(
                    "",
                    "",
                    "",
                    0,
                    0.0,
                    0

            );

            for(Student s : students)
            {
                if(s != null && s.getPrice() > maxSt.getPrice())
                {
                    maxSt = s;
                }
            }

            return Optional.of(maxSt);
        }
    }
}
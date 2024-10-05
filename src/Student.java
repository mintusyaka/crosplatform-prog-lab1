import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

public class Student implements Comparable<Student>
{
    private String firstname;
    private String lastname;
    private String hostel;
    private int roomNumber;
    private double price;
    private Integer age;
    private Set<String> privileges;

    public Student(
            String firstname,
            String lastname,
            String hostel,
            int roomNumber,
            double price,
            Integer age
    )
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.hostel = hostel;
        this.roomNumber = roomNumber;
        this.price = price;
        this.age = age;
        this.privileges = new HashSet<String>();
    }

    public boolean havePrivileges()
    {
        return !privileges.isEmpty();
    }

    public int howManyPrivileges()
    {
        if(havePrivileges())
            return privileges.size();
        return 0;
    }

    public boolean addPrivilege(String privilege)
    {
        return privileges.add(privilege);
    }

    public String getFirstname() {return firstname;}
    public String getLastname() {return lastname;}
    public String getHostel() {return hostel;}
    public int getRoom()
    {
        return roomNumber;
    }
    public double getPrice() {return price;}
    public Integer getAge()
    {
        return age;
    }
    public Set<String> getPrivileges() {return privileges;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return roomNumber == student.roomNumber && Double.compare(price, student.price) == 0 && Objects.equals(firstname, student.firstname) && Objects.equals(lastname, student.lastname) && Objects.equals(hostel, student.hostel) && Objects.equals(age, student.age) && Objects.equals(privileges, student.privileges);
    }



    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, hostel, roomNumber, price, age, privileges);
    }

    @Override
    public int compareTo(@NotNull Student o) {
        return age.compareTo(o.age);
    }
}
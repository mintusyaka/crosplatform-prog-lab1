import java.util.Collection;
import java.util.HashSet;

public class Student
{
    private String firstname;
    private String lastname;
    private String hostel;
    private int roomNumber;
    private double price;
    private int age;
    private Collection<String> privileges;

    public Student(
            String firstname,
            String lastname,
            String hostel,
            int roomNumber,
            double price,
            int age
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
    public int getAge()
    {
        return age;
    }
    public Collection<String> getPrivileges() {return privileges;}
}
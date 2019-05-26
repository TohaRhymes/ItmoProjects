import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class TimeTraveller implements  Comparable<TimeTraveller>, Serializable {

    private String name;
    private OurDate birthdayDate;
    private OurDate locDate;
    private double x;
    private double y;
    private long age;
    private String owner;

    public TimeTraveller(String name, double x, double y, String login) {
        this.name = name;
        birthdayDate = new OurDate();
        locDate = new OurDate();
        this.x = x;
        this.y = y;
        age = locDate.getAge(birthdayDate);
        owner = login;
    }

    @Override
    public String toString() {
        return name + "," + birthdayDate.DatetoString() + "," + locDate.DatetoString() + "," + x + "," + y + "," + age + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj == null)
            return false;
        if(!(getClass() == obj.getClass()))
            return false;
        else{
            TimeTraveller t = (TimeTraveller) obj;
            if(t.getName().equals(this.getName()))
                return true;
            else
                return false;
        }
    }

    @Override
    public int compareTo(TimeTraveller traveller) {
        int result;
        result = (int) (Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2)) - (Math.sqrt(Math.pow(traveller.getX(), 2) + Math.pow(traveller.getY(), 2))));
        if (result == 0)
            result = this.name.compareTo((traveller.name));
        return result;
    }
}
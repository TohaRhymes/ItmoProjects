import java.time.OffsetDateTime;

public class OurDate {

    public OffsetDateTime date;

    public OurDate() {
        date = OffsetDateTime.now();
    }

    public OurDate(String stringToDate) {
        try {
            //кроме даты, нужно время, поэтому я заполняю рандомное время, просто чтобы оно было.
            String s = stringToDate.concat("T10:15:30+01:00");
            date = OffsetDateTime.parse(s);
        }catch(Exception ee){
            throw new IllegalArgumentException("Неправильный формат ввода даты. Используйте yyyy-MM-dd.");
        }
    }

    public int getDay() {
        return this.date.getDayOfYear();
    }

    public int getYear() {
        return this.date.getYear();
    }

    public int getAge(OurDate birth) {
        if (this.getDay() >= birth.getDay()) return this.getYear() - birth.getYear();
        return this.getYear() - birth.getYear() - 1;
    }

    public String DatetoString() {
        try {return "" + date.toString().substring(0, 10);
        } catch(Exception ee){
            return null;
        }
    }
}

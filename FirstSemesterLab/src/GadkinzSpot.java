
import java.util.Date;
import java.util.Vector;
import java.util.Collections;
import java.util.Iterator;

/**
 * SuperLab
 *
 * @shit false
 * @author Anton Changalidi
 * @version 0.9
 */
public class GadkinzSpot {
    //Initialization of Vector

    /**
     *Date type Varyable, that stores date of Class object creation
     */
    private Date initializationDate;

    /**
     *Collection with Vector type, that stores Gadkinz elements
     */
    private Vector<Gadkinz> gees;

    public GadkinzSpot() {
        this.initializationDate = new Date();
        gees = new Vector<>();
    }

    //Methods

    /**
     * Method adds new element, which type is Gadkinz.
     * @param name (String) - the element's name.
     * @param money (int) - amount of money, that this element owns.
     * @param shares (int) - amount of shares, that this element owns.
     */
    public void add(String name, int money, int shares) {
        gees.add(new Gadkinz(name, money, shares));
        System.out.println("The object has added.");
    }

    /**
     * Method adds new element, which type is Gadkinz.
     * @param gadkinz (Gadkinz) - object of class Gadkinz, that are added
     */
    public void put(Gadkinz gadkinz) {
        gees.add(gadkinz);
    }

    /**
     * Method shows all elements in String representation.
     */
    public void show() {
        for (Gadkinz g : gees) {
            System.out.println(g.toString());
        }
    }

    /**
     * Method return Vector<Gadkinz>.
     */
    public Vector<Gadkinz> getGadkinzes() {
        return gees;
    }

    /**
     * Method removes all collection's elements.
     */
    public void clear() {
        gees.clear();
        System.out.println("The Collection has been cleaned.");
    }

    /**
     * Method shows informormation about collection (type, date of initialization, number of elements).
     */
    public void info() {
        System.out.println("The Collection has type Vector and contains Gadkinz objects.");
        System.out.println("Its date of initialization is " + initializationDate);
        if (gees.size() > 1) System.out.println("It contains " + gees.size() + " elements now.");
        else if (gees.size() == 1) System.out.println("It contains " + gees.size() + " element now.");
        else System.out.println("It does not contain elements now.");
    }

    /**
     * Method removes first collection's element, which are the same with argument gadkinzForDelete.
     *
     * @param gadkinzForDelete (Gadkinz) - object of class Gadkinz.
     */
    public void remove(Gadkinz gadkinzForDelete) {
        if (gees.remove(gadkinzForDelete)) System.out.println("The element has been deleted.");
        else System.out.println("The collection does not have this object.");
    }

    /**
     * Method removes all collection's elements, which are the same with argument gadkinzForDelete.
     *
     * @param gadkinzForDelete (Gadkinz) - object of class Gadkinz.
     */
    public void removeAll(Gadkinz gadkinzForDelete) {
        int count = 0;

        while (gees.remove(gadkinzForDelete)) {
            count++;
        }
        if (count > 1) System.out.println(count + " objects have been deleted.");
        else if (count == 1) System.out.println(count + " object has been deleted.");
        else System.out.println("The collection does not have this object.");
    }

    /**
     * Method removes collection's elements, which are greater than argument gadkinzForCompare.
     *
     * @param gadkinzForCompare (Gadkinz) - object of class Gadkinz.
     */
    public void removeGreater(Gadkinz gadkinzForCompare) {
        Iterator<Gadkinz> iterator = gees.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Gadkinz anotherGadkinz = iterator.next();
            if (gadkinzForCompare.compareTo(anotherGadkinz) < 0) {
                iterator.remove();
                count++;
            }
        }
        if (count > 1) System.out.println(count + " objects have been deleted.");
        else if (count == 1) System.out.println(count + " object has been deleted.");
        else System.out.println("The collection does not have greater objects");
    }

    /**
     * Method sorts collection's elements in increasing order (firstly - by increasing of shares' number, secondly - by increasing of money, finally - by name).
     */
    public void sort() {
        Collections.sort(gees);
    }

}

/**
 * @author Anton Changalidi
 */
public class EnvNameException extends Exception {
    public EnvNameException(){
        super();
    }

    @Override
    public String getMessage() {
        return String.format("Environment variable LabInput wasn't found.");
    }
}

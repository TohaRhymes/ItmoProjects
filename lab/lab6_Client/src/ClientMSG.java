import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@RequiredArgsConstructor
public class ClientMSG {

    @Setter
    @NonNull
    private String command;

    @Setter
    @NonNull
    private ArrayList<String> args;

    private String cookie;

    {
        cookie = MainClient.getCookie();
    }

    public ClientMSG(String command) {
        this.command = command;
    }

}

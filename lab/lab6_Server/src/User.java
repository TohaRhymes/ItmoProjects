import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;

@Getter
@Setter
@AllArgsConstructor
public class User {

	private InetAddress add;
	private int port;

}

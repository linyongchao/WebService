package ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class WSTest {
	@WebMethod
	public void hello() {
		System.out.println("hello");
	}

	@WebMethod
	public int add(int a, int b) {
		System.out.println(a + b);
		return a + b;
	}
}
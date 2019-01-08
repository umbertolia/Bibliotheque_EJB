
import org.junit.Test;

import junit.framework.TestCase;
import utils.BiblioUtil;

public class BiblioTest extends TestCase{


	
	@Test
	public void testEnumAction() {
		
		try {
		// test sur action qui existe
			String action1 = "modifier";
			assertNotNull(BiblioUtil.recupActionEnum(action1));
			
			String action2 = "jfkdsl";
			BiblioUtil.recupActionEnum(action2);
		}
		catch (Exception exception) {
			System.out.println(exception.getMessage());
			fail(exception.getMessage());
		}
	
			
		
	}
;
}

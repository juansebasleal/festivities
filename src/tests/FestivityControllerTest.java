/**
 * TODO Testing the controller
 * This still does not work
 */

package tests;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import main.java.festivity.FestivityController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
public class FestivityControllerTest {
	
	private MockMvc mockMvc;
	@Autowired
    private FestivityController festivityControllerMock;

	@Test
	public void testAll() throws Exception {
		
		//ArrayList<Festivity> festivitiesList = new ArrayList<Festivity>(); 
    	//festivitiesList.add(new Festivity("Navidad", new Date(), new Date()));
    	//festivitiesList.add(new Festivity("Dia del perro", new Date(), new Date()));
		
		//[{"name":"Navidad","from":1458061236806,"to":1458061236806},{"name":"Dia del perro","from":1458061236806,"to":1458061236806}]
		
		
		mockMvc.perform(get("/api/todo"))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	        .andExpect(jsonPath("$", hasSize(2)))
	        .andExpect(jsonPath("$[0].id", is(1)))
	        .andExpect(jsonPath("$[0].description", is("Lorem ipsum")))
	        .andExpect(jsonPath("$[0].title", is("Foo")))
	        .andExpect(jsonPath("$[1].id", is(2)))
	        .andExpect(jsonPath("$[1].description", is("Lorem ipsum")))
	        .andExpect(jsonPath("$[1].title", is("Bar")));
		
		
		verify(festivityControllerMock, times(1)).all();
        verifyNoMoreInteractions(festivityControllerMock);
		
		//assertEquals("a", "a");
	}
}

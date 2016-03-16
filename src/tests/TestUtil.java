/**
 * TODO This class is still unusefull as FestivityControllerTest does not work yet.
 */


//package tests;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;

public class TestUtil {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),                        
            Charset.forName("utf8")                     
            );
}

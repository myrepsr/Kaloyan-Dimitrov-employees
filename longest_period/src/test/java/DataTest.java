import com.assignment.backend.CustomFileReader;
import com.assignment.backend.DataRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DataTest {

    private CustomFileReader customFileReader;
    private List<DataRecord> recordList = new ArrayList<>();

    @Test
    public void supportAllDateFormatsTest(){
        try {
            customFileReader = CustomFileReader.getInstance("src/Data format4.csv");
            recordList = customFileReader.readFile();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

package kmu.tp.newscheduler.test;

import org.junit.Test;
import java.util.regex.Pattern;
import org.junit.Assert.assertFalse;
import org.junit.Assert.assertTrue;
import kmu.tp.newscheduler.MainActivity;
import kmu.tp.newscheduler.AddSchedule;

public class AddScheduleTest extends ActivityInstrumentationTestCase2<AddSchedule> {

   public AddScheduleTest() {
      super(AddSchedule.class);
   }
   
   @Test
   public void getDiffTimeTest() {
         AddSchedule diff = new AddSchedule();
         Date testSDate = new Date(201512120210);
         Date testEDate = new Date(201512120215);
         Assert.assetTrue(0 < diff.getDiffTimeTest(testSDate,testEDate)); 
         Assert.assetTrue(0 > diff.getDiffTimeTest(testEDate,testSDate)); 
    }
}
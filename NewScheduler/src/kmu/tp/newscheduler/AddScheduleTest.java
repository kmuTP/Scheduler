package kmu.tp.newscheduler;

import java.math.BigInteger;
import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;

public class AddScheduleTest extends ActivityInstrumentationTestCase2<AddSchedule> {

   public AddScheduleTest() {
      super(AddSchedule.class);
   }
   
   @Test
   public void getDiffTimeTest() {
         AddSchedule diff = new AddSchedule();
         BigInteger sLong = new BigInteger("201512120210");
         BigInteger eLong = new BigInteger("201512120215");
         Date testSDate = new Date(sLong.longValue());
         Date testEDate = new Date(eLong.longValue());
         Assert.assertTrue(0 < diff.getDiffTime(testSDate,testEDate)); 
         Assert.assertTrue(0 > diff.getDiffTime(testEDate,testSDate)); 
    }
   

}
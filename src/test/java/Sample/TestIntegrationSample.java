package Sample;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestIntegrationSample {

    @Test(groups = "qa", priority = 1)
    @Owner("Shilpa")
    @Description("Verify that the booking can be created")
    public void testCreateBooking(){
        Assert.assertTrue(true);
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Shilpa")
    @Description("Verify  the booking ID ")
    public void testVerifyBookingID(){
        Assert.assertTrue(true);
    }

    @Test(groups = "qa", priority = 3)
    @Owner("Shilpa")
    @Description("Verify Update booking details")
    public void testUpdateBookingByID(){
        Assert.assertTrue(true);
    }

    @Test(groups = "qa", priority = 4)
    @Owner("Shilpa")
    @Description("Verify Delete booking ")
    public void testDeleteBookingById(){
        Assert.assertTrue(true);
    }

}

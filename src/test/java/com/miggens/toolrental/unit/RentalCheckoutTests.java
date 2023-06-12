package com.miggens.toolrental.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*; 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@AutoConfigureMockMvc
public class RentalCheckoutTests {
    
    @Autowired
    MockMvc mvc; 

    @Test
    public void testZeroDayRental_andExpectBadRequest() throws Exception {
        String url = this.urlBuilder("05-06-2023", "JAKR", "0", "0");
        mvc.perform( post(url) )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Atleast one request argument is invalid: checkout.dayCount: must be greater than or equal to 1"));
            
    }

    /*
     *  Test 1
        JAKR 
        9/3/15
        days 5 
        Discount 101%
    */ 
    
    @Test
    public void testOver100PercentDiscount_andExpectBadRequest() throws Exception {
        String url = this.urlBuilder("09-03-2015", "JAKR", "5", "101");
        mvc.perform( post(url) )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Atleast one request argument is invalid: checkout.discount: must be less than or equal to 100"));
    }

    /*
     * Test 2
        LADW 
        7/2/20 
        3
        10%
     */
    @Test
    
    public void testFourthOfJuly2020() throws Exception{
        String url = this.urlBuilder("02-07-2020", "LADW", "3", "10");
        mvc.perform( post(url) )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.chargableDaysCount", is(2)))
                .andExpect(jsonPath("$.finalCheckoutCharge", is(358)))
                .andExpect(jsonPath("$.returnDate", is("2020-07-04")));
    }

    /*
     * Test 2
        LADW 
        7/2/20 
        3
        10%
     */
    @Test
    public void testFourthOfJuly2015Chainsaw() throws Exception{
        String url = this.urlBuilder("02-07-2015", "CHNS", "5", "25");
        mvc.perform( post(url) )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.chargableDaysCount", is(3)))
                .andExpect(jsonPath("$.finalCheckoutCharge", is(335)))
                .andExpect(jsonPath("$.returnDate", is("2015-07-06")));
                //.andExpect(content().string("Atleast one request argument is invalid: checkout.discount: must be less than or equal to 100"));
    }

    @Test
    public void testLaborDay2015DewaltJackHammer() throws Exception{
        String url = this.urlBuilder("03-09-2015", "JAKD", "6", "0");
        mvc.perform( post(url) )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.chargableDaysCount", is(3)))
                .andExpect(jsonPath("$.finalCheckoutCharge", is(897)))
                .andExpect(jsonPath("$.returnDate", is("2015-09-08")));
                //.andExpect(content().string("Atleast one request argument is invalid: checkout.discount: must be less than or equal to 100"));
    }

    @Test
    public void testLaborDay2015RigidJackHammer() throws Exception{
        String url = this.urlBuilder("02-07-2015", "JAKR", "9", "0");
        mvc.perform( post(url) )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.chargableDaysCount", is(6)))
                .andExpect(jsonPath("$.finalCheckoutCharge", is(1794)))
                .andExpect(jsonPath("$.returnDate", is("2015-07-10")));
                //.andExpect(content().string("Atleast one request argument is invalid: checkout.discount: must be less than or equal to 100"));
    }

    @Test
    public void testFourthOfJuly2020RigidJackHammer() throws Exception{
        String url = this.urlBuilder("02-07-2020", "JAKR", "4", "50");
        mvc.perform( post(url) )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.chargableDaysCount", is(1)))
                .andExpect(jsonPath("$.finalCheckoutCharge", is(149)))
                .andExpect(jsonPath("$.returnDate", is("2020-07-05")));
                //.andExpect(content().string("Atleast one request argument is invalid: checkout.discount: must be less than or equal to 100"));
    }

    private String urlBuilder(String date, String code, String days, String discount) {
        StringBuilder sb = new StringBuilder(); 
        sb.append("/rentals/checkout/").append(date).append("/item/").append(code).append("/days/").append(days).append("/discount/").append(discount);
        return sb.toString();
    }
}

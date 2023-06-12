package com.miggens.toolrental.transaction;

import com.miggens.toolrental.biz.RentalAgreement;
import com.miggens.toolrental.data.models.ToolEntity;
import com.miggens.toolrental.data.models.ToolRateEntity;
import com.miggens.toolrental.data.service.DataService;
import com.miggens.toolrental.tools.Tool;
import com.miggens.toolrental.tools.ToolRate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import de.schegge.holidays.*;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RentalTransaction {

    @Autowired
    private DataService dataService; 

    //private RentalAgreement rentalTransactioAgreement; 

    public Optional<RentalAgreement> runRransaction(String dateStr, String toolCode, Integer rentalDuration, Integer discount) {

        ToolEntity tool = this.dataService.getToolById(toolCode);
        
        if (tool == null) {
            return Optional.empty(); 
        }

        ToolRateEntity toolRate = this.dataService.getToolRatesById(tool.getType()); 
        
        if (toolRate == null) {
            return Optional.empty();
        }

        String[] dateComponents = dateStr.split("-"); 
        Integer year = Integer.parseInt(dateComponents[2]); 
        Integer month = Integer.parseInt(dateComponents[1]); 
        Integer day = Integer.parseInt(dateComponents[0]);

        LocalDate checkoutDate = LocalDate.of(year, month, day);
        LocalDate returnDate = checkoutDate.plusDays(rentalDuration-1); 
        List<LocalDate> rentalSpan = checkoutDate.datesUntil(returnDate.plusDays(1)).collect(Collectors.toList());
        Map<LocalDate, String> holidays = Holidays.in(Locale.US).getHolidays(year);
        Boolean doNotChargeWeekend = toolRate.getWeekendCharge(); 
        Boolean doNotChargeHoliday = toolRate.getHolidayCharge(); 
        Boolean weekendsInSpan = false;
        Boolean holidaysInSpan = false; 
        List<LocalDate> rentalSpanWeekends = null; 
        List<LocalDate> rentalSpanHolidays = null; 

        List<LocalDate> chargableDates = new ArrayList<LocalDate>(); 
       
        if (doNotChargeWeekend == false) {
            //need to removed weekends from rental span
            rentalSpanWeekends = rentalSpan.stream().filter(date -> (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)).collect(Collectors.toList());
            //System.out.println("SEIVED WEEKENDS " + rentalSpanWeekends);
            if (!rentalSpanWeekends.isEmpty()) weekendsInSpan = true; 
        }
        
        if (doNotChargeHoliday == false) {
            //remove holidays if any from rental span
            //if they exists in span and are on the weekend remove from the nearest weekeday.
            rentalSpanHolidays = this.getHolidaysInSpan(rentalSpan, holidays.keySet()); 
            
            if (!rentalSpanHolidays.isEmpty()) holidaysInSpan = true; 
        }

        if (weekendsInSpan == false && holidaysInSpan == false) {
            //all spandates are chargable
            for (LocalDate date: rentalSpan) {
                chargableDates.add(date); 
            }
        }
        else if (weekendsInSpan == true && holidaysInSpan == false) {
            //remove weekends from span. the remainder is chargable.
            for (LocalDate date: rentalSpan) {
                if (!rentalSpanWeekends.contains(date)) chargableDates.add(date); 
            }
        } 
        else if (weekendsInSpan == false && holidaysInSpan == true) {
            //remove holidays from span. the remainder is chargble
            for (LocalDate date: rentalSpan) {
                if (!rentalSpanHolidays.contains(date)) chargableDates.add(date); 
            }
        }
        else if (weekendsInSpan && holidaysInSpan){
            for (LocalDate date: rentalSpan) {
                if (!rentalSpanWeekends.contains(date) && !rentalSpanHolidays.contains(date)) chargableDates.add(date); 
            }
        }

        Integer chargebleDays = chargableDates.size(); 
        
        ToolRate rentalToolRate = new ToolRate(toolRate.getRate(), toolRate.getWeekdayCharge(), toolRate.getWeekendCharge(), toolRate.getHolidayCharge()); 
        
        Tool rentalTool = new Tool( tool.getCode(), tool.getType(), tool.getBrand(), rentalToolRate);
        Integer toolRateDailyRate = rentalToolRate.getDailyRate(); 
        
        Float dailyRatef = toolRateDailyRate / 100.0f; 
        Float discountf = discount / 100.0f; 
        Float totalBeforeDiscount = (dailyRatef * chargebleDays); 
          
        Integer amountToDiscount =  (int) Math.round( discountf * totalBeforeDiscount * 100.0f ) ;
        Integer preDiscountCharge = (int) Math.round( totalBeforeDiscount * 100.0f ); 

        RentalAgreement rentalTransactioAgreement = new RentalAgreement(); 
        rentalTransactioAgreement.setRentalTool( rentalTool );
        rentalTransactioAgreement.setRentalDaysCount(rentalDuration);
        rentalTransactioAgreement.setChargableDaysCount(chargebleDays);
        rentalTransactioAgreement.setCheckoutDate(checkoutDate);
        rentalTransactioAgreement.setReturnDate(returnDate);
        rentalTransactioAgreement.setDiscount( new Discount(discount, amountToDiscount));
        rentalTransactioAgreement.setPreDiscountCharge( preDiscountCharge );
        rentalTransactioAgreement.setFinalCheckoutCharge( preDiscountCharge - amountToDiscount );
        rentalTransactioAgreement.setAsString( rentalTransactioAgreement.toString() );

        System.out.println(rentalTransactioAgreement.toString());
        System.out.println("Dates Charged: " + chargableDates + "\n\n");

        return Optional.of(rentalTransactioAgreement);
    }
    
    private List<LocalDate> getHolidaysInSpan(List<LocalDate> span, Set<LocalDate> holidays) {

        List<LocalDate> holidaysInSpan = new ArrayList<LocalDate>();

        for (LocalDate h : holidays) {
            if (h.getDayOfWeek() == DayOfWeek.SATURDAY && span.contains(h)) {
                LocalDate adjHoliday = h.minusDays(1); 
                if (span.contains(adjHoliday)) {
                    holidaysInSpan.add(adjHoliday); 
                }
            }
            else if (h.getDayOfWeek() == DayOfWeek.SUNDAY && span.contains(h)) {
                LocalDate adjHoliday = h.plusDays(1);
                if (span.contains(adjHoliday)) {
                    holidaysInSpan.add(adjHoliday); 
                }
            }
            else if (span.contains(h)) {
                holidaysInSpan.add(h); 
            }
        }

        return holidaysInSpan; 
    }
}

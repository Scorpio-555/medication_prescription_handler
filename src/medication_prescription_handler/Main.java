package medication_prescription_handler;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class Main {

    public static List<SingleMedicationPrescriptionHandler> generateSingleHandlerList() throws Exception {
        List<SingleMedicationPrescriptionHandler> handlerList = new ArrayList<>();

        char letter = 'A';

        for(int i = -1; i < 11; i+= 2)
        {
            LocalDate prescriptionExpiration = LocalDate.now();
            prescriptionExpiration = prescriptionExpiration.plusDays(i);

            int year = prescriptionExpiration.getYear();
            int month = prescriptionExpiration.getMonthValue();
            int day = prescriptionExpiration.getDayOfMonth();

            SingleMedicationPrescriptionHandler tempHandler = new SingleMedicationPrescriptionHandler();
            tempHandler.setPrescriptionName("med" + letter);
            letter++;
            tempHandler.setPrescriptionExpiration(year, month, day);
            tempHandler.setMaxPillCountInBottle(1);
            tempHandler.setRefillsRemaining(9 - i);
            tempHandler.setMilligramDosageInASingleTablet(1);
            tempHandler.setTakeThisManyTabletsAtaTime(1);
            tempHandler.setTakeMedicationThisManyTimesADay(1);
            handlerList.add(tempHandler);
        }

        return handlerList;
    }

    public static void main(String[] args) throws Exception {

        //Testing PrescriptionExpirationAlert
        System.out.println();
        System.out.println("Testing PrescriptionExpirationAlert-------------------------------------------------");
        System.out.println();

        //Test 1    Make sure 1 year, 1 month, 1 day prints with no plural words
        LocalDate today = LocalDate.now();
        LocalDate alertDate = LocalDate.now();
        alertDate = alertDate.plusYears(1);
        alertDate = alertDate.plusMonths(1);
        alertDate = alertDate.plusDays(1);

        Period timeLeft = Period.between(today, alertDate);

        PrescriptionExpirationAlert testExpirationAlert1 = new PrescriptionExpirationAlert(timeLeft,"test1");
        assertTrue( "Incorrect message from test1",testExpirationAlert1.sendAlert().equals("Your prescription for test1 expires in 1 year, 1 month, 1 day"));

        //Test 2    Make sure 2 years, 2 months, 2 days prints with all plural words
        alertDate = LocalDate.now();
        alertDate = alertDate.plusYears(2);
        alertDate = alertDate.plusMonths(2);
        alertDate = alertDate.plusDays(2);

        timeLeft = Period.between(today, alertDate);

        PrescriptionExpirationAlert testExpirationAlert2 = new PrescriptionExpirationAlert(timeLeft,"test2");
        assertTrue(  "Incorrect message from test2",testExpirationAlert2.sendAlert().equals("Your prescription for test2 expires in 2 years, 2 months, 2 days"));

        //Test 3    Make sure -1 year, -1 month, -1 day prints overdue message with no plural words
        alertDate = LocalDate.now();
        alertDate = alertDate.minusYears(1);
        alertDate = alertDate.minusMonths(1);
        alertDate = alertDate.minusDays(1);

        timeLeft = Period.between(today, alertDate);

        PrescriptionExpirationAlert testExpirationAlert3 = new PrescriptionExpirationAlert(timeLeft,"test3");
        assertTrue( "Incorrect message from test3",testExpirationAlert3.sendAlert().equals("Your prescription for test3 is overdue by 1 year, 1 month, 1 day!"));

        //Test 4    Make sure -2 years, -2 months, -2 days prints overdue message with all plural words
        alertDate = LocalDate.now();
        alertDate = alertDate.minusYears(2);
        alertDate = alertDate.minusMonths(2);
        alertDate = alertDate.minusDays(2);

        timeLeft = Period.between(today, alertDate);

        PrescriptionExpirationAlert testExpirationAlert4 = new PrescriptionExpirationAlert(timeLeft,"test4");
        assertTrue( "Incorrect message from test4",testExpirationAlert4.sendAlert().equals("Your prescription for test4 is overdue by 2 years, 2 months, 2 days!"));

        //Test 5    Make sure 0 years, 0 months, 0 days prints expires today message
        alertDate = LocalDate.now();

        timeLeft = Period.between(today, alertDate);

        PrescriptionExpirationAlert testExpirationAlert5 = new PrescriptionExpirationAlert(timeLeft,"test5");
        assertTrue( "Incorrect message from test5",testExpirationAlert5.sendAlert().equals("Your prescription for test5 expires today!"));


        SingleMedicationPrescriptionHandler badInputHandler = new SingleMedicationPrescriptionHandler();

        //setting Invalid prescriptionName
        System.out.println("Testing invalid input for prescriptionName-----------------------------------------");
        System.out.println();

        try{
            String nullString = new String();
            badInputHandler.setPrescriptionName(nullString);
        }catch (Exception e){
            assertTrue("expected different error message for setting empty/blank prescription name",e.getMessage().equals("Prescription Name must be filled out"));
        }
        assertTrue(badInputHandler.getPrescriptionName() == null);

        try{
            badInputHandler.setPrescriptionName("");
        }catch (Exception e){
            assertTrue("expected different error message for setting empty/blank prescription name",e.getMessage().equals("Prescription Name must be filled out"));
        }
        assertTrue(badInputHandler.getPrescriptionName() == null);

        try{
            badInputHandler.setPrescriptionName("          ");
        }catch (Exception e){
            assertTrue("expected different error message for setting empty/blank prescription name",e.getMessage().equals("Prescription Name must be filled out"));
        }
        assertTrue(badInputHandler.getPrescriptionName() == null);

        try{
            badInputHandler.setPrescriptionName("abc");
        }catch (Exception e){
            assertTrue("expected different error message for setting prescription name with too few characters",e.getMessage().equals("Prescription Name must be at least four letters long"));
        }
        assertTrue(badInputHandler.getPrescriptionName() == null);

        try{
            badInputHandler.setPrescriptionName("abc1!");
        }catch (Exception e){
            assertTrue("expected different error message for setting prescription name with non-letter characters",e.getMessage().equals("Prescription Name must only contain letters"));
        }
        assertTrue(badInputHandler.getPrescriptionName() == null);

        //Testing invalid input for prescriptionExpiration
        System.out.println("Testing invalid input for prescriptionExpiration------------------------------------");
        System.out.println();

        //adding bad date
        try {
            badInputHandler.setPrescriptionExpiration(2021, 3, 33);
        } catch (Exception e) {
            assertTrue("expected different error message for adding bad date",e.getMessage().equals("Invalid date"));
        }
        assertTrue(badInputHandler.getPrescriptionExpiration() == null);

        //Testing invalid input for takeMedicationThisManyTimesADay
        System.out.println("Testing invalid input for takeMedicationThisManyTimesADay---------------------------");
        System.out.println();

        try {
            badInputHandler.setTakeMedicationThisManyTimesADay(0);
        } catch (Exception e) {
            assertTrue("expected different error message for setting takeMedicationThisManyTimesADay to be < 1",e.getMessage().equals("Number of times a day to take medication must be greater than zero"));
        }
        assertTrue("times taken in a day should be zero",badInputHandler.getTakeMedicationThisManyTimesADay() == 0);

        try {
            badInputHandler.setTakeMedicationThisManyTimesADay(-1);
        } catch (Exception e) {
            assertTrue("expected different error message for setting takeMedicationThisManyTimesADay to be < 1",e.getMessage().equals("Number of times a day to take medication must be greater than zero"));
        }
        assertTrue("times taken in a day should be zero",badInputHandler.getTakeMedicationThisManyTimesADay() == 0);

        //Testing invalid input for milligramDosageInASingleTablet
        System.out.println("Testing invalid input for milligramDosageInASingleTablet----------------------------");
        System.out.println();

        try {
            badInputHandler.setMilligramDosageInASingleTablet(0);
        } catch (Exception e) {
            assertTrue("expected different error message for setting milligramDosageInASinglePill to be < 1",e.getMessage().equals("Dosage of a single pill must be greater than zero"));
        }

        try {
            badInputHandler.setMilligramDosageInASingleTablet(-1);
        } catch (Exception e) {
            assertTrue("expected different error message for setting milligramDosageInASinglePill to be < 1",e.getMessage().equals("Dosage of a single pill must be greater than zero"));
        }
        assertTrue("mg dosage should be zero",badInputHandler.getMilligramDosageInASingleTablet() == 0);

        //Testing invalid input for takeThisManyTabletsAtaTime
        System.out.println("Testing invalid input for takeThisManyTabletsAtaTime--------------------------------");
        System.out.println();

        try {
            badInputHandler.setTakeThisManyTabletsAtaTime(0);
        } catch (Exception e) {
            assertTrue("expected different error message for setting takeThisManyTabletsAtaTime to be < 1",e.getMessage().equals("Number of Tablets must be greater than zero"));
        }

        try {
            badInputHandler.setTakeThisManyTabletsAtaTime(-1);
        } catch (Exception e) {
            assertTrue("expected different error message for setting takeThisManyTabletsAtaTime to be < 1",e.getMessage().equals("Number of Tablets must be greater than zero"));
        }
        assertTrue("# of tablets should be zero",badInputHandler.getTakeThisManyTabletsAtaTime() == 0);

        //Testing invalid input for pillsRemainingInBottle
        System.out.println("Testing invalid input for pillsRemainingInBottle------------------------------------");
        System.out.println();

        try {
            badInputHandler.setPillsRemainingInBottle(-1);
        } catch (Exception e) {
            assertTrue("expected different error message for setting pillsRemainingInBottle to be < 0",e.getMessage().equals("Pills remaining can't be negative"));
        }
        assertTrue( "pills remaining should be -1",badInputHandler.getPillsRemainingInBottle() == -1);

        //Testing addHandler
        System.out.println("Testing addHandler------------------------------------------------------------------");
        System.out.println();

        MedicationPrescriptionGeneralHandler generalHandlerAdd = new MedicationPrescriptionGeneralHandler();

        SingleMedicationPrescriptionHandler allOnes = new SingleMedicationPrescriptionHandler();
        try{
            generalHandlerAdd.addHandler(allOnes);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        allOnes.setPrescriptionName("allOnes");
        try{
            generalHandlerAdd.addHandler(allOnes);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        allOnes.setPrescriptionExpiration(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        try{
            generalHandlerAdd.addHandler(allOnes);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        allOnes.setTakeThisManyTabletsAtaTime(1);
        try{
            generalHandlerAdd.addHandler(allOnes);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        allOnes.setTakeMedicationThisManyTimesADay(1);
        try{
            generalHandlerAdd.addHandler(allOnes);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        allOnes.setMilligramDosageInASingleTablet(1);
        try{
            generalHandlerAdd.addHandler(allOnes);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            allOnes.setRefillsRemaining(0);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        allOnes.setMaxPillCountInBottle(1);
        try{
            generalHandlerAdd.addHandler(allOnes);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            generalHandlerAdd.addHandler(allOnes);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println(allOnes.sendLowPillCountAlert());
        generalHandlerAdd.run();

        //Generating list of single handlers and running general handler
        System.out.println("Generating list of single handlers and running general handler----------------------");
        System.out.println();

        List<SingleMedicationPrescriptionHandler> autoGeneratedMedHandlerList = generateSingleHandlerList();
        MedicationPrescriptionGeneralHandler generalHandler = new MedicationPrescriptionGeneralHandler();
        for(SingleMedicationPrescriptionHandler handler: autoGeneratedMedHandlerList){
            generalHandler.addHandler(handler);
        }

        generalHandler.run();
        generalHandler.run();

    }
}

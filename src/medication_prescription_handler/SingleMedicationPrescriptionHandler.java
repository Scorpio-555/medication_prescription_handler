package medication_prescription_handler;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleMedicationPrescriptionHandler {

    private LocalDate _prescriptionExpiration;
    private String _prescriptionName;
    private int _takeMedicationThisManyTimesADay;
    private int _milligramDosageInASingleTablet;
    private int _takeThisManyTabletsAtaTime;
    private int _pillsRemainingInBottle;

    public SingleMedicationPrescriptionHandler(){
        _pillsRemainingInBottle = -1;
    }

    public String getPrescriptionName(){
        return _prescriptionName;
    }
    public LocalDate getPrescriptionExpiration() {
        return _prescriptionExpiration;
    }
    public int getTakeMedicationThisManyTimesADay(){return _takeMedicationThisManyTimesADay;}
    public int getMilligramDosageInASingleTablet(){return _milligramDosageInASingleTablet;}
    public int getTakeThisManyTabletsAtaTime(){return _takeThisManyTabletsAtaTime;}
    public int getPillsRemainingInBottle(){return _pillsRemainingInBottle;}

    public void setTakeMedicationThisManyTimesADay(int takeMedicationThisManyTimesADay) throws Exception{
        if(takeMedicationThisManyTimesADay < 1){
            MedHandlerException takeMedLessThanOnceADay = new MedHandlerException("Number of times a day to take medication must be greater than zero");
            System.out.println(takeMedLessThanOnceADay.getMessage());
            System.out.println();
            throw takeMedLessThanOnceADay;
        } else {
            _takeMedicationThisManyTimesADay = takeMedicationThisManyTimesADay;
        }
    }

    public void setMilligramDosageInASinglePilTablet(int milligramDosageInASinglePill) throws Exception{
        if(milligramDosageInASinglePill < 1){
            MedHandlerException singlePillDosageIsLessThanOneMg = new MedHandlerException("Dosage of a single pill must be greater than zero");
            System.out.println(singlePillDosageIsLessThanOneMg.getMessage());
            System.out.println();
            throw singlePillDosageIsLessThanOneMg;
        } else {
            _milligramDosageInASingleTablet = milligramDosageInASinglePill;
        }
    }

    public void setTakeThisManyTabletsAtaTime(int takeThisManyTabletsAtaTime) throws Exception{
        if(takeThisManyTabletsAtaTime < 1){
            MedHandlerException NumberOfTabletsIsLessThanOne = new MedHandlerException("Number of Tablets must be greater than zero");
            System.out.println(NumberOfTabletsIsLessThanOne.getMessage());
            System.out.println();
            throw NumberOfTabletsIsLessThanOne;
        } else {
            _takeThisManyTabletsAtaTime = takeThisManyTabletsAtaTime;
        }
    }
    public void setPillsRemainingInBottle(int pillsRemainingInBottle) throws Exception{
        if(pillsRemainingInBottle < 0){
            MedHandlerException pillCountIsLessThanZero = new MedHandlerException("Pills remaining can't be negative");
            System.out.println(pillCountIsLessThanZero.getMessage());
            System.out.println();
            throw pillCountIsLessThanZero;
        } else {
            _pillsRemainingInBottle = pillsRemainingInBottle;
        }
    }

    public void set_prescriptionName(String prescriptionName) throws Exception{

        Pattern pattern = Pattern.compile("[^A-Za-z]");
        Matcher matcher = pattern.matcher(prescriptionName);
        boolean containsBadCharacters = matcher.find();

       if(prescriptionName.isEmpty() || prescriptionName.isBlank()){
           MedHandlerException nullPrescriptionName = new MedHandlerException("Prescription Name must be filled out");
           System.out.println(nullPrescriptionName.getMessage());
           System.out.println();
           throw nullPrescriptionName;
        }else if(prescriptionName.length() < 4) {
           MedHandlerException tooShortPrescriptionName = new MedHandlerException("Prescription Name must be at least four letters long");
           System.out.println(tooShortPrescriptionName.getMessage());
           System.out.println();
           throw tooShortPrescriptionName;
        }else if(containsBadCharacters){
           MedHandlerException nonLetterPrescriptionName = new MedHandlerException("Prescription Name must only contain letters");
           System.out.println(nonLetterPrescriptionName.getMessage());
           System.out.println();
           throw nonLetterPrescriptionName;
       }else{
           _prescriptionName = prescriptionName;
       }
    }

    public void setPrescriptionExpiration(int year, int month, int day) throws Exception{
        try {
            LocalDate prescriptionExpiration = LocalDate.of(year, month, day);
            _prescriptionExpiration = prescriptionExpiration;
        } catch (Exception e){
            MedHandlerException invalidDate = new MedHandlerException("Invalid date");
            System.out.println(invalidDate.getMessage());
            System.out.println();
            throw invalidDate;
        }
    }

    public boolean isCloseToPrescriptionExpiration(){
        boolean isCloseToPrescriptionExpiration = false;

        LocalDate today = LocalDate.now();

        Period timeLeft = Period.between(today, _prescriptionExpiration);
        int years = timeLeft.getYears();
        int months = timeLeft.getMonths();
        int days = timeLeft.getDays();

        if((years < 1) && (months < 1) && (days < 6)){
            isCloseToPrescriptionExpiration = true;
        }

        return isCloseToPrescriptionExpiration;
    }

    public void sendPrescriptionExpirationAlert() {
        LocalDate today = LocalDate.now();
        Period timeLeft = Period.between(today, _prescriptionExpiration);
        PrescriptionExpirationAlert alert = new PrescriptionExpirationAlert(timeLeft, _prescriptionName);
        alert.sendAlert();
    }

    public void sendTakePillAlert(){
        System.out.println("Remember to take your pill for " + _prescriptionName);
        System.out.println();
    }

    public void sendLowPillCountAlert(){

    }

    public boolean isValid() {
        boolean isValid = false;

        if(     (_prescriptionName != null) &&
                (_prescriptionExpiration != null) &&
                (_takeThisManyTabletsAtaTime != 0) &&
                (_milligramDosageInASingleTablet != 0) &&
                (_takeMedicationThisManyTimesADay != 0) &&
                (_pillsRemainingInBottle != -1)
        ){
            isValid = true;
        } else if(_prescriptionName == null){
            System.out.println("Must enter prescription name");
            System.out.println();
        } else if(_prescriptionExpiration == null){
            System.out.println("Must enter prescription expiration");
            System.out.println();
        } else if(_takeThisManyTabletsAtaTime == 0){
            System.out.println("Must enter number of tablets to take");
            System.out.println();
        } else if(_takeMedicationThisManyTimesADay == 0){
            System.out.println("Must enter number of times a day to take medication");
            System.out.println();
        }else if(_milligramDosageInASingleTablet == 0){
            System.out.println("Must enter milligram dosage in a single tablet");
            System.out.println();
        } else if(_pillsRemainingInBottle == -1){
            System.out.println("Must enter number of tablets remaining");
            System.out.println();
        }

        return isValid;
    }
}

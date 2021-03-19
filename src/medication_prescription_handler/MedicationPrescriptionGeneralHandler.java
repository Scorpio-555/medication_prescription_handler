package medication_prescription_handler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class MedicationPrescriptionGeneralHandler implements Runnable{
    private List<SingleMedicationPrescriptionHandler> prescriptionList;
    private LocalDateTime lastDateAndTimeTakePillsAlertWasSent;

    MedicationPrescriptionGeneralHandler(){
        prescriptionList = new ArrayList<>();
    }

    private int getMaxTimesADay(){
        int maxTimesADay = 0;
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(handler.getTakeMedicationThisManyTimesADay() > maxTimesADay){
                maxTimesADay = handler.getTakeMedicationThisManyTimesADay();
            }
        }
        return maxTimesADay;
    }

    private String sendTakePillAlert(){
        String takePillsMessage = new String();
        LocalDate today = LocalDate.now();

        if( ! prescriptionList.isEmpty()){
            if(lastDateAndTimeTakePillsAlertWasSent == null){
                lastDateAndTimeTakePillsAlertWasSent = LocalDateTime.now();
                takePillsMessage = "Remember to take your pills";
            } else if (lastDateAndTimeTakePillsAlertWasSent.toLocalDate().equals(today)){
                int numberOfHoursBetweenAlerts = 24 / getMaxTimesADay();
                LocalTime rightNow = LocalTime.now();
                LocalTime timeOfLastAlert = lastDateAndTimeTakePillsAlertWasSent.toLocalTime();
                int currentHour = rightNow.getHour();
                int hourOfLastAlert = timeOfLastAlert.getHour();

                if((currentHour - hourOfLastAlert) >= numberOfHoursBetweenAlerts){
                    lastDateAndTimeTakePillsAlertWasSent = LocalDateTime.now();
                    takePillsMessage = "Remember to take your pills";
                }

            } else {
                lastDateAndTimeTakePillsAlertWasSent = LocalDateTime.now();
                takePillsMessage = "Remember to take your pills";
            }
        }

        return takePillsMessage;
    }

    private List<String> sendPrescriptionExpirationAlerts(){
        List<String> prescriptionExpirationMessages = new ArrayList<>();
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(handler.isCloseToPrescriptionExpiration() && handler.haveNotAlreadySentExpirationAlertToday()){
                handler.setDateExpirationAlertWasLastSent(LocalDate.now());
                prescriptionExpirationMessages.add(handler.sendPrescriptionExpirationAlert());
            }
        }

        return prescriptionExpirationMessages;
    }

    private List<String> sendLowPillCountAlerts(){
        List<String> lowPillCountMessages = new ArrayList<>();
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(handler.isCloseToRunningOut() && handler.haveNotAlreadySentPillCountAlertToday()){
                handler.setDatePillCountAlertWasLastSent(LocalDate.now());
                lowPillCountMessages.add(handler.sendLowPillCountAlert());
            }
        }

        return lowPillCountMessages;
    }

    public SingleMedicationPrescriptionHandler cloneFromList(String prescriptionName, int dosage) {
        SingleMedicationPrescriptionHandler clone = new SingleMedicationPrescriptionHandler();

        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(     handler.getPrescriptionName().equals(prescriptionName) &&
                    handler.getMilligramDosageInASingleTablet() == dosage){
                clone = handler.clone();
            }
        }

        return clone;
    }

    public List<SingleMedicationPrescriptionHandler> cloneAll(){
        List<SingleMedicationPrescriptionHandler> cloneList = new ArrayList<>();

        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            cloneList.add(handler.clone());
        }

        return cloneList;
    }

    public void addHandler(SingleMedicationPrescriptionHandler newHandler) throws Exception {
        if(newHandler.isValid()){

            boolean newHandlerIsUnique = true;
            for(SingleMedicationPrescriptionHandler existingHandler: prescriptionList){

                if(     newHandler.getPrescriptionName().equals(existingHandler.getPrescriptionName()) &&
                        newHandler.getMilligramDosageInASingleTablet() == existingHandler.getMilligramDosageInASingleTablet()
                ){
                    newHandlerIsUnique = false;
                }
            }

            if(newHandlerIsUnique){
                prescriptionList.add(newHandler.clone());
            } else{
                Exception duplicate = new Exception("Didn't save! You already have saved a prescription of the same name and dosage");
                throw duplicate;
            }

        }

    }

    public void takePill(String prescriptionName, int dosage){
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(     handler.getPrescriptionName().equals(prescriptionName) &&
                    handler.getMilligramDosageInASingleTablet() == dosage)
            {
                int pillsBeingTaken = handler.getTakeThisManyTabletsAtaTime();
                int pillsInBottle = handler.getPillsRemainingInBottle();
                int pillsLeft = pillsInBottle - pillsBeingTaken;

                if(pillsLeft < 0){
                    pillsLeft = 0;
                }

                try {
                    handler.setPillsRemainingInBottle(pillsLeft);
                } catch (Exception e) {
                    //shouldn't ever have an exception
                }
            }
        }
    }

    public void delete(String prescriptionName, int dosage){

        List <SingleMedicationPrescriptionHandler> tempList = new ArrayList<>();
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            tempList.add(handler.clone());
        }

        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(     handler.getPrescriptionName().equals(prescriptionName) &&
                    handler.getMilligramDosageInASingleTablet() == dosage)
            {
                tempList.remove(prescriptionList.indexOf(handler));
            }
        }

        prescriptionList = tempList;
    }

    public void replace(String prescriptionNameOfObsolete, int dosageOfObsolete, SingleMedicationPrescriptionHandler replacement) throws Exception {
        if(replacement.isValid()){
            delete(prescriptionNameOfObsolete,dosageOfObsolete);
            addHandler(replacement);
        }
    }

    public void refillBottle(String prescriptionName, int dosage){

        for(SingleMedicationPrescriptionHandler handler: prescriptionList) {
            if (handler.getPrescriptionName().equals(prescriptionName) &&
                    handler.getMilligramDosageInASingleTablet() == dosage)
            {
                try {
                    handler.setPillsRemainingInBottle(handler.getMaxPillCountInBottle());
                    if(handler.getRefillsRemaining() > 0){
                        handler.setRefillsRemaining(handler.getRefillsRemaining() - 1);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    public void run(){
        List<String> masterListOfMessages = new ArrayList<>();
        masterListOfMessages.add(sendTakePillAlert());
        masterListOfMessages.addAll(sendPrescriptionExpirationAlerts());
        masterListOfMessages.addAll(sendLowPillCountAlerts());

        List<String> tempList = new ArrayList<>();
        for(String message: masterListOfMessages){
            if(! message.isEmpty()){
                tempList.add(message);
            }
        }

        masterListOfMessages = tempList;

        if(!masterListOfMessages.isEmpty()){
            for(String message: masterListOfMessages){
                System.out.println(message);
                System.out.println();
            }
        }
    }
}

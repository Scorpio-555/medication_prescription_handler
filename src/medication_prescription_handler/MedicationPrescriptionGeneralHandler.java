package medication_prescription_handler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicationPrescriptionGeneralHandler {
    private List<SingleMedicationPrescriptionHandler> prescriptionList;

    MedicationPrescriptionGeneralHandler(){
        prescriptionList = new ArrayList<>();
    }

    MedicationPrescriptionGeneralHandler(List<SingleMedicationPrescriptionHandler> list){
        prescriptionList = list;
    }

    private String sendTakePillAlert(){
        return "Remember to take your pills";
    }

    private List<String> sendPrescriptionExpirationAlerts(){
        List<String> prescriptionExpirationMessages = new ArrayList<>();
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(handler.isCloseToPrescriptionExpiration()){
                prescriptionExpirationMessages.add(handler.sendPrescriptionExpirationAlert());
            }
        }

        return prescriptionExpirationMessages;
    }

    private List<String> sendLowPillCountAlerts(){
        List<String> lowPillCountMessages = new ArrayList<>();
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(handler.isCloseToRunningOut()){
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
                MedHandlerException duplicate = new MedHandlerException("Didn't save! You already have saved a prescription of the same name and dosage");
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

        for(String message: masterListOfMessages){
            System.out.println(message);
            System.out.println();
        }
    }
}

package utils;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Common tools
 */

public class Tools {


    /**
     * get string in format yyyyMMdd_HHmmss
     * @return
     */
    public static String getCurDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * get user email in format yyyyMMdd_HHmmss_*@gmail.com
     * @return
     */
    public static String getRandomUserEmail() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_");
        return sdf.format(new Date(System.currentTimeMillis())) + String.valueOf(System.currentTimeMillis()).substring(10,13) + "@gmail.com";
    }

    /**
     * get random number in range 0 - i
     * @param i
     * @return
     */
    public static String getRandomNumber(int i) {
        Random r = new Random();
        return String.valueOf(r.nextInt(i));
    }


    /**
     * compare lists and generate differences table
     * @param actual
     * @param expected
     */
    public static void compareTwoLists(List actual, List expected){

        LinkedList<String> listOfProcessedItemsFromActual = new LinkedList<String>();
        LinkedList<String> listOfProcessedItemsFromExpected = new LinkedList<String>();

        // search for records in Actual but not in Expected response
        int i = 0;
        for (Object entityFromActual : expected) {
            i++;
            boolean found = false;
            //LOGGER.info(i + " - " + entity);
            for(Object entityFromExpected : actual){
                if(entityFromExpected.toString().equals(entityFromActual.toString())) {
                    found = true;
                    listOfProcessedItemsFromActual.add(entityFromActual.toString());
                    break;
                }
            };
            if(!found) {
                //LOGGER.error(i + " Item was not found in Expected response: " + entityFromActual);
                listOfProcessedItemsFromActual.add(ReporterManager.MARKER_OF_FAILED_ITEM +  entityFromActual);
            }
        }


        //search for records in Expected response but not in Actual report
        i = 0;
        for (Object entityFromExpected : actual) {
            i++;
            boolean found = false;
            for(Object entityFromActual : expected){
                if(entityFromExpected.toString().equals(entityFromActual.toString())) {
                    found = true;
                    listOfProcessedItemsFromExpected.add(entityFromExpected.toString());
                    break;
                }
            };
            if(!found) {
                //LOGGER.error(i + " Item was not found in Expected response: " + entityFromExpected);
                listOfProcessedItemsFromExpected.add(ReporterManager.MARKER_OF_FAILED_ITEM + entityFromExpected);
            }
        }

        actual.clear();
        actual.addAll(listOfProcessedItemsFromExpected);
        expected.clear();
        expected.addAll(listOfProcessedItemsFromActual);
    }

    /**
     * transform string to list (split and trim)
     * @param string
     * @return
     */
    public static List<String> stringsToList(String string) {
        return Arrays.asList(string.replace(" ","").split(","));
    }


    /*        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        List<ContactModel> contactList = gson.fromJson(jsonString, type);
        */
}

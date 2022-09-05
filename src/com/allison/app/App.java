package com.allison.app;

import com.allison.data.AccuracyData;
import com.allison.data.TitanicSurvival;
import com.allison.model.ComplexModl;
import com.allison.model.NaiveModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class App {
    public static final String FILE_NAME = "/Users/axin/Downloads/newFullTitanic.csv";
    public static final String OUTPUT_DIR = "/Users/axin/Downloads/output.csv";
    public static final int REPEAT_COUNT = 100;
    private static List<TitanicSurvival> loadData(String fileName) {
        BufferedReader in = null;
        List<TitanicSurvival> list = new ArrayList<TitanicSurvival>();
        try {
            in = new BufferedReader(new FileReader(fileName));
            String line = in.readLine();
            while (line != null) {
                if (!line.isEmpty()) {
                    String[] fields = line.split(",");
                    TitanicSurvival ts = new TitanicSurvival(fields[1], fields[3], fields[0], fields[9]);
                    list.add(ts);
                }
                line = in.readLine();
            }
            list.remove(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ex) {
                }
            }
        }
        return list;
    }

    private static void exportCsv(List<AccuracyData> accuracyList) {
        FileWriter out = null;
        try{
            String outFile = OUTPUT_DIR + "output_" + (new Date().getTime()) + ".csv";
            out = new FileWriter(outFile);
            for (AccuracyData data: accuracyList) {
                out.write(data.toString());
            }
            out.flush();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception ex){}
            }
        }
    }

    private static List<AccuracyData> buildCases(List<TitanicSurvival> list){
        List<AccuracyData> accuracyList = new ArrayList<AccuracyData>();

        int dataSize = list.size();
        double runRoot = Math.sqrt(REPEAT_COUNT);
        List<TitanicSurvival> listCopy = new ArrayList<TitanicSurvival>(list);

        for (int trainSize = 10; trainSize < listCopy.size()-10; trainSize += 20){
            double[] complexAccuracies = new double[REPEAT_COUNT];
            double[] naiveAccuracies = new double[REPEAT_COUNT];
            int testSize = 0;

            for (int repeatIdx = 0; repeatIdx < REPEAT_COUNT; repeatIdx++) {
                Collections.shuffle(listCopy);
                List<TitanicSurvival> trainList = listCopy.subList(0, trainSize - 1);
                List<TitanicSurvival> testList = listCopy.subList(trainSize, dataSize - 1);
                testSize = testList.size();

                //Complex Bayes Model accuracy calculation
                ComplexModl cModel = new ComplexModl(trainList);
                complexAccuracies[repeatIdx] = AccuracyCalc.accuracy(testList, cModel);

                //Naive Bayes Model accuracy calculation
                NaiveModel nModel = new NaiveModel(trainList);
                naiveAccuracies[repeatIdx] = AccuracyCalc.accuracy(testList, nModel);
            }
            double[] cRet = stats(complexAccuracies);
            double[] nRet = stats(naiveAccuracies);
            AccuracyData ret = new AccuracyData(trainSize,
                    cRet[0]/testSize,
                    cRet[1]/runRoot/dataSize,
                    nRet[0]/testSize,
                    nRet[1]/runRoot/dataSize);
            accuracyList.add(ret);
        }
        return accuracyList;
    }

    private static double[] stats(double[] arr){
        double sum = 0;
        double std = 0;
        int size = arr.length;

        double avg = Arrays.stream(arr).average().getAsDouble();
        for (int i = 0; i < size; i++){
           sum += (arr[i]-avg) * (arr[i]-avg);
        }
        std = Math.sqrt(sum/size);
        double[] ret = {avg, std};
        return ret;
    }

    public static void main (String[] args) {
        List<TitanicSurvival> list = loadData(FILE_NAME);

        List<AccuracyData> accuracyList = buildCases(list);
        System.out.println(accuracyList);
        exportCsv(accuracyList);

    }
}

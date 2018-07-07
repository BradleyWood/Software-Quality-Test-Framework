package org.sqtf.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CsvSource extends DataSource {

    @Override
    List<Object[]> loadData(String source, Object instance) {
        final LinkedList<Object[]> data = new LinkedList<>();

        try (FileReader fr = new FileReader(source)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(fr);
            for (CSVRecord record : records) {
                Object[] columnData = new Object[record.size()];
                for (int i = 0; i < columnData.length; i++) {
                    columnData[i] = record.get(i).trim();
                }
                data.add(columnData);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return data;
    }

    @Override
    boolean accept(String source, Object instance, Class[] classes) {
        return source.trim().toLowerCase().endsWith(".csv");
    }

}

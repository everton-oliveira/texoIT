package br.com.texo.teste.helper;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

public class MovieListCSVReader {

    public List<MovieCSV> read(String csvFileName) throws IOException {

        ClassPathResource classPathResource = new ClassPathResource(csvFileName);
        Reader reader = new InputStreamReader(classPathResource.getInputStream());

        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .withCSVParser(parser)
                .build();

        return new CsvToBeanBuilder<MovieCSV>(csvReader)
                .withType(MovieCSV.class)
                .build()
                .stream()
                .collect(Collectors.toList());
    }
}

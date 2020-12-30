package com.board_of_ads.service.impl;


import com.board_of_ads.models.Country;
import com.board_of_ads.repository.CountryRepository;
import com.board_of_ads.service.interfaces.CountryService;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public void saveCountry() throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream("src/main/resources/Countries/Countries.xls");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            Workbook workbook = new HSSFWorkbook(bufferedInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                countryRepository.save(new Country(row.getCell(0).getStringCellValue()));
            }
        }
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }
}

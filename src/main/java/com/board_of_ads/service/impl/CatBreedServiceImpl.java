package com.board_of_ads.service.impl;

import com.board_of_ads.models.posting.forCats.CatBreed;

import com.board_of_ads.repository.CatBreedRepository;
import com.board_of_ads.service.interfaces.CatBreedService;
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
public class CatBreedServiceImpl implements CatBreedService {
    private final CatBreedRepository catBreedRepository;

    @Override
    public void saveCatBreed() throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream("src/main/resources/cat_breeds/cat_breeds.xls");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            Workbook workbook = new HSSFWorkbook(bufferedInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                catBreedRepository.save(new CatBreed(row.getCell(0).getStringCellValue()));
            }
        }
    }

    @Override
    public List<CatBreed> findAll() {
        return catBreedRepository.findAll();
    }
}

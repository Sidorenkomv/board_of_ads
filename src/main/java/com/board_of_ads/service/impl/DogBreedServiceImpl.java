package com.board_of_ads.service.impl;

import com.board_of_ads.models.posting.forDogs.DogBreed;

import com.board_of_ads.repository.DogBreedRepository;
import com.board_of_ads.service.interfaces.DogBreedService;
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
public class DogBreedServiceImpl implements DogBreedService {
    private final DogBreedRepository dogBreedRepository;

    @Override
    public void saveDogBreed() throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream("src/main/resources/dog_breeds/dog_breeds.xls");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            Workbook workbook = new HSSFWorkbook(bufferedInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                dogBreedRepository.save(new DogBreed(row.getCell(0).getStringCellValue()));
            }
        }
    }

    @Override
    public List<DogBreed> findAll() {
        return dogBreedRepository.findAll();
    }
}

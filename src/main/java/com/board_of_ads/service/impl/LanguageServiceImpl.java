package com.board_of_ads.service.impl;

import com.board_of_ads.models.ForeignLanguage;
import com.board_of_ads.models.dto.LanguageDto;
import com.board_of_ads.repository.LanguageRepository;
import com.board_of_ads.service.interfaces.LanguageService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;

    @Override
    public void saveLanguage() throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream("src/main/resources/Languages/Languages.xls");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            Workbook workbook = new HSSFWorkbook(bufferedInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                languageRepository.save(new ForeignLanguage(row.getCell(0).getStringCellValue()));
            }
        }
    }

    @Override
    public List<LanguageDto> findAllLanguages() {
        return languageRepository.findAllLanguages();
    }
}

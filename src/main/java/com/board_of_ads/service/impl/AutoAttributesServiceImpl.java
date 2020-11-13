package com.board_of_ads.service.impl;

import com.board_of_ads.models.posting.autoTransport.cars.car_attributes.AutoColor;
import com.board_of_ads.models.posting.autoTransport.cars.car_attributes.AutoModel;
import com.board_of_ads.repository.AutoColorRepository;
import com.board_of_ads.repository.AutoModelRepository;
import com.board_of_ads.service.interfaces.AutoAttributesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AutoAttributesServiceImpl implements AutoAttributesService {
    @Autowired
    private final AutoColorRepository autoColorRepository;
    @Autowired
    private final AutoModelRepository autoModelRepository;

    @Override
    public void saveNewAutoColor(AutoColor autoColor) {
        autoColorRepository.save(autoColor);
    }

    @Override
    public Set<String> getAllAutoColorsRusNames() {
        List<AutoColor> acList = autoColorRepository.findAll();
        Set<String> autoColorsSet = new TreeSet<>();
        for (AutoColor ac : acList) {
            autoColorsSet.add(ac.getColorRusName());
        }
        return autoColorsSet;
    }

    @Override
    public void saveModel(AutoModel auto) {
        autoModelRepository.save(auto);
    }

    @Override
    public void getDataFromAutoCatalogueExcel() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/auto_catalogue/auto_catalogue.xls");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        Workbook workbook = new HSSFWorkbook(bufferedInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            AutoModel auto = new AutoModel();
            String brand = row.getCell(0).getStringCellValue();
            auto.setBrand(brand);
            CellType cellType = row.getCell(1).getCellType();
            String model;
            if (cellType.equals(CellType.STRING)) {
                model = row.getCell(1).getStringCellValue();
            } else {
                int m = (int) row.getCell(1).getNumericCellValue();
                model = Integer.toString(m);
            }
            auto.setModel(model);
            short yearStart;
            cellType = row.getCell(2).getCellType();
            if (cellType.equals(CellType.NUMERIC)) {
                yearStart = (short) row.getCell(2).getNumericCellValue();
            } else {
                yearStart = 1990;
            }
            auto.setYearStart(yearStart);
            short yearEnd;
            cellType = row.getCell(3).getCellType();
            if (cellType.equals(CellType.NUMERIC)) {
                yearEnd = (short) row.getCell(3).getNumericCellValue();
            } else {
                yearEnd = 2020;
            }
            auto.setYearEnd(yearEnd);
            autoModelRepository.save(auto);
        }
        fileInputStream.close();
    }

    @Override
    public Set<String> getBrandsSet() {
        Set<String> brandsSet = new TreeSet<>();
        List<AutoModel> autoModelList = autoModelRepository.findAll();
        for (AutoModel am : autoModelList) {
            brandsSet.add(am.getBrand());
        }
        return brandsSet;
    }

    @Override
    public Set<String> getModelsSet(String brand) {
        Set<String> modelsSet = new TreeSet<>();
        List<AutoModel> autoModelList = autoModelRepository.findAllByBrand(brand);
        for (AutoModel am : autoModelList) {
            if (am.getBrand().equals(brand)) {
                modelsSet.add(am.getModel());
            }
        }
        return modelsSet;
    }

    @Override
    public Set<Short> getYearsByBrandAndModel(String brand, String model) {
        AutoModel am = autoModelRepository.findAutoModelByBrandAndModel(brand, model);
        Set<Short> years = new TreeSet<>();
        for (short year = am.getYearStart(); year <= am.getYearEnd(); year++) {
            years.add(year);
        }
        return years;
    }

    @Override
    public AutoModel getAutoModelByBrandAndModel(String brand, String model) {
        return autoModelRepository.findAutoModelByBrandAndModel(brand, model);
    }
}

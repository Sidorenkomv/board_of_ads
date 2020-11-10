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
import java.util.HashSet;
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
        System.out.println("Hello Color " + autoColor.getColorName());
        autoColorRepository.save(autoColor);
    }

    @Override
    public Set<String> getAllAutoColorsRusNames() {
        List<AutoColor> acList = autoColorRepository.findAll();
        Set<String> autoColorsSet = new HashSet<>();
        for (AutoColor ac: acList) {
            autoColorsSet.add(ac.getColorRusName());
        }
        return autoColorsSet;
    }

    @Override
    public Set<String> getTypeOfUsedCarPostingSet(){
        Set<String> typeOfUsedCarPostingSet = new TreeSet<>();
        typeOfUsedCarPostingSet.add("Продаю личный автомобиль");
        typeOfUsedCarPostingSet.add("Автомобиль приобретен на продажу");
        return typeOfUsedCarPostingSet;
    }

    @Override
    public Set<String> getPowerSteeringTypes(){
        Set<String> types = new TreeSet<>();
        types.add("Гидро-");
        types.add("Электро-");
        types.add("Электрогидро-");
        return types;
    }

    @Override
    public Set<String> getClimateControlTypes(){
        Set<String> types = new TreeSet<>();
        types.add("Кондиционер");
        types.add("Климат-контроль однозонный");
        types.add("Климат-контроль двузонный");
        types.add("Климат-контроль трехзонный");
        return types;
    }

    @Override
    public Set<String> getAllInteriorTypes(){
        Set<String> types = new HashSet<>();
        types.add("Кожа");
        types.add("Ткань");
        types.add("Велюр");
        types.add("Комбинированный");
        return types;
    }

    @Override
    public Set<String> getPowerWindowTypes(){
        Set<String> types = new HashSet<>();
        types.add("Только передние");
        types.add("Передние и задние");
        return types;
    }

    @Override
    public Set<String> getAudioSystemTypes(){
        Set<String> types = new TreeSet<>();
        types.add("2 колонки");
        types.add("4 колонки");
        types.add("6 колонок");
        types.add("8+ колонок");
        return types;
    }

    @Override
    public Set<String> getFrontLightTypes(){
        Set<String> types = new HashSet<>();
        types.add("Галогенные");
        types.add("Ксеноновые");
        types.add("Светодиодные");
        return types;
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
            String brand =row.getCell(0).getStringCellValue();
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

            int yearStart;
            cellType = row.getCell(2).getCellType();
            if (cellType.equals(CellType.NUMERIC)) {
                yearStart = (int) row.getCell(2).getNumericCellValue();
            } else {
                yearStart = 1990;
            }
            auto.setYearStart(yearStart);

            int yearEnd;

            cellType = row.getCell(3).getCellType();
            if (cellType.equals(CellType.NUMERIC)) {
                yearEnd = (int) row.getCell(3).getNumericCellValue();
            } else {
                yearEnd = 2020;
            }

            auto.setYearEnd(yearEnd);
            System.out.println("Added car: " + brand + " - " + model + " start: " + yearStart + " end: " + yearEnd);


            autoModelRepository.save(auto);
        }
        fileInputStream.close();
    }

    @Override
    public Set<String> getBrandsSet(){
        Set<String> brandsSet = new TreeSet<>();
        List<AutoModel> autoModelList = autoModelRepository.findAll();
        for (AutoModel am : autoModelList) {
            brandsSet.add(am.getBrand());
            //  System.out.println("Added brand - " + am.getBrand());
        }
        return brandsSet;
    }



}

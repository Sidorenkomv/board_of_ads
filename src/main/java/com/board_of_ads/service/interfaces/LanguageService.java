package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.dto.LanguageDto;

import java.io.IOException;
import java.util.List;

public interface LanguageService {
    void saveLanguage() throws IOException;
    List<LanguageDto> findAllLanguages();
}

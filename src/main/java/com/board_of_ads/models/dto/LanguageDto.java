package com.board_of_ads.models.dto;

import com.board_of_ads.models.ForeignLanguage;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.models.posting.job.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageDto {
    private String language;

    public LanguageDto(ForeignLanguage language){
        this.language = language.getLanguage();
    }
}

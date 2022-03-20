package com.edu.domain.dto.subject;

import com.edu.domain.dto.BasePage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SubjectQueryDto extends BasePage implements Serializable {

    private static final long serialVersionUID = -6773363045511350954L;

    private String title;
}

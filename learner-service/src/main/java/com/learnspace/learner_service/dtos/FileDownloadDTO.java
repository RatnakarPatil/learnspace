package com.learnspace.learner_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@AllArgsConstructor
public class FileDownloadDTO {
    private String fileName;
    private String contentType;
    private Resource resource;
}

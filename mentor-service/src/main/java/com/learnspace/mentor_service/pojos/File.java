package com.learnspace.mentor_service.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String fileName;
    private String fileType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")  // ✅ Ensures storage for binary data
    private byte[] fileData;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;  // ✅ File belongs to a classroom

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;
}

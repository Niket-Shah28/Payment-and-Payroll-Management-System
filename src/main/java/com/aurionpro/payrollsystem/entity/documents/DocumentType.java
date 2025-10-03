package com.aurionpro.payrollsystem.entity.documents;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document_type")
public class DocumentType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "document_type_id")
	private Long documentTypeId;
	
	@Column(name = "document_type_name", nullable = false)
	private String documentTypeName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "file_format")
	private FileFormat fileFormat; 
	
	@Column(name = "max_size")
	private Integer maxSize;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private DocumentRole role;
	
	@Column(name = "compulsory")
	private Boolean compulsory;
	
	@Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isActive;
	
	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;
	
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedAt;
}

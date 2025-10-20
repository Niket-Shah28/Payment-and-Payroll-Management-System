
export interface DocumentTypeDto {
  documentTypeId: number;
  documentTypeName: string;
  fileFormat: 'pdf' | 'jpg' | 'png';
  maxSize: number;
  role: 'EMPLOYEE' | 'ORGANIZATION';
  compulsory: boolean;
}

export interface DocumentDto {
  documentId?: number;
  documentTypeId: number;
  cloudinaryUrl: string;
  documentSize: number;
  fileFormat: 'pdf' | 'jpg' | 'png';
}

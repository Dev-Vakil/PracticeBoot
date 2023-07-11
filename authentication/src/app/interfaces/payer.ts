export interface Payer {
    payerId: number;
    payerName: string;
    payerCode: string;  
    password: string;
    email: string;
    isActive: boolean;
    createdAt:string;
    modifiedAt:string;
    roleAssociation: number[];
  }
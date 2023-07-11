export interface Provider {
    providerId: number;
    providerName: string;
    providerCode: string;
    username: string;
    password: string;
    email: string;
    isActive: boolean;
    createdAt:string;
    modifiedAt:string;
    roleAssociation: number[];
  }
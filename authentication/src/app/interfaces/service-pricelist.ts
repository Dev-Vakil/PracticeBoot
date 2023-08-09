import { Pricelist } from "./pricelist";

export interface ServicePricelist {
    servicePricelistId: number;
    serviceCode: string;
    serviceDescription: string;
    price: number;
    status: String;
    pricelist: Pricelist;
}

import {Car} from "./car";
import {Reason} from "./reason";

export interface ReportPost {
    id: number,
    postId: number,
    title: string,
    description: string,
    horsepower: number,
    mileage: number,
    date: string,
    manufacturingYear: string,
    price: number,
    name: string,
    surname: string,
    phoneNumber: string,
    isNew: boolean;
    carType: string;
    color: string;
    car: Car,
    numOfImages: number,
    reasons: Reason[],
}

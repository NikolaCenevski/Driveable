import {Car} from "./car";

export interface Post {
    id: number,
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
}

import { MyElement } from "./my-element"

export interface MyResponse {
    timeStamp: Date;
    statusCode: number;
    status: string;
    reason: string;
    message: string;
    devMessage: string;
    data: {elements?: MyElement[], element?: MyElement}
}
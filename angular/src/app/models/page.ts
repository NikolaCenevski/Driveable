import {Post} from "./post";

export interface Page {
    content: Post[],
    totalElements: number,
    totalPages: number,
}

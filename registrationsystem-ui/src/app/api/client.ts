import {SERVER_URL} from "@/app/constant";
import axios from "axios";
export function registerUser(credentials: any){
    const path = "user"

    try {
        return axios({
            method: "POST",
            url: SERVER_URL + path,
            data: credentials
        })
    }
    catch (e) {
        console.error(e);
    }
}
import {SERVER_URL} from "@/app/constant";
import axios from "axios";
export function registerUser(credentials : {fullName:string, email:string, password:string}){
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
import Cookies from "js-cookie";
import axios from "axios";

const BASE_URL = 'http://localhost:8080'

const api = axios.create({
    baseURL : BASE_URL
})

api.interceptors.request.use((config) => {
    const token = Cookies.get('token');

    if(token){
        config.headers.Authorization = `Bearer ${token}`
    }

    return config
})

export const authService = {
    async login (username, password) {
        try{
            const response = await api.post('/auth/login',{username, password})

            if(response.data.token){
                Cookies.set('token', response.data.token, {expires : 7})
            }

            return response.data
        }
        catch(err){
            throw new Error(err.response?.data?.headers || "Login Failed")
        }
    }
    ,
    async signup(first, last, email, password) {
        const response = await api.post('/auth/register', { first, last, email, password });
        if (response.data.token) {
        Cookies.set('token', response.data.token, { expires: 7 });
        }
        return response.data;
    }
    ,
    logout(){
        Cookies.remove('token')
    }
    ,
    isAuthenticated(){
        return !!Cookies.get('token')
    }
    ,
    getToken(){
        return Cookies.get('token')
    }
}

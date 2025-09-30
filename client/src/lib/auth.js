import Cookies from "js-cookie";
import axios from "axios";

const BASE_URL = 'http://localhost:9090'

const api = axios.create({
    baseURL : BASE_URL
})

api.interceptors.request.use((config) => {
    const token = Cookies.get('token');

    if(token){
        config.headers.Authorization = `Bearer ${token}`
    }

    return config;
})

const GOOGLE_OAUTH_CONFIG = {
    clientId: '246953645362-i01mg48n95ldtjopahc5ttqg0foo2e9t.apps.googleusercontent.com',
    redirectUri: 'http://localhost:9090/auth/google/callback',
    scope: 'openid email profile',
    responseType: 'code'
};

export const authService = {
    async login (username, password) {
        try{
            const response = await api.post('/auth/login',{username, password})

            if(response.data.token){
                Cookies.set('token', response.data.token, {expires : 7})
                if (response.data.user) {
                    localStorage.setItem('user', JSON.stringify(response.data.user));
                }
            }
            
            console.log(response.data  + "from authjs login");
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
            if (response.data.user) {
                localStorage.setItem('user', JSON.stringify(response.data.user));
            }
        }
        console.log(response.data + "from authjs signup");
        return response.data
    }
    ,
    initiateGoogleLogin() {
        const params = new URLSearchParams({
            client_id: GOOGLE_OAUTH_CONFIG.clientId,
            redirect_uri: GOOGLE_OAUTH_CONFIG.redirectUri,
            response_type: GOOGLE_OAUTH_CONFIG.responseType,
            scope: GOOGLE_OAUTH_CONFIG.scope,
            access_type: 'offline',
            prompt: 'consent'
        });

        const authUrl = `https://accounts.google.com/o/oauth2/v2/auth?${params.toString()}`;

        console.log("from authjs initiateGoogleLogin()");
        
        window.location.href = authUrl;
    },

    async handleGoogleCallback(token) {
        try {
            console.log(token);
            
            if (token) {
                alert("Google OAuth successful, token: " + token);
                Cookies.set('token', token, { expires: 7 });

                const userResponse = await this.getCurrentUser(token);
                console.log(userResponse + "from handleGoogleCallback");
                
                if (userResponse) {
                    localStorage.setItem('user', JSON.stringify(userResponse));
                }
        
                console.log("from authjs handleGoogleCallback()");

                return { success: true, token, user: userResponse };
            }
            throw new Error('No token received from Google OAuth');
        } catch (err) {
            throw new Error(err.message || "Google authentication failed");
        }
    },
    async verifyOTP(email, otpCode) {
        try {
            const response = await api.post('/auth/verify-otp', { email, otpCode });
            if (response.data.success && response.data.token) {
                Cookies.set('token', response.data.token, { expires: 7 });
                if (response.data.user) {
                    localStorage.setItem('user', JSON.stringify(response.data.user));
                }
            }
            
            console.log("from authjs verifyOTP()");

            return response.data;
        } catch (err) {
            throw new Error(err.response?.data?.message || "OTP verification failed");
        }
    }
    ,
    async resendOTP(email) {
        try {
            const response = await api.post('/auth/resend-otp', { email });

            console.log("from authjs resendOTP()");
            
            return response.data;
        } catch (err) {
            throw new Error(err.response?.data?.message || "Failed to resend OTP");
        }
    }
    ,
    logout(){
        console.log("logout in auth");

        Cookies.remove('token');
        localStorage.removeItem('user');
        this.isAuthenticated();
        localStorage.clear();
    }
    ,
    isAuthenticated(){
        return Cookies.get('token')
    }
    ,
    async getCurrentUser(token) {
        try {
            const response = await api.get(`/auth/me?token=${token}`);
            return response.data;
        } catch (err) {
            throw new Error(err.response?.data?.message || "Failed to get user data");
        }
    },
    getCurrentUserFromStorage() {
        try {
            const userStr = localStorage.getItem('user');
            return userStr ? JSON.parse(userStr) : null;
        } catch {
            return null;
        }
    }
    ,
    getToken(){
        return Cookies.get('token')
    }
}

////////////////////////////////////////////////////////////////////////


// Response interceptor to handle token expiration
// api.interceptors.response.use(
//     (response) => response,
//     (error) => {
//         if (error.response?.status === 401) {
//             // Token expired or invalid, logout user
//             authService.logout();
//         }
//         return Promise.reject(error);
//     }
// );


    // Fetch current user from server


    // Refresh token
    // async refreshToken() {
    //     try {
    //         const response = await api.post('/auth/refresh');
    //         if (response.data.token) {
    //             Cookies.set('token', response.data.token, { expires: 7 });
    //             return response.data.token;
    //         }
    //     } catch (err) {
    //         this.logout();
    //         throw new Error('Token refresh failed');
    //     }
    // }

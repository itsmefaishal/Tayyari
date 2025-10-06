import fetch from 'node-fetch';
import dotenv from 'dotenv';
import fs from 'fs';

dotenv.config();

const log = (message) => {
    const timestamp = new Date().toLocaleString();
    fs.appendFileSync("pinger.log", `[${timestamp}] ${message}\n`);
}

const api_gateway_url = process.env.API_GATEWAY_URL;
const auth_url = process.env.AUTH_SERVICE_URL;
const user_url = process.env.USER_SERVICE_URL;
const question_url = process.env.QUESTION_SERVICE_URL;
const quiz_url = process.env.QUIZ_SERVICE_URL;
const service_url = process.env.SERVICE_REGISTRY_URL;

const urls = [
    api_gateway_url, auth_url, user_url, question_url, quiz_url, service_url
];

async function pingTayyariServices() {
    let success = 0;
    let failed = 0;

    console.log(`\n=== Pinging all services at ${new Date().toLocaleDateString()} ===`);
    log("=== Pinging all services ===");

    const promises = urls.map(async (url) => {
        try {
            const res = await fetch(url);
            console.log(`${url} -> ${res.status}`);
            log(`${url} -> ${res.status}`);

            if(res.status == 200){
                success++;
            }
            else{
                failed++;
            }
        } catch (err) {
            console.log(`${url} -> FAILED: ${err.message}`);
            log(`${url} -> ${res.status}`);
            failed++;
        }
    });

    await Promise.all(promises);

    console.log(
        `${success} services started successfully \n${failed} services failed to start`
    );
    log(
        `${success} services started successfully \n${failed} services failed to start`
    );
}

pingTayyariServices();
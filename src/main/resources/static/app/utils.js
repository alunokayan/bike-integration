const secretKey = '416e8c854ca3a038f0358b8e8befa50397d5290fe84f3b293f88396e42714965';

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
        const encryptedValue = parts.pop().split(';').shift();
        try {
            const bytes = CryptoJS.AES.decrypt(encryptedValue, secretKey);
            const decryptedValue = bytes.toString(CryptoJS.enc.Utf8);
            return decryptedValue;
        } catch (e) {
            console.error("Falha na descriptografia do cookie:", e);
            return null;
        }
    }
    return null;
}

function setCookie(name, value, days) {
    const date = new Date();
    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
    const encryptedValue = CryptoJS.AES.encrypt(value, secretKey).toString();
    document.cookie = `${name}=${encryptedValue};expires=${date.toUTCString()};path=/`;
}
const clearNumber = (num: string) => num.replace(/\D+/g, "");

export const formatCardNumber = (num: string) => {
    if(!num) return num;
    const clear = clearNumber(num);
    const newNum = `${clear.slice(0, 4)} ${clear.slice(4, 8)} ${clear.slice(8, 12)} ${clear.slice(12, 16)}`;
    return newNum.trim();
};

export const formatExpirationDate = (val: string) => {
    const clear = clearNumber(val);
    if(clear.length >= 3) {
        return `${clear.slice(0, 2)}/${clear.slice(2, 4)}`;
    }
    return clear;
};

export const accountErrors =() =>{
    const error = new Map();
    error.set(1,"This account number not exists");
    error.set(2,"You are trying to write off more than you have on your account");
    error.set(3,"Transaction error");
    return error;
}
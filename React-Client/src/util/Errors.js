export const accountErrors =() =>{
    const error = new Map();
    error.set(1,"This account number not exists");
    error.set(2,"You are trying to write off more than you have on your account");
    error.set(3,"Transaction error");
    return error;
}

export const authErrors = () =>{
    const error = new Map();
    error.set(1,"Bad username or password");
    error.set(2,"Invalid username");
    error.set(3,"Invalid password");
    return error;
}

export const registerErrors = () =>{
    const error = new Map();
    error.set(1,"Username is already exists");
    error.set(2,"Invalid username");
    error.set(3,"Invalid password");
    return error;
}
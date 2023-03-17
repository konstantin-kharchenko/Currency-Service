export const authErrors = () =>{
    const error = new Map();
    error.set(1,"Bad username or password");
    error.set(2,"Invalid username");
    error.set(3,"Invalid password");
    return error;
}
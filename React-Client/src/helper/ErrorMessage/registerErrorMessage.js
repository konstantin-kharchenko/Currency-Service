export const registerErrors = () =>{
    const error = new Map();
    error.set(1,"Username is already exists");
    error.set(2,"Invalid username");
    error.set(3,"Invalid password");
    return error;
}
import axios from 'axios';

const axiosInstance = axios.create();

const getSessionFromStorage = () => JSON.parse(localStorage.getItem('access-token'));

const baseUrl = 'http://localhost:8080';

export const request = async ({
                                  headers = {},
                                  method = 'GET',
                                  url,
                                  data,
                                  params,
                              }) => {
    const {accessToken} = getSessionFromStorage() || {};

    if (accessToken) {
        headers['Access-Token'] = accessToken;
    }
    const options = {
        headers,
        method,
        data,
        params,
        url: baseUrl + url,
        withCredentials: true
    };

    try {
        const result = await axiosInstance(options);

        return result;
    } catch (error) {
        if (error.response.status === 401) {

            const refreshOptions = {
                headers :{},
                method: "GET",
                url: baseUrl + '/auth/refresh',
                withCredentials: true
            }

            try {

                console.log("refresh request options: " + refreshOptions.url)
                const refreshResponse = await axiosInstance(refreshOptions);
                if (refreshResponse.data.accessToken!==undefined) {
                    localStorage.setItem('access-token', JSON.stringify({accessToken: refreshResponse.data.accessToken}));
                }
                headers['Access-Token'] = refreshResponse.data.accessToken;
                const finalOptions = {
                    headers,
                    method,
                    data,
                    params,
                    url: baseUrl + url,
                    withCredentials: true
                }

                const finalResponse = await axiosInstance(finalOptions);

                return finalResponse;
            } catch (error) {
                localStorage.clear();
                document.location = "http://localhost:3000/login"
            }
        }
        throw error;
    }
};
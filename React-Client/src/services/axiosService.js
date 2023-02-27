import axios from 'axios';

const axiosInstance = axios.create();

const getSessionFromStorage = () => JSON.parse(localStorage.getItem('access-token'));

const baseUrl = 'http://localhost:8080';

// фабрика создания запросов
export const request = async ({
                                  headers = {},
                                  method = 'GET',
                                  url,
                                  data,
                                  params,
                              }) => {
    // получили токен
    const { accessToken} = getSessionFromStorage() || {};

    // если есть токен то добавили его в header
    if (accessToken) {
        headers.Authorization = accessToken;
    }
    // формируем параметры запроса
    const options = {
        headers,
        method,
        data,
        params,
        url: baseUrl + url,
    };

    try {
        // выполняем запрос
        const result = await axiosInstance(options);

        return result;
    } catch (error) {
        if (error.response.status === 401) {
            options.method = 'POST';
            options.url = baseUrl + '/auth/refresh';

            const refreshResponse = await axiosInstance(options);
            localStorage.setItem('access-token', refreshResponse.accessToken);

            const finalResponse = await request({ headers, method, url, data, params });

            return finalResponse;
        }
        throw error;
    }
};
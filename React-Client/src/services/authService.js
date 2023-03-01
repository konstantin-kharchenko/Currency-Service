import {request} from './axiosService';

export const loginUser = async ({username, password}) => {

    /*fetch('http://localhost:8080/auth/'
        , {
            method: 'POST',
            headers: {
                'Accept:': ' *!/!*',
                'Accept-Encoding': 'gzip, deflate, br',
                'Host': 'localhost:8080',
                'Origin': 'http://localhost:3000',
                'Sec-Fetch-Dest': 'empty',
                'Sec-Fetch-Mode': 'cors',
                'Sec-Fetch-Site': 'same-site'
            },
            body: {
                username: username,
                password: password
            },
            credentials: 'include',
        })
        .then(response => response.json())
        .then(json => {
            localStorage.setItem('access-token', JSON.stringify({accessToken: json.accessToken}));
        })
        .catch(error => console.log(error))*/

    const {data} = await request({method: 'POST', url: '/auth/', data: {username, password}
    });
    localStorage.setItem('access-token', JSON.stringify({accessToken: data.accessToken}));
};

export const registerUser = async ({username, password}) => {
    await request({method: 'POST', url: '/registration/', data: {username, password}});
}
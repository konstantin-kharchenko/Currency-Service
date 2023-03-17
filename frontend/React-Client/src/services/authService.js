import {request} from './axiosService';

export const loginUser = async ({username, password}) => {
    const {data} = await request({method: 'POST', url: '/auth/', data: {username, password}});
    localStorage.setItem('access-token', JSON.stringify({accessToken: data.accessToken}));
};

export const registerUser = async ({username, password}) => {
    await request({method: 'POST', url: '/registration/', data: {username, password}});
}
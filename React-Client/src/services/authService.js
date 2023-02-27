import { request } from './axiosService';

export const loginUser = async ({ username, password }) => {
    const { data } = await request({ method: 'POST', url: '/auth/', data: { username, password } });
    localStorage.setItem('access-token', JSON.stringify({ accessToken: data.accessToken}));
};

export const loginUserByGitHub = async () =>{
    const res = await request({headers:{"Access-Control-Allow-Origin": "*"}, url: '/auth/to-github'});
    console.log(res.url)
}
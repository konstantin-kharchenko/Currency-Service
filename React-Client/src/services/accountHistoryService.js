import {request} from "./axiosService";

export const getNewPageInHistory = async (page, accountNumber) => {
    const {data} = await request({
        method: 'GET',
        url: '/history/find?page=' + page + '&size=3&sort=id,DESC&account-number=' + accountNumber
    });

    if (data !== undefined) {
        for (let i = 0; i < data.content.length; i++) {
            data.content[i].created = new Date(data.content[i].created);
        }
    }

    return data;
};